package org.javads;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.javads.stack.Stack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Date: 23/01/22
 * Time: 5:53 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class StackTest {

    @Test
    public void stack_test() {
        List<Integer> elem = Arrays.asList(1, 2, 3);
        Stack<Integer> stack = JavaDs.buildRegularStack();
        stack.push(elem.toArray(new Integer[0]));
        Collections.reverse(elem);
        assertArrayEquals(elem.toArray(new Integer[0]), stack.popStream().toArray(Integer[]::new));
    }

    @Test
    public void storage_grow_test() {
        Stack<Integer> stack = JavaDs.buildRegularStack();
        IntStream.range(0, 50).forEach(stack::push);
        Assertions.assertEquals(50, stack.popStream().count());
    }
}
