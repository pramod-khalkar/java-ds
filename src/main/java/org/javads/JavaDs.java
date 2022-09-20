package org.javads;

import org.javads.nlinear.tree.AvlTree;
import org.javads.nlinear.tree.BTree;
import org.javads.nlinear.tree.BiTree;
import org.javads.nlinear.tree.BinarySearchTree;
import org.javads.nlinear.tree.GeneralBinaryTree;
import org.javads.nlinear.tree.GeneralNaryTree;
import org.javads.nlinear.tree.RedBlackTree;
import org.javads.nlinear.tree.SplayTree;
import org.javads.nlinear.tree.Treap;
import org.javads.nlinear.tree.TreapTree;
import org.javads.nlinear.tree.Tree;
import org.javads.nlinear.tree.UnBalanceBiTree;
import org.javads.nlinear.tree.UnBalanceNaryTree;

/**
 * Date: 23/01/22
 * Time: 1:17 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class JavaDs {

    /**
     * @param <E> Type of element
     * @return create instance of BST (Binary Search Tree)
     */
    public static <E extends Comparable<? super E>> BiTree<E> buildBinarySearchTree() {
        return new BinarySearchTree<>();
    }

    /**
     * @param <E> Type of element
     * @return create instance of AVL tree
     */
    public static <E extends Comparable<? super E>> BiTree<E> buildAvlTree() {
        return new AvlTree<>();
    }

    /**
     * @param <E> Type of element
     * @return create instance of Splay tree
     */

    public static <E extends Comparable<? super E>> BiTree<E> buildSplayTree() {
        return new SplayTree<>();
    }

    /**
     * @param <E> Type of element
     * @return create instance of Treap tree
     */
    public static <E extends Comparable<? super E>> TreapTree<E> buildTreap() {
        return new Treap<>();
    }

    /**
     * @param <E> Type of element
     * @return create instance of General N-Array Tree
     */
    public static <E> UnBalanceNaryTree<E> buildGeneralNaryTree() {
        return new GeneralNaryTree<>();
    }

    /**
     * @param <E> Type of element
     * @return create instance of General Binary Tree
     */
    public static <E> UnBalanceBiTree<E> buildBinaryTree() {
        return new GeneralBinaryTree<>();
    }

    /**
     * @param <E> Type of element
     * @return create instance of Red Black Tree
     */
    public static <E extends Comparable<? super E>> BiTree<E> buildRedBlackTree() {
        return new RedBlackTree<>();
    }

    /**
     * @param <E> Type of element
     * @return create instance of B Tree
     */
    public static <E extends Comparable<? super E>> Tree<E> buildBTree() {
        return new BTree<>();
    }
}
