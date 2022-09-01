package org.javads.exception;

import org.javads.nlinear.Node;

/**
 * @author : Pramod Khalkar
 * @since : 28/08/22, Sun
 * description: This file belongs to java-ds
 **/
public class NodeException extends RuntimeException {
    private Node node;

    public NodeException(String msg) {
        super(msg);
        this.node = null;
    }

    public NodeException(Node node, String msg) {
        super(msg);
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
