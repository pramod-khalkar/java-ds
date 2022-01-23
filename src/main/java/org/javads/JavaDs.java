package org.javads;

/**
 * Date: 23/01/22
 * Time: 1:17 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class JavaDs {

    public static <E> RegularQueue<E> buildReqularQueue() {
        return new RegularQueue<>();
    }

    public static <E> RegularStack<E> buildReqularStack() {
        return new RegularStack<>();
    }

    public static <E extends Comparable<? super E>> BinarySearchTree<E> buildBinarySearchTree() {
        return new BinarySearchTree<>();
    }
}
