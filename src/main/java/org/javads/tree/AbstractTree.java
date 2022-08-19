package org.javads.tree;

import java.util.Objects;
import java.util.Optional;

/**
 * Date: 05/01/22
 * Time: 8:14 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
abstract class AbstractTree<E> implements Tree<E> {
    private Node<E> root;

    protected AbstractTree() {
        this.root = null;
    }

    @Override
    public boolean isEmpty() {
        return getRootNode() == null;
    }

    @Override
    public Node<E> getRootNode() {
        return this.root;
    }

    protected void setRootNode(Node<E> root) {
        this.root = root;
    }

    @Override
    public Optional<Node<E>> search(E value) {
        Objects.requireNonNull(value);
        return Optional.empty();
    }

    @Override
    public Node<E> insert(E value) {
        Objects.requireNonNull(value);
        return new Node<>(value);
    }

    @Override
    public void remove(E value) {
        Objects.requireNonNull(value);
    }

    @Override
    public void removeAll() {
        setRootNode(null);
    }
}
