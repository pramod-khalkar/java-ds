package org.javads.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
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
        this.index = -1;
    }

    public SimpleStack(int capacity) {
        super(capacity);
    }

    @Override
    public void push(T value) throws RuntimeException {
        if ((index + 1) < this.capacity) {
            this.elementData[++index] = value;
        } else {
            //throw new RuntimeException("Stack is full");
            growStorage();
            push(value);
        }
    }

    @Override
    public T pop() throws RuntimeException {
        if (!isEmpty()) {
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

    @Override
    public boolean isEmpty() {
        return index < 0;
    }

    @SafeVarargs
    @Override
    public final void push(T... elements) {
        Arrays.stream(elements).forEach(this::push);
    }

    @Override
    public Stream<T> popStream() {
        List<T> list = new ArrayList<>();
        while (!this.isEmpty()) {
            list.add(this.pop());
        }
        return list.stream();
    }
}
