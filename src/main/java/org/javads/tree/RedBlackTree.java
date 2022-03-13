package org.javads.tree;

import static org.javads.tree.RedBlackTree.Color.BLACK;
import static org.javads.tree.RedBlackTree.Color.RED;

import java.util.Objects;
import java.util.Optional;

/**
 * Date: 25/01/22
 * Time: 7:33 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 * Left leaning R-B tree
 * https://codetube.vn/visual/redblacktree/
 * https://www.fatalerrors.org/a/principle-and-implementation-of-red-black-tree.html
 */
public class RedBlackTree<E extends Comparable<? super E>> extends BSTree<E> {

    public RedBlackTree() {
        super();
    }

    @Override
    public Optional<Node<E>> search(E value) {
        return super.search(value);
    }

    @Override
    public Node<E> insert(E value) {
        Objects.requireNonNull(value);
        RbNode<E> newNode = new RbNode<>(value);
        insertRbNode(newNode);
        return newNode;
    }

    private void insertRbNode(RbNode<E> newNode) {
        RbNode<E> y = null;
        RbNode<E> x = (RbNode<E>) getRootNode();
        while (x != null) {
            y = x;
            if (newNode.getData().compareTo(x.getData()) > 0) {
                x = (RbNode<E>) x.getRight();
            } else {
                x = (RbNode<E>) x.getLeft();
            }
        }
        newNode.setParent(y);
        if (y != null) {
            if (newNode.getData().compareTo(y.getData()) > 0) {
                y.setRight(newNode);
            } else {
                y.setLeft(newNode);
            }
        } else {
            setRootNode(newNode);
        }
        newNode.setColor(RED);
        checkRbPropertiesAfterInsert(newNode);
    }

    private void checkRbPropertiesAfterInsert(RbNode<E> node) {
        RbNode<E> parent, gparent;
        while (((parent = parentOf(node)) != null) && isRed(parent)) {
            gparent = parentOf(parent);
            if (parent == gparent.getLeft()) {
                RbNode<E> uncle = (RbNode<E>) gparent.getRight();
                if (isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }
                if (parent.getRight().equals(node)) {
                    RbNode<E> tmp;
                    rotateLeft(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }
                setBlack(parent);
                setRed(gparent);
                rotateRight(gparent);
            } else {
                RbNode<E> uncle = (RbNode<E>) gparent.getLeft();
                if (isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }
                if (parent.getLeft() != null && parent.getLeft().equals(node)) {
                    RbNode<E> tmp;
                    rotateRight(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }
                setBlack(parent);
                setRed(gparent);
                rotateLeft(gparent);
            }
        }
        setBlack((RbNode<E>) getRootNode());
    }

    private void rotateRight(RbNode<E> y) {
        RbNode<E> x = (RbNode<E>) y.getLeft();
        y.setLeft(x.getRight());
        if (x.getRight() != null) {
            ((RbNode<E>) x.getRight()).setParent(y);
        }
        x.setParent(y.getParent());
        if (y.getParent() == null) {
            setRootNode(x);
        } else {
            if (y.getParent().getRight().equals(y)) {
                y.getParent().setRight(x);
            } else {
                y.getParent().setLeft(x);
            }
        }
        x.setRight(y);
        y.setParent(x);

        RbNode<E> z = (RbNode<E>) x.getRight();
        x.setRight(y);
        y.setParent(x);
    }

    private void rotateLeft(RbNode<E> x) {
        RbNode<E> y = (RbNode<E>) x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != null) {
            ((RbNode<E>) y.getLeft()).parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            setRootNode(y);            // If "x's father" is an empty node, set y as the root node
        } else {
            if (x.parent.getLeft().equals(x)) {
                x.parent.setLeft(y);
            }// If x is the left child of its parent, set y to "the left child of X's parent"
            else {
                x.parent.setRight(y);    // If x is the left child of its parent, set y to "the left child of X's parent"
            }
        }
        // Set "x" to "left child of y"
        y.setLeft(x);
        // Set "parent of x" to "y"
        x.parent = y;
    }

    @Override
    public void remove(E value) {
        Objects.requireNonNull(value);
        Optional<Node<E>> tobeDeleted = search(value);
        tobeDeleted.ifPresent(eNode -> deleteRbNode((RbNode<E>) eNode));
    }

    private void deleteRbNode(RbNode<E> node) {
        RbNode<E> child, parent;
        Color color;
        if ((node.getLeft() != null) && (node.getRight() != null)) {
            RbNode<E> replace = node;
            replace = (RbNode<E>) replace.getRight();
            while (replace.getLeft() != null) {
                replace = (RbNode<E>) replace.getLeft();
            }
            if (parentOf(node) != null) {
                if (parentOf(node).getLeft() == node) {
                    parentOf(node).setLeft(replace);
                } else {
                    parentOf(node).setRight(replace);
                }
            } else {
                setRootNode(replace);
            }
            child = (RbNode<E>) replace.getRight();
            parent = parentOf(replace);
            color = colorOf(replace);
            if (parent == node) {
                parent = replace;
            } else {
                if (child != null) {
                    setParent(child, parent);
                }
                parent.setLeft(child);
                replace.setRight(node.getRight());
                setParent((RbNode<E>) node.getRight(), replace);
            }
            replace.setParent(node.getParent());
            replace.color = node.color;
            replace.setLeft(node.getLeft());
            ((RbNode<E>) node.getLeft()).setParent(replace);
            if (color == BLACK) {
                checkRbPropertiesAfterDelete(child, parent);
            }
            node = null;
            return;
        }
        if (node.getLeft() != null) {
            child = (RbNode<E>) node.getLeft();
        } else {
            child = (RbNode<E>) node.getRight();
        }
        parent = node.getParent();
        color = node.color;
        if (child != null) {
            child.setParent(parent);
        }
        if (parent != null) {
            if (parent.getLeft() == node) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        } else {
            setRootNode(child);
        }
        if (color == BLACK) {
            checkRbPropertiesAfterDelete(child, parent);
        }
        node = null;
    }

    private void checkRbPropertiesAfterDelete(RbNode<E> node, RbNode<E> parent) {
        RbNode<E> other;
        while ((node == null || isBlack(node)) && (node != getRootNode())) {
            if (parent.getLeft() == node) {
                other = (RbNode<E>) parent.getRight();
                if (isRed(other)) {
                    setBlack(other);
                    setRed(parent);
                    rotateLeft(parent);
                    other = (RbNode<E>) parent.getRight();
                }
                if ((other.getLeft() == null || isBlack((RbNode<E>) other.getLeft())) &&
                        (other.getRight() == null || isBlack((RbNode<E>) other.getRight()))) {
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {
                    if (other.getRight() == null || isBlack((RbNode<E>) other.getRight())) {
                        setBlack((RbNode<E>) other.getLeft());
                        setRed(other);
                        rotateRight(other);
                        other = (RbNode<E>) parent.getRight();
                    }
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack((RbNode<E>) other.getRight());
                    rotateLeft(parent);
                    node = (RbNode<E>) getRootNode();
                    break;
                }
            } else {
                other = (RbNode<E>) parent.getLeft();
                if (isRed(other)) {
                    setBlack(other);
                    setRed(parent);
                    rotateRight(parent);
                    other = (RbNode<E>) parent.getLeft();
                }
                if ((other.getLeft() == null || isBlack((RbNode<E>) other.getLeft())) &&
                        (other.getRight() == null || isBlack((RbNode<E>) other.getRight()))) {
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {
                    if (other.getLeft() == null || isBlack((RbNode<E>) other.getLeft())) {
                        setBlack((RbNode<E>) other.getRight());
                        setRed(other);
                        rotateLeft(other);
                        other = (RbNode<E>) parent.getLeft();
                    }
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack((RbNode<E>) other.getLeft());
                    rotateRight(parent);
                    node = (RbNode<E>) getRootNode();
                    break;
                }
            }
        }
        if (node != null) {
            setBlack(node);
        }
    }

    private RbNode<E> parentOf(RbNode<E> node) {
        return node != null ? node.parent : null;
    }

    private Color colorOf(RbNode<E> node) {
        return node != null ? node.color : BLACK;
    }

    private boolean isRed(RbNode<E> node) {
        return (node != null) && (node.color == RED);
    }

    private boolean isBlack(RbNode<E> node) {
        return !isRed(node);
    }

    private void setBlack(RbNode<E> node) {
        if (node != null) {
            node.color = BLACK;
        }
    }

    private void setRed(RbNode<E> node) {
        if (node != null) {
            node.color = RED;
        }
    }

    private void setParent(RbNode<E> node, RbNode<E> parent) {
        if (node != null) {
            node.parent = parent;
        }
    }

    private void setColor(RbNode<E> node, Color color) {
        if (node != null) {
            node.color = color;
        }
    }

    static class RbNode<E> extends Node<E> {
        private Color color;
        private RbNode<E> parent;

        public RbNode(E data) {
            super(data);
            this.color = RED;
            this.parent = null;
        }

        public RbNode(E value, Color color, RbNode<E> parent, RbNode<E> left, RbNode<E> right) {
            super(value);
            this.color = color;
            this.parent = parent;
            setLeft(left);
            setRight(right);
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public void setParent(RbNode<E> parent) {
            this.parent = parent;
        }

        public RbNode<E> getParent() {
            return parent;
        }

        @Override
        public String toString() {
            return super.toString() + String.format("[%s]", this.color == RED ? "red" : "black");
        }
    }

    enum Color {
        RED, BLACK;
    }
}
