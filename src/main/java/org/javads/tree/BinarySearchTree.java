package org.javads.tree;

import static java.util.Optional.empty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Date: 05/01/22
 * Time: 8:17 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class BinarySearchTree<E extends Comparable<? super E>> extends AbstractTree<E> {

    public BinarySearchTree() {
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

    @Override
    public List<E> preOrderTraverse() {
        if (getRootNode() == null) {
            return Collections.emptyList();
        } else {
            List<E> list = new ArrayList<>();
            preOrderTraverse0(getRootNode(), list);
            return list;
        }
    }

    @Override
    public List<E> postOrderTraverse() {
        if (getRootNode() == null) {
            return Collections.emptyList();
        } else {
            List<E> list = new ArrayList<>();
            postOrderTraverse0(getRootNode(), list);
            return list;
        }
    }

    @Override
    public List<E> inOrderTraverse() {
        if (getRootNode() == null) {
            return Collections.emptyList();
        } else {
            List<E> list = new ArrayList<>();
            inOrderTraverse0(getRootNode(), list);
            return list;
        }
    }

    private void inOrderTraverse0(Node<E> tNode, List<E> list) {
        if (tNode == null) {
            return;
        }
        inOrderTraverse0(tNode.getLeft(), list);
        list.add(tNode.getData());
        inOrderTraverse0(tNode.getRight(), list);
    }

    private void preOrderTraverse0(Node<E> tNode, List<E> list) {
        if (tNode == null) {
            return;
        }
        list.add(tNode.getData());
        preOrderTraverse0(tNode.getLeft(), list);
        preOrderTraverse0(tNode.getRight(), list);
    }

    private void postOrderTraverse0(Node<E> tNode, List<E> list) {
        if (tNode == null) {
            return;
        }
        postOrderTraverse0(tNode.getLeft(), list);
        postOrderTraverse0(tNode.getRight(), list);
        list.add(tNode.getData());
    }

    @SafeVarargs
    public final void insert(E... elements) {
        Arrays.stream(elements).forEach(this::insert);
    }

    @SafeVarargs
    public final void delete(E... elements) {
        Arrays.stream(elements).forEach(this::remove);
    }

    @Override
    public Stream<E> preOrderTraverseStream() {
        return preOrderTraverse().stream();
    }

    @Override
    public Stream<E> inOrderTraverseStream() {
        return inOrderTraverse().stream();
    }

    @Override
    public Stream<E> postOrderTraverseStream() {
        return postOrderTraverse().stream();
    }
}
