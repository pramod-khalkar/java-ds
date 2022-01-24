package org.javads.tree;

import java.util.Optional;

/**
 * Date: 16/01/22
 * Time: 7:10 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class SplayTree<E extends Comparable<? super E>> extends BSTree<E> {

    public SplayTree() {
        super();
    }

    @Override
    public Optional<Node<E>> search(E value) {
        Optional<Node<E>> result = super.search(value);
        setRootNode(splayTheTree(getRootNode(), new Node<>(value)));
        return result;
    }

    @Override
    public Node<E> insert(E value) {
        Node<E> newNode = super.insert(value);
        setRootNode(splayTheTree(getRootNode(), newNode));
        return newNode;
    }

    @Override
    public void remove(E value) {
        Node<E> nodeToDelete = new Node<>(value);
        setRootNode(splayTheTree(getRootNode(), nodeToDelete));
        if (getRootNode().getData().compareTo(nodeToDelete.getData()) == 0) {
            Node<E> leftSubTree = getRootNode().getLeft();
            Node<E> rightSubTree = getRootNode().getRight();
            leftSubTree = splayTheTree(leftSubTree, maxValueNode(leftSubTree));
            if (leftSubTree != null) {
                leftSubTree.setRight(rightSubTree);
            } else {
                leftSubTree = rightSubTree;
            }
            setRootNode(leftSubTree);
        }
    }

    private Node<E> maxValueNode(Node<E> node) {
        while (node != null && node.getRight() != null) {
            node = maxValueNode(node.getRight());
        }
        return node;
    }

    private Node<E> splayTheTree(Node<E> tNode, Node<E> node) {
        if (tNode == null || tNode.getData().compareTo(node.getData()) == 0) {
            return tNode;
        }
        if (tNode.getData().compareTo(node.getData()) > 0) {
            if (tNode.getLeft() == null) {
                return tNode;
            }
            if (tNode.getLeft().getData().compareTo(node.getData()) > 0) {
                tNode.getLeft().setLeft(splayTheTree(tNode.getLeft().getLeft(), node));
                tNode = rotateRight(tNode);
            } else if (tNode.getLeft().getData().compareTo(node.getData()) < 0) {
                tNode.getLeft().setRight(splayTheTree(tNode.getLeft().getRight(), node));
                if (tNode.getLeft().getRight() != null) {
                    tNode.setLeft(rotateLeft(tNode.getLeft()));
                }
            }
            return tNode.getLeft() == null ? tNode : rotateRight(tNode);
        } else {
            if (tNode.getRight() == null) {
                return tNode;
            }
            if (tNode.getRight().getData().compareTo(node.getData()) > 0) {
                tNode.getRight().setLeft(splayTheTree(tNode.getRight().getLeft(), node));
                if (tNode.getRight().getLeft() != null) {
                    tNode.setRight(rotateRight(tNode.getRight()));
                }
            } else if (tNode.getRight().getData().compareTo(node.getData()) < 0) {
                tNode.getRight().setRight(splayTheTree(tNode.getRight().getRight(), node));
                tNode = rotateLeft(tNode);
            }
            return tNode.getRight() == null ? tNode : rotateLeft(tNode);
        }
    }

    private Node<E> rotateRight(Node<E> y) {
        Node<E> x = y.getLeft();
        Node<E> z = x.getRight();
        x.setRight(y);
        y.setLeft(z);
        return x;
    }

    private Node<E> rotateLeft(Node<E> y) {
        Node<E> x = y.getRight();
        Node<E> z = x.getLeft();
        x.setLeft(y);
        y.setRight(z);
        return x;
    }
}
