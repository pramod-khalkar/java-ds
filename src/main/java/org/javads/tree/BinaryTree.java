package org.javads.tree;

import java.util.List;
import java.util.stream.Stream;

/**
 * Date: 23/01/22
 * Time: 11:56 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public interface BinaryTree<E> extends Tree<E> {

    Stream<E> preOrderTraverseStream();

    Stream<E> inOrderTraverseStream();

    Stream<E> postOrderTraverseStream();

    List<E> preOrderTraverse();

    List<E> postOrderTraverse();

    List<E> inOrderTraverse();
}
