package org.javads;

import org.javads.queue.SimpleQueue;
import org.javads.stack.SimpleStack;
import org.javads.tree.AvlTree;
import org.javads.tree.BinarySearchTree;
import org.javads.tree.SplayTree;
import org.javads.tree.Treap;
import org.javads.tree.unbalance.BinaryTree;
import org.javads.tree.unbalance.GeneralTree;

/**
 * Date: 23/01/22
 * Time: 1:17 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class JavaDs {

    public static <E> SimpleQueue<E> buildRegularQueue() {
        return new SimpleQueue<>();
    }

    public static <E> SimpleStack<E> buildRegularStack() {
        return new SimpleStack<>();
    }

    public static <E extends Comparable<? super E>> BinarySearchTree<E> buildBinarySearchTree() {
        return new BinarySearchTree<>();
    }

    public static <E extends Comparable<? super E>> AvlTree<E> buildAvlTree() {
        return new AvlTree<>();
    }

    public static <E extends Comparable<? super E>> SplayTree<E> buildSplayTree() {
        return new SplayTree<>();
    }

    public static <E extends Comparable<? super E>> Treap<E> buildTreap() {
        return new Treap<>();
    }

    public static <E> GeneralTree<E> buildGeneralTree() {
        return new GeneralTree<>();
    }

    public static <E> BinaryTree<E> buildBinaryTree() {
        return new BinaryTree<>();
    }
}
