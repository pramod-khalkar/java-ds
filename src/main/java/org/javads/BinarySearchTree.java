package org.javads;

import java.util.Arrays;
import org.javads.internal.tree.BSTree;

/**
 * Date: 23/01/22
 * Time: 2:14 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class BinarySearchTree<E extends Comparable<? super E>> {
    private BSTree<E> bsTree;

    BinarySearchTree() {
        bsTree = new BSTree<>();
    }

    @SafeVarargs
    public final void insert(E... elements) {
        Arrays.stream(elements).forEach(bsTree::insert);
    }

    @SafeVarargs
    public final void delete(E... elements) {
        Arrays.stream(elements).forEach(bsTree::remove);
    }

    public boolean search(E element) {
        return bsTree.search(element).isPresent();
    }
}
