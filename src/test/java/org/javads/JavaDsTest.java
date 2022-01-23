package org.javads;

import org.junit.jupiter.api.Test;

/**
 * Date: 23/01/22
 * Time: 1:56 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class JavaDsTest {

    @Test
    public void queue_test() {
        RegularQueue<Integer> queue = JavaDs.buildReqularQueue();
        queue.enqueue(1, 2, 3, 4, 5);
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
    }

    @Test
    public void stack_test() {
        RegularStack<Integer> stack = JavaDs.buildReqularStack();
        stack.push(1, 2, 3, 4, 5);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }

    @Test
    public void bs_tree_test() {
        BinarySearchTree<Integer> bsTree = JavaDs.buildBinarySearchTree();
        bsTree.insert(1, 2, 3, 4, 5);
        System.out.println(bsTree.search(4));
        System.out.println(bsTree.search(44));
    }
}
