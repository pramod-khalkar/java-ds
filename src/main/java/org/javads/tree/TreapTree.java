package org.javads.tree;

/**
 * Date: 24/01/22
 * Time: 11:33 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public interface TreapTree<E> extends BinaryTree<E> {
    Node<E> insert(E value, int priority);
}
