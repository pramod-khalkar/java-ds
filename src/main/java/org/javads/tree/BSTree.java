package org.javads.tree;

import static java.util.Optional.empty;

import java.util.Objects;
import java.util.Optional;

/**
 * Date: 05/01/22
 * Time: 8:17 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class BSTree<E extends Comparable<? super E>> extends AbstractBinaryTree<E> {

    public BSTree() {
        super();
    }

    @Override
    public Optional<Node<E>> search(E value) {
        Objects.requireNonNull(value);
        return search0(getRootNode(), new Node<>(value));
    }

    private Optional<Node<E>> search0(Node<E> tNode, Node<E> searchNode) {
        if (tNode != null) {
            if (tNode.getData().compareTo(searchNode.getData()) == 0) {
                return Optional.of(tNode);
            } else {
                if (tNode.getData().compareTo(searchNode.getData()) > 0) {
                    return search0(tNode.getLeft(), searchNode);
                } else if (tNode.getData().compareTo(searchNode.getData()) < 0) {
                    return search0(tNode.getRight(), searchNode);
                }
            }
        }
        return empty();
    }

    @Override
    public Node<E> insert(E value) {
        Objects.requireNonNull(value);
        Node<E> newNode = new Node<>(value);
        setRootNode(insert0(getRootNode(), newNode));
        return newNode;
    }

    private Node<E> insert0(Node<E> tNode, Node<E> newNode) {
        if (tNode == null) {
            return newNode;
        } else if (tNode.getData().compareTo(newNode.getData()) > 0) {
            tNode.setLeft(insert0(tNode.getLeft(), newNode));
        } else if (tNode.getData().compareTo(newNode.getData()) < 0) {
            tNode.setRight(insert0(tNode.getRight(), newNode));
        } else {
            throw new RuntimeException(String.format("Duplicate value %s", newNode.getData()));
        }
        return tNode;
    }

    @Override
    public void remove(E value) {
        Objects.requireNonNull(value);
        delete0(getRootNode(), value);
    }

    private Node<E> delete0(Node<E> tNode, E nodeTobeDeleted) {
        if (tNode == null) {
            return tNode;
        }
        if (nodeTobeDeleted.compareTo(tNode.getData()) < 0) {
            tNode.setLeft(delete0(tNode.getLeft(), nodeTobeDeleted));
        } else if (nodeTobeDeleted.compareTo(tNode.getData()) > 0) {
            tNode.setRight(delete0(tNode.getRight(), nodeTobeDeleted));
        } else {
            if (tNode.getLeft() == null) {
                return tNode.getRight();
            } else if (tNode.getRight() == null) {
                return tNode.getLeft();
            } else {
                tNode.setData(minValue(tNode.getRight()));
                tNode.setRight(delete0(tNode.getRight(), tNode.getData()));
            }
        }
        return tNode;
    }

    private E minValue(Node<E> node) {
        E minVal = node.getData();
        while (node.getLeft() != null) {
            minVal = node.getLeft().getData();
            node = node.getLeft();
        }
        return minVal;
    }
}
