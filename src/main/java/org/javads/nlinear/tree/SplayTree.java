package org.javads.nlinear.tree;

import java.util.Optional;

/**
 * Date: 16/01/22
 * Time: 7:10 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class SplayTree<E extends Comparable<? super E>> extends BinarySearchTree<E> {

    public SplayTree() {
        super();
    }

    @Override
    public Optional<E> search(E value) {
        Optional<E> result = super.search(value);
        setRootNode(splayTheTree((BiNode<E>) getRootNode(), new BiNode<>(value)));
        return result;
    }

    @Override
    public void insert(E value) {
        super.insert(value);
        setRootNode(splayTheTree((BiNode<E>) getRootNode(), new BiNode<>(value)));
    }

    @Override
    public void remove(E value) {
        BiNode<E> nodeToDelete = new BiNode<>(value);
        setRootNode(splayTheTree((BiNode<E>) getRootNode(), nodeToDelete));
        if (((BiNode<E>) getRootNode()).getData().compareTo(nodeToDelete.getData()) == 0) {
            BiNode<E> rootN = (BiNode<E>) getRootNode();
            BiNode<E> leftSubTree = rootN.getLeft();
            BiNode<E> rightSubTree = rootN.getRight();
            leftSubTree = splayTheTree(leftSubTree, maxValueNode(leftSubTree));
            if (leftSubTree != null) {
                leftSubTree.setRight(rightSubTree);
            } else {
                leftSubTree = rightSubTree;
            }
            setRootNode(leftSubTree);
        }
    }

    private BiNode<E> maxValueNode(BiNode<E> node) {
        while (node != null && node.getRight() != null) {
            node = maxValueNode(node.getRight());
        }
        return node;
    }

    private BiNode<E> splayTheTree(BiNode<E> tNode, BiNode<E> node) {
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

    private BiNode<E> rotateRight(BiNode<E> y) {
        BiNode<E> x = y.getLeft();
        BiNode<E> z = x.getRight();
        x.setRight(y);
        y.setLeft(z);
        return x;
    }

    private BiNode<E> rotateLeft(BiNode<E> y) {
        BiNode<E> x = y.getRight();
        BiNode<E> z = x.getLeft();
        x.setLeft(y);
        y.setRight(z);
        return x;
    }
}
