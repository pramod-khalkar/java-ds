package org.javads.queue;

import java.util.stream.Stream;
import org.javads.Algorithm;

/**
 * Date: 31/12/21
 * Time: 9:45 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public interface Queue<E> extends Algorithm {
    void enqueue(E value);

    E dequeue();

    boolean isEmpty();

    boolean isFull();

    E peek();

    void removeAll();

    void enqueue(E... elements);

    Stream<E> dequeueStream();
}
