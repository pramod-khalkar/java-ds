package org.javads.internal.stack;

import org.javads.internal.Algorithm;

/**
 * Date: 31/12/21
 * Time: 12:39 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public interface Stack<T> extends Algorithm {
    void push(T value);

    T pop();

    void clear();
}
