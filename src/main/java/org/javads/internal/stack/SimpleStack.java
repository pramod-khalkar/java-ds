package org.javads.internal.stack;

import org.javads.internal.ArrayStorage;

/**
 * Date: 31/12/21
 * Time: 12:14 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class SimpleStack<T> extends ArrayStorage<T> implements Stack<T> {

    private int index;

    public SimpleStack() {
        this(10);
        this.index = 0;
    }

    public SimpleStack(int capacity) {
        super(capacity);
    }

    @Override
    public void push(T value) throws RuntimeException {
        if (index < this.capacity) {
            this.elementData[++index] = value;
        } else {
            throw new RuntimeException("Stack is full");
        }
    }

    @Override
    public T pop() throws RuntimeException {
        if (index >= 0) {
            T popedElement = (T) this.elementData[index];
            this.elementData[index] = null;
            index--;
            return popedElement;
        }
        throw new RuntimeException("Stack is empty");
    }

    @Override
    public void clear() {
        resetStorage();
        index = 0;
    }
}
