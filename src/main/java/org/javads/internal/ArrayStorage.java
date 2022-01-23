package org.javads.internal;

/**
 * Date: 31/12/21
 * Time: 9:56 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public abstract class ArrayStorage<E> {
    protected Object[] elementData;
    protected int capacity;

    protected ArrayStorage(int capacity) {
        this.capacity = capacity;
        this.elementData = new Object[this.capacity];
    }

    protected void resetStorage() {
        this.elementData = new Object[this.capacity];
    }
}
