package org.javads.tree;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * Date: 04/01/22
 * Time: 11:32 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class GeneralBinaryTree<E> extends AbstractBinaryTree<E> implements UnBalanceBinaryTree<E> {

    private final Random random;

    public GeneralBinaryTree() {
        super();
        random = new Random();
    }

    @Override
    public Node<E> insert(E value) {
        Node<E> newNode = super.insert(value);
        Node<E> tNode = randomInsert(getRootNode(), newNode, (random.nextInt(2) == 0) ? Side.LEFT : Side.RIGHT);
        setRootNode(tNode);
        return newNode;
    }

    private Node<E> randomInsert(Node<E> node, Node<E> newNode, Side side) {
        if (node == null) {
            return newNode;
        } else if (side == Side.LEFT) {
            if (node.getLeft() == null) {
                node.setLeft(randomInsert(node.getLeft(), newNode, side));
            } else {
                node.setRight(randomInsert(node.getRight(), newNode, side));
            }
        } else if (side == Side.RIGHT) {
            if (node.getRight() == null) {
                node.setRight(randomInsert(node.getRight(), newNode, side));
            } else {
                node.setLeft(randomInsert(node.getLeft(), newNode, side));
            }
        } else {
            throw new RuntimeException(String.format("Duplicate value %s", newNode.getData()));
        }
        return node;
    }

    @Override
    public Node<E> insert(E value, E parent, Side side) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(parent);
        Node<E> newNode = new Node<>(value);
        if (isEmpty()) {
            setRootNode(newNode);
        } else {
            Optional<Node<E>> parentNode = search0(getRootNode(), new Node<>(parent));
            if (parentNode.isPresent()) {
                parentInsert(newNode, parentNode.get(), side);
            } else {
                throw new RuntimeException(String.format("Parent node not available %s", parent));
            }
        }
        return newNode;
    }

    private void parentInsert(Node<E> newNode, Node<E> parentNode, Side side) {
        if (parentNode.equals(newNode)) {
            throw new RuntimeException(String.format("Duplicate value %s", newNode.getData()));
        } else {
            if (side == Side.LEFT && parentNode.getLeft() == null) {
                parentNode.setLeft(newNode);
            } else if (side == Side.RIGHT && parentNode.getRight() == null) {
                parentNode.setRight(newNode);
            } else {
                throw new RuntimeException(String.format("Parent %s already has %s node", newNode.getData(), side));
            }
        }
    }

    @Override
    public Optional<Node<E>> search(E value) {
        super.search(value);
        return search0(getRootNode(), new Node<>(value));
    }

    private Optional<Node<E>> search0(Node<E> node, Node<E> searchNode) {
        if (node != null) {
            if (node.equals(searchNode)) {
                return Optional.of(node);
            } else {
                Optional<Node<E>> leftNode = search0(node.getLeft(), searchNode);
                Optional<Node<E>> rightNode = search0(node.getRight(), searchNode);
                return leftNode.isPresent() ? leftNode : rightNode;
            }
        }
        return Optional.empty();
    }

    @Override
    public void remove(E value) {
        super.remove(value);
        setRootNode(delete0(getRootNode(), new Node<>(value)));
    }

    private Node<E> delete0(Node<E> node, Node<E> toBeDelete) {
        if (node == null) {
            return node;
        } else if (node.equals(toBeDelete)) {
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                node.setData(node.getRight().getData());
                node.setRight(delete0(node.getRight(), node));
            }
        } else {
            node.setLeft(delete0(node.getLeft(), toBeDelete));
            node.setRight(delete0(node.getRight(), toBeDelete));
        }
        return node;
    }
}
