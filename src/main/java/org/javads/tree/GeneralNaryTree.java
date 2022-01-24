package org.javads.tree;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * Date: 01/01/22
 * Time: 9:05 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class GeneralNaryTree<E> extends AbstractTree<E> implements UnBalanceNaryTree<E> {

    public GeneralNaryTree() {
        super();
    }

    @Override
    public Node<E> insert(E value) {
        return insert(value, !isEmpty() ? getRootNode().getData() : value);
    }

    @Override
    public Node<E> insert(E value, E parent) {
        Node<E> newGNode = new Node<>(value);
        Node<E> parentGNode = new Node<>(parent);
        newGNode.setData(value);
        if (isEmpty() || parent == null) {
            setRootNode(newGNode);
        } else {
            Node<E> parent_ = findParent(getRootNode(), parentGNode);
            if (parent_ != null) {
                if (parent_.getLeft() == null) {
                    parent_.setLeft(newGNode);
                } else {
                    if (parent_.getLeft().getRight() == null) {
                        parent_.getLeft().setRight(newGNode);
                    } else {
                        Node<E> sibling = parent_.getLeft().getRight();
                        while (sibling.getRight() != null) {
                            sibling = sibling.getRight();
                        }
                        sibling.setRight(newGNode);
                    }
                }
            }
        }
        return newGNode;
    }

    private Node<E> findParent(Node<E> tNode, Node<E> searchingNode) {
        if (tNode.equals(searchingNode)) {
            return tNode;
        } else {
            if (tNode.getLeft() != null) {
                return findParent(tNode.getLeft(), searchingNode);
            }
            if (tNode.getRight() != null) {
                return findParent(tNode.getRight(), searchingNode);
            }
        }
        return null;
    }

    @Override
    public void remove(E value) {
        Node<E> tobeDeleted = new Node<>(value);
        if (!isEmpty()) {
            if (getRootNode().equals(tobeDeleted)) {
                setRootNode(null);
            } else {
                setRootNode(delete0(getRootNode(), tobeDeleted));
            }
        }
    }

    private Node<E> delete0(Node<E> tNode, Node<E> deletingNode) {
        if (tNode == null) {
            return tNode;
        } else if (tNode.equals(deletingNode)) {
            tNode = tNode.getRight();
        } else {
            tNode.setLeft(delete0(tNode.getLeft(), deletingNode));
            tNode.setRight(delete0(tNode.getRight(), deletingNode));
        }
        return tNode;
    }

    @Override
    public Optional<Node<E>> search(E value) {
        return search0(getRootNode(), new Node<>(value));
    }

    private Optional<Node<E>> search0(Node<E> tNode, Node<E> value) {
        if (tNode != null) {
            if (tNode.equals(value)) {
                return Optional.of(tNode);
            } else {
                if (tNode.getLeft() != null) {
                    return search0(tNode.getLeft(), value);
                }
                if (tNode.getRight() != null) {
                    return search0(tNode.getRight(), value);
                }
            }
        }
        return Optional.empty();
    }

    @SafeVarargs
    @Override
    public final void insert(E... elements) {
        Arrays.stream(elements).forEach(this::insert);
    }

    @SafeVarargs
    @Override
    public final void delete(E... elements) {
        Arrays.stream(elements).forEach(this::delete);
    }

    public final void insert(Map<E, E> childParentRelation) {
        childParentRelation.forEach(this::insert);
    }
}
