package org.javads.nlinear.tree;

/**
 * Date: 24/01/22
 * Time: 11:30 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public interface UnBalanceNaryTree<E> extends Tree<E> {
    /**
     * @param value  element to be inserted
     * @param parent what should be the parent of new element , pass same as a parent if it is first node
     */
    void insert(E value, E parent);
}
