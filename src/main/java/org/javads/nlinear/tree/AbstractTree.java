package org.javads.nlinear.tree;

import java.util.Arrays;
import org.javads.nlinear.Node;

/**
 * Date: 05/01/22
 * Time: 8:14 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
abstract class AbstractTree<E> implements Tree<E> {
    private Node root;

    protected AbstractTree() {
        this.root = null;
    }

    @Override
    public Node getRootNode() {
        return this.root;
    }

    protected void setRootNode(Node root) {
        this.root = root;
    }

    @Override
    public void removeAll() {
        setRootNode(null);
    }

    @Override
    public void insert(E... elements) {
        Arrays.stream(elements).forEach(this::insert);
    }

    @Override
    public void remove(E... elements) {
        Arrays.stream(elements).forEach(this::remove);
    }

    public boolean isEmpty() {
        return getRootNode() == null;
    }
}
