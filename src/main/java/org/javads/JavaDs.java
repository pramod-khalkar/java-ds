package org.javads;

import org.javads.queue.Queue;
import org.javads.queue.SimpleQueue;
import org.javads.stack.SimpleStack;
import org.javads.stack.Stack;
import org.javads.tree.AvlTree;
import org.javads.tree.BSTree;
import org.javads.tree.BinaryTree;
import org.javads.tree.GeneralBinaryTree;
import org.javads.tree.GeneralNaryTree;
import org.javads.tree.RedBlackTree;
import org.javads.tree.SplayTree;
import org.javads.tree.Treap;
import org.javads.tree.TreapTree;
import org.javads.tree.UnBalanceBinaryTree;
import org.javads.tree.UnBalanceNaryTree;

/**
 * Date: 23/01/22
 * Time: 1:17 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class JavaDs {

    public static <E> Queue<E> buildRegularQueue() {
        return new SimpleQueue<>();
    }

    public static <E> Stack<E> buildRegularStack() {
        return new SimpleStack<>();
    }

    public static <E extends Comparable<? super E>> BinaryTree<E> buildBinarySearchTree() {
        return new BSTree<>();
    }

    public static <E extends Comparable<? super E>> BinaryTree<E> buildAvlTree() {
        return new AvlTree<>();
    }

    public static <E extends Comparable<? super E>> BinaryTree<E> buildSplayTree() {
        return new SplayTree<>();
    }

    public static <E extends Comparable<? super E>> TreapTree<E> buildTreap() {
        return new Treap<>();
    }

    public static <E> UnBalanceNaryTree<E> buildGeneralNaryTree() {
        return new GeneralNaryTree<>();
    }

    public static <E> UnBalanceBinaryTree<E> buildBinaryTree() {
        return new GeneralBinaryTree<>();
    }

    public static <E extends Comparable<? super E>> BinaryTree<E> buildRedBlackTree() {
        return new RedBlackTree<>();
    }
}
