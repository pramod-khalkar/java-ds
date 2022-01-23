package org.javads.queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Date: 31/12/21
 * Time: 9:54 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class SimpleQueue<E> extends AbstractQueue<E> implements Queue<E> {

    public SimpleQueue() {
        this(10);
    }

    public SimpleQueue(int capacity) {
        super(capacity);
    }

    @Override
    public void enqueue(E value) throws RuntimeException {
        if (isFull()) {
            //throw new RuntimeException("Queue is full");
            growStorage();
            enqueue(value);
        } else {
            if (this.front == -1) {
                this.front++;
            }
            this.elementData[++this.rear] = value;
        }
    }

    @Override
    public E dequeue() throws RuntimeException {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        } else {
            E element = (E) this.elementData[this.front++];
            if (this.front > this.rear) {
                this.front = this.rear = -1;
            }
            return element;
        }
    }

    @Override
    public boolean isEmpty() {
        return this.front == -1;
    }

    @Override
    public boolean isFull() {
        return this.front != -1 && this.rear == this.capacity - 1;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        } else {
            return (E) this.elementData[this.front];
        }
    }

    @Override
    public void removeAll() {
        resetStorage();
    }

    @SafeVarargs
    @Override
    public final void enqueue(E... elements) {
        Arrays.stream(elements).forEach(this::enqueue);
    }

    @Override
    public Stream<E> dequeueStream() {
        List<E> allElements = new ArrayList<>();
        while (!this.isEmpty()) {
            allElements.add(this.dequeue());
        }
        return allElements.stream();
    }
}
