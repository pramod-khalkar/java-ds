package org.javads.internal.tree;

import java.util.Optional;
import org.javads.internal.Algorithm;

/**
 * Date: 01/01/22
 * Time: 9:01 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public interface Tree<T> extends Algorithm {

    Optional<Node<T>> search(T value);

    Node<T> insert(T value);

    void remove(T value);

    void removeAll();

    boolean isEmpty();
}
