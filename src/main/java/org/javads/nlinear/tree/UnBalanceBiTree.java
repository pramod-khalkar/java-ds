package org.javads.nlinear.tree;

/**
 * Date: 24/01/22
 * Time: 11:41 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public interface UnBalanceBiTree<E> extends BiTree<E> {
    /**
     * @param value  element to be added
     * @param parent parent element value for new element, in case of root node it should be same of new value
     * @param side   mention LEFT or RIGHT node
     */
    void insert(E value, E parent, BiSide side);
}
