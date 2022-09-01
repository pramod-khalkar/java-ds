package org.javads.nlinear.tree;

import java.util.Objects;
import org.javads.nlinear.Node;

/**
 * Date: 05/01/22
 * Time: 5:04 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class BiNode<T> implements Node {
    private T data;
    private BiNode<T> left;
    private BiNode<T> right;

    public BiNode(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.data != null ? data.toString() : null;
    }

    public BiNode<T> getLeft() {
        return left;
    }

    public BiNode<T> getRight() {
        return right;
    }

    public void setLeft(BiNode<T> left) {
        this.left = left;
    }

    public void setRight(BiNode<T> right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BiNode<T> node_ = (BiNode<T>) obj;
        return Objects.equals(this.data, node_.data);
    }
}
