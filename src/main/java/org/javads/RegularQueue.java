package org.javads;

import java.util.Arrays;
import org.javads.internal.queue.Queue;
import org.javads.internal.queue.SimpleQueue;

/**
 * Date: 23/01/22
 * Time: 1:59 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class RegularQueue<E> {
    private Queue<E> queue_;

    RegularQueue() {
        this.queue_ = new SimpleQueue<>();
    }

    @SafeVarargs
    public final void enqueue(E... elements) {
        Arrays.stream(elements).forEach(queue_::enqueue);
    }

    public E dequeue() {
        return queue_.dequeue();
    }
}
