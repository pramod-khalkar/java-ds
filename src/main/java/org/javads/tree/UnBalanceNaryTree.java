package org.javads.tree;

/**
 * Date: 24/01/22
 * Time: 11:30 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public interface UnBalanceNaryTree<E> extends Tree<E> {
    Node<E> insert(E value, E parent);
}
