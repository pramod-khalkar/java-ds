package org.javads.internal;

import java.util.Arrays;

/**
 * Date: 31/12/21
 * Time: 9:56 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public abstract class ArrayStorage<E> {
    protected Object[] elementData;
    protected int capacity;
    private static final Integer DEFAULT_CAPACITY = 10;

    protected ArrayStorage(int capacity) {
        this.capacity = capacity;
        this.elementData = new Object[this.capacity];
    }

    protected void resetStorage() {
        this.elementData = new Object[DEFAULT_CAPACITY];
    }

    // TODO: 23/01/22  need to handle well
    protected void growStorage() {
        this.elementData = Arrays.copyOf(this.elementData, this.elementData.length * 2);
        this.capacity = this.elementData.length;
    }
}
