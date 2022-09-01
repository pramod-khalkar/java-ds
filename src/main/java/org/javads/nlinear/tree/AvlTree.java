package org.javads.nlinear.tree;

import java.util.Objects;
import java.util.Optional;

/**
 * Date: 05/01/22
 * Time: 10:02 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class AvlTree<E extends Comparable<? super E>> extends BinarySearchTree<E> {

    public AvlTree() {
        super();
    }

    @Override
    public void insert(E value) {
        Objects.requireNonNull(value);
        AvlNode<E> newNode = new AvlNode<>(value);
        setRootNode(insert0((AvlNode<E>) getRootNode(), newNode));
    }

    AvlNode<E> insert0(AvlNode<E> tNode, AvlNode<E> newNode) {
        if (tNode == null) {
            return newNode;
        } else if (tNode.getData().compareTo(newNode.getData()) > 0) {
            tNode.setLeft(insert0((AvlNode<E>) tNode.getLeft(), newNode));
        } else if (tNode.getData().compareTo(newNode.getData()) < 0) {
            tNode.setRight(insert0((AvlNode<E>) tNode.getRight(), newNode));
        } else {
            throw new RuntimeException(String.format("Duplicate value %s", newNode.getData()));
        }
        return reBalance(tNode);
    }

    private AvlNode<E> reBalance(AvlNode<E> z) {
        z.updateHeight();
        int bal = z.getBalanceFactor();
        if (bal > 1) {
            if (heightOf(z.getRight().getRight()) > heightOf(z.getRight().getLeft())) {
                z = rotateLeft(z);
            } else {
                z.setRight(rotateRight((AvlNode<E>) z.getRight()));
                z = rotateLeft(z);
            }
        } else if (bal < -1) {
            if (heightOf(z.getLeft().getLeft()) > heightOf(z.getLeft().getRight())) {
                z = rotateRight(z);
            } else {
                z.setLeft(rotateLeft((AvlNode<E>) z.getLeft()));
                z = rotateRight(z);
            }
        }
        return z;
    }

    protected <E> int balanceFactorOf(BiNode<E> node) {
        return heightOf(node.getLeft()) - heightOf(node.getRight());
    }

    protected <E> int heightOf(BiNode<E> tNode) {
        int height = 0;
        if (tNode == null) {
            height = -1;
        } else {
            height = Math.max(heightOf(tNode.getLeft()), heightOf(tNode.getRight())) + 1;
        }
        return height;
    }

    private AvlNode<E> rotateRight(AvlNode<E> y) {
        AvlNode<E> x = (AvlNode<E>) y.getLeft();
        AvlNode<E> z = (AvlNode<E>) x.getRight();
        x.setRight(y);
        y.setLeft(z);
        y.updateHeight();
        x.updateHeight();
        return x;
    }

    private AvlNode<E> rotateLeft(AvlNode<E> y) {
        AvlNode<E> x = (AvlNode<E>) y.getRight();
        AvlNode<E> z = (AvlNode<E>) x.getLeft();
        x.setLeft(y);
        y.setRight(z);
        y.updateHeight();
        x.updateHeight();
        return x;
    }

    @Override
    public void remove(E value) {
        super.remove(value);
        reBalance((AvlNode<E>) getRootNode());
    }

    @Override
    public Optional<E> search(E value) {
        return super.search(value);
    }

    public class AvlNode<T> extends BiNode<T> {
        private int height;

        public AvlNode(T data) {
            super(data);
            this.height = -1;
        }

        public int getBalanceFactor() {
            int rightH, leftH;
            rightH = this.getRight() == null ? 0 : ((AvlNode<T>) this.getRight()).getHeight();
            leftH = this.getLeft() == null ? 0 : ((AvlNode<T>) this.getLeft()).getHeight();
            return rightH - leftH;
        }

        public int getHeight() {
            return this.height;
        }

        public void updateHeight() {
            this.height = heightOf(this);
        }

        @Override
        public String toString() {
            return super.toString() + String.format("(bf:%d)", getBalanceFactor());
        }
    }
}
