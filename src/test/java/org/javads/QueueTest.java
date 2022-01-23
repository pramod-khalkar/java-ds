package org.javads;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.javads.queue.Queue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Date: 23/01/22
 * Time: 5:54 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class QueueTest {

    @Test
    public void queue_test() {
        Integer[] elem = new Integer[] {1, 2, 3};
        Queue<Integer> queue = JavaDs.buildRegularQueue();
        queue.enqueue(elem);
        List<Integer> list = queue.dequeueStream().collect(Collectors.toList());
        assertArrayEquals(elem, list.toArray(new Integer[0]));
    }

    @Test
    public void storage_grow_test() {
        Queue<Integer> queue = JavaDs.buildRegularQueue();
        IntStream.range(0, 50).forEach(queue::enqueue);
        Assertions.assertEquals(50, queue.dequeueStream().count());
    }
}
