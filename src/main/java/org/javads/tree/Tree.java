package org.javads.tree;

import java.util.Optional;
import org.javads.Algorithm;

/**
 * Date: 01/01/22
 * Time: 9:01 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public interface Tree<E> extends Algorithm {

    Optional<Node<E>> search(E value);

    Node<E> insert(E value);

    void remove(E value);

    void removeAll();

    boolean isEmpty();

    void insert(E... elements);

    void delete(E... elements);
}
