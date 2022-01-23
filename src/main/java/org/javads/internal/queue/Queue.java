package org.javads.internal.queue;

import org.javads.internal.Algorithm;

/**
 * Date: 31/12/21
 * Time: 9:45 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public interface Queue<T> extends Algorithm {
    void enqueue(T value);

    T dequeue();

    boolean isEmpty();

    boolean isFull();

    T peek();

    void clear();
}
