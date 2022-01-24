package org.javads.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Date: 24/01/22
 * Time: 12:12 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {

    protected AbstractBinaryTree() {
        super();
    }

    protected void inOrderTraverse0(Node<E> tNode, List<E> list) {
        if (tNode == null) {
            return;
        }
        inOrderTraverse0(tNode.getLeft(), list);
        list.add(tNode.getData());
        inOrderTraverse0(tNode.getRight(), list);
    }

    protected void preOrderTraverse0(Node<E> tNode, List<E> list) {
        if (tNode == null) {
            return;
        }
        list.add(tNode.getData());
        preOrderTraverse0(tNode.getLeft(), list);
        preOrderTraverse0(tNode.getRight(), list);
    }

    protected void postOrderTraverse0(Node<E> tNode, List<E> list) {
        if (tNode == null) {
            return;
        }
        postOrderTraverse0(tNode.getLeft(), list);
        postOrderTraverse0(tNode.getRight(), list);
        list.add(tNode.getData());
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

    @SafeVarargs
    public final void insert(E... elements) {
        Arrays.stream(elements).forEach(this::insert);
    }

    @SafeVarargs
    public final void delete(E... elements) {
        Arrays.stream(elements).forEach(this::remove);
    }
}
