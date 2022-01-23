package org.javads.tree;

import java.util.Random;

/**
 * Date: 17/01/22
 * Time: 10:39 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class Treap<E extends Comparable<? super E>> extends BinarySearchTree<E> {

    private final Random random = new Random();

    public Treap() {
        super();
    }

    @Override
    public Node<E> insert(E value) {
        TreapNode<E> newNode = new TreapNode<>(value);
        setRootNode(isEmpty() ? newNode : insert0((TreapNode<E>) getRootNode(), newNode));
        return newNode;
    }

    public Node<E> insert(E value, int priority) {
        TreapNode<E> newNode = new TreapNode<>(value, priority);
        setRootNode(isEmpty() ? newNode : insert0((TreapNode<E>) getRootNode(), newNode));
        return newNode;
    }

    private TreapNode<E> insert0(TreapNode<E> tNode, TreapNode<E> newNode) {
        if (tNode == null) {
            return newNode;
        }
        if (tNode.getData().compareTo(newNode.getData()) > 0) {
            tNode.setLeft(insert0((TreapNode<E>) tNode.getLeft(), newNode));
            if (tNode.getLeft() != null && ((TreapNode<E>) tNode.getLeft()).getPriority() > tNode.getPriority()) {
                tNode = (TreapNode<E>) rotateRight(tNode);
            }
        } else if (tNode.getData().compareTo(newNode.getData()) < 0) {
            tNode.setRight(insert0((TreapNode<E>) tNode.getRight(), newNode));
            if (tNode.getRight() != null && ((TreapNode<E>) tNode.getRight()).getPriority() > tNode.getPriority()) {
                tNode = (TreapNode<E>) rotateLeft(tNode);
            }
        } else {
            throw new RuntimeException(String.format("Duplicate value %s", newNode.getData()));
        }
        return tNode;
    }

    private Node<E> rotateRight(TreapNode<E> y) {
        Node<E> x = y.getLeft();
        Node<E> z = x.getRight();
        x.setRight(y);
        y.setLeft(z);
        return x;
    }

    private Node<E> rotateLeft(TreapNode<E> y) {
        Node<E> x = y.getRight();
        Node<E> z = x.getLeft();
        x.setLeft(y);
        y.setRight(z);
        return x;
    }

    @Override
    public void remove(E value) {
        TreapNode<E> tobeDeleted = new TreapNode<>(value);
        if (!isEmpty()) {
            setRootNode(delete0((TreapNode<E>) getRootNode(), tobeDeleted));
        }
    }

    private TreapNode<E> delete0(TreapNode<E> tNode, TreapNode<E> tobeDeleted) {
        if (tNode == null) {
            return null;
        }
        if (tNode.getData().compareTo(tobeDeleted.getData()) > 0) {
            tNode.setLeft(delete0((TreapNode<E>) tNode.getLeft(), tobeDeleted));
        } else if (tNode.getData().compareTo(tobeDeleted.getData()) < 0) {
            tNode.setRight(delete0((TreapNode<E>) tNode.getRight(), tobeDeleted));
        } else {
            if (tNode.getLeft() == null && tNode.getRight() == null) {
                tNode = null;
            } else if (tNode.getLeft() != null && tNode.getRight() != null) {
                if (((TreapNode<E>) tNode.getLeft()).getPriority() < ((TreapNode<E>) tNode.getRight()).getPriority()) {
                    tNode = (TreapNode<E>) rotateLeft(tNode);
                    tNode.setLeft(delete0((TreapNode<E>) tNode.getLeft(), tobeDeleted));
                } else {
                    tNode = (TreapNode<E>) rotateRight(tNode);
                    tNode.setRight(delete0((TreapNode<E>) tNode.getRight(), tobeDeleted));
                }
            } else {
                tNode = tNode.getLeft() != null ? (TreapNode<E>) tNode.getLeft() : (TreapNode<E>) tNode.getRight();
            }
        }
        return tNode;
    }

    class TreapNode<T> extends Node<T> {
        private int priority;

        public TreapNode(T data, int priority) {
            super(data);
            this.priority = priority;
        }

        public TreapNode(T data) {
            this(data, random.nextInt(500));
        }

        public int getPriority() {
            return this.priority;
        }

        @Override
        public String toString() {
            return super.toString() + String.format("(P:%d)", getPriority());
        }
    }
}
