package org.javads.nlinear.tree;

import java.util.List;

/**
 * Date: 23/01/22
 * Time: 11:56 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public interface BiTree<E> extends Tree<E> {

    /**
     * @return list of pre order traveled elements
     */
    List<E> preOrderTraverse();

    /**
     * @return list of post order traveled elements
     */
    List<E> postOrderTraverse();

    /**
     * @return list of in order traveled elements
     */
    List<E> inOrderTraverse();
}
