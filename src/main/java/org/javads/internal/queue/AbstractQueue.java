package org.javads.internal.queue;

import org.javads.internal.ArrayStorage;

/**
 * Date: 19/01/22
 * Time: 11:12 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public abstract class AbstractQueue<T> extends ArrayStorage<T> {
    protected int front, rear;

    protected AbstractQueue(int capacity) {
        super(capacity);
        this.front = this.rear = -1;
    }

    @Override
    protected void resetStorage() {
        super.resetStorage();
        this.front = this.rear = -1;
    }
}
