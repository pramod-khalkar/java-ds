package org.javads.internal.queue;

/**
 * Date: 31/12/21
 * Time: 9:54 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class SimpleQueue<T> extends AbstractQueue<T> implements Queue<T> {

    public SimpleQueue() {
        this(10);
    }

    public SimpleQueue(int capacity) {
        super(capacity);
    }

    @Override
    public void enqueue(T value) throws RuntimeException {
        if (isFull()) {
            throw new RuntimeException("Queue is full");
        } else {
            if (this.front == -1) {
                this.front++;
            }
            this.elementData[++this.rear] = value;
        }
    }

    @Override
    public T dequeue() throws RuntimeException {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        } else {
            T element = (T) this.elementData[this.front++];
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
        return this.front == -1 && this.rear == this.capacity;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        } else {
            return (T) this.elementData[this.front];
        }
    }

    @Override
    public void clear() {
        resetStorage();
    }
}
