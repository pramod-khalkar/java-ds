package org.javads;

import java.util.Arrays;
import org.javads.internal.stack.SimpleStack;
import org.javads.internal.stack.Stack;

/**
 * Date: 23/01/22
 * Time: 2:04 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class RegularStack<E> {
    private final Stack<E> stack_;

    RegularStack() {
        this.stack_ = new SimpleStack<E>();
    }

    @SafeVarargs
    public final void push(E... elements) {
        Arrays.stream(elements).forEach(stack_::push);
    }

    public E pop() {
        return stack_.pop();
    }
}
