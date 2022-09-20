package org.javads.nlinear.tree;

/**
 * Date: 24/01/22
 * Time: 11:33 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public interface TreapTree<E> extends BiTree<E> {
    /**
     * @param value    element to be added
     * @param priority priority for inserted element , less means low priority
     */
    void insert(E value, int priority);
}
