package org.javads.nlinear.tree;

import java.util.Optional;
import org.javads.Algorithm;
import org.javads.nlinear.Node;

/**
 * Date: 01/01/22
 * Time: 9:01 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public interface Tree<E> extends Algorithm {

    /**
     * @return root node or head
     */
    Node getRootNode();

    /**
     * @param value element to be inserted
     */
    void insert(E value);

    /**
     * @param elements multiple elements to be inserted
     */
    void insert(E... elements);

    /**
     * @param value element to be inserted
     * @return searched node or empty if not found
     */
    Optional<E> search(E value);

    /**
     * @param elements multiple elements to be removed
     */
    void remove(E... elements);

    /**
     * @param value element to be removed
     */
    void remove(E value);

    /**
     * remove all node's
     */
    void removeAll();
}
