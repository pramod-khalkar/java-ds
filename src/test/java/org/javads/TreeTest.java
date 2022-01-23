package org.javads;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.javads.tree.Treap;
import org.javads.tree.Tree;
import org.javads.tree.unbalance.BinaryTree;
import org.javads.tree.unbalance.GeneralTree;
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
        Tree<Integer> bsTree = JavaDs.buildBinarySearchTree();
        Tree<Integer> eAvlTree = JavaDs.buildAvlTree();
        Tree<Integer> eSplayTree = JavaDs.buildSplayTree();
        Treap<Integer> eTreap = JavaDs.buildTreap();
        GeneralTree<Integer> gTree = JavaDs.buildGeneralTree();
        BinaryTree<Integer> bTree = new BinaryTree<>();

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

    }
}
