package org.javads.nlinear.tree;

import static java.util.Optional.empty;

import java.util.Objects;
import java.util.Optional;

/**
 * Date: 05/01/22
 * Time: 8:17 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class BinarySearchTree<E extends Comparable<? super E>> extends AbstractBiTree<E> {

    public BinarySearchTree() {
        super();
    }

    @Override
    public Optional<E> search(E value) {
        Objects.requireNonNull(value);
        Optional<BiNode<E>> sNode = search0((BiNode<E>) getRootNode(), new BiNode<>(value));
        return sNode.map(BiNode::getData);
    }

    protected Optional<BiNode<E>> search0(BiNode<E> tNode, BiNode<E> searchNode) {
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
    public void insert(E value) {
        Objects.requireNonNull(value);
        setRootNode(insert0((BiNode<E>) getRootNode(), new BiNode<>(value)));
    }

    private BiNode<E> insert0(BiNode<E> tNode, BiNode<E> newNode) {
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
        delete0((BiNode<E>) getRootNode(), value);
    }

    private BiNode<E> delete0(BiNode<E> tNode, E nodeTobeDeleted) {
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

    private E minValue(BiNode<E> node) {
        E minVal = node.getData();
        while (node.getLeft() != null) {
            minVal = node.getLeft().getData();
            node = node.getLeft();
        }
        return minVal;
    }
}
