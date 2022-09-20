package org.javads;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.javads.nlinear.tree.BTree;
import org.javads.nlinear.tree.BiTree;
import org.javads.nlinear.tree.GeneralBinaryTree;
import org.javads.nlinear.tree.TreapTree;
import org.javads.nlinear.tree.Tree;
import org.javads.nlinear.tree.UnBalanceBiTree;
import org.javads.nlinear.tree.UnBalanceNaryTree;
import org.junit.jupiter.api.Test;

/**
 * Date: 23/01/22
 * Time: 5:54 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class TreeTest {

    @Test
    public void all_tree_traversal_test() {
        Integer[] elements = new Integer[] {10, 30, 20, 40, 50};
        BiTree<Integer> bsTree = JavaDs.buildBinarySearchTree();
        BiTree<Integer> eAvlTree = JavaDs.buildAvlTree();
        BiTree<Integer> eSplayTree = JavaDs.buildSplayTree();
        TreapTree<Integer> eTreap = JavaDs.buildTreap();
        // TODO: 23/01/22 Need to implement other supported methods
        UnBalanceNaryTree<Integer> gTree = JavaDs.buildGeneralNaryTree();
        UnBalanceBiTree<Integer> bTree = new GeneralBinaryTree<>();
        BiTree<Integer> rbTree = JavaDs.buildRedBlackTree();

        // Binary tree test
        bsTree.insert(elements);
        assertArrayEquals(bsTree.preOrderTraverse().toArray(new Integer[0]), new Integer[] {10, 30, 20, 40, 50});
        assertArrayEquals(bsTree.inOrderTraverse().toArray(new Integer[0]), new Integer[] {10, 20, 30, 40, 50});
        assertArrayEquals(bsTree.postOrderTraverse().toArray(new Integer[0]), new Integer[] {20, 50, 40, 30, 10});

        // AVL tree test
        eAvlTree.insert(elements);
        assertArrayEquals(eAvlTree.preOrderTraverse().toArray(new Integer[0]), new Integer[] {30, 10, 20, 40, 50});
        assertArrayEquals(eAvlTree.inOrderTraverse().toArray(new Integer[0]), new Integer[] {10, 20, 30, 40, 50});
        assertArrayEquals(eAvlTree.postOrderTraverse().toArray(new Integer[0]), new Integer[] {20, 10, 50, 40, 30});

        // Splaying tree test
        eSplayTree.insert(elements);
        assertArrayEquals(eSplayTree.preOrderTraverse().toArray(new Integer[0]), new Integer[] {50, 40, 30, 20, 10});
        assertArrayEquals(eSplayTree.inOrderTraverse().toArray(new Integer[0]), new Integer[] {10, 20, 30, 40, 50});
        assertArrayEquals(eSplayTree.postOrderTraverse().toArray(new Integer[0]), new Integer[] {10, 20, 30, 40, 50});

        // Treap tree test
        Integer[] elementsPriority = new Integer[] {35, 213, 276, 289, 235};
        for (int index = 0; index < elements.length; index++) {
            eTreap.insert(elements[index], elementsPriority[index]);
        }
        assertArrayEquals(eTreap.preOrderTraverse().toArray(new Integer[0]), new Integer[] {40, 20, 10, 30, 50});
        assertArrayEquals(eTreap.inOrderTraverse().toArray(new Integer[0]), new Integer[] {10, 20, 30, 40, 50});
        assertArrayEquals(eTreap.postOrderTraverse().toArray(new Integer[0]), new Integer[] {10, 30, 20, 50, 40});

        //Red black tree test
        rbTree.insert(elements);
        assertArrayEquals(rbTree.preOrderTraverse().toArray(new Integer[0]), new Integer[] {20, 10, 40, 30, 50});
        assertArrayEquals(rbTree.inOrderTraverse().toArray(new Integer[0]), new Integer[] {10, 20, 30, 40, 50});
        assertArrayEquals(rbTree.postOrderTraverse().toArray(new Integer[0]), new Integer[] {10, 30, 50, 40, 20});
    }

    @Test
    public void general_nary_tree_test() {
        //General Nary tree test
        Tree<Integer> genAryTree = JavaDs.buildGeneralNaryTree();
        genAryTree.insert(10);
        genAryTree.insert(20, 10);
        genAryTree.insert(30, 10);
        genAryTree.insert(40, 10);
        genAryTree.insert(1, 30);
        genAryTree.insert(2, 40);
        genAryTree.insert(3, 40);
        genAryTree.search(3);
    }

    @Test
    public void test() {
        Tree<Integer> tree = new BTree<>();
        tree.insert(44, 1000, 0, 12, 5, 45, 1, 23, 56, 8, 2);
        tree.insert(100);
    }
}
