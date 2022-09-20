package org.javads.nlinear.tree;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * Date: 04/01/22
 * Time: 11:32 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class GeneralBinaryTree<E> extends AbstractBiTree<E> implements UnBalanceBiTree<E> {

    private final Random random;

    public GeneralBinaryTree() {
        super();
        random = new Random();
    }

    @Override
    public void insert(E value) {
        Objects.requireNonNull(value);
        BiNode<E> tNode = randomInsert((BiNode<E>) getRootNode(), new BiNode<>(value), (random.nextInt(2) == 0) ? BiSide.LEFT : BiSide.RIGHT);
        setRootNode(tNode);
    }

    private BiNode<E> randomInsert(BiNode<E> node, BiNode<E> newNode, BiSide side) {
        if (node == null) {
            return newNode;
        } else if (side == BiSide.LEFT) {
            if (node.getLeft() == null) {
                node.setLeft(randomInsert(node.getLeft(), newNode, side));
            } else {
                node.setRight(randomInsert(node.getRight(), newNode, side));
            }
        } else if (side == BiSide.RIGHT) {
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
    public void insert(E value, E parent, BiSide side) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(parent);
        BiNode<E> newNode = new BiNode<>(value);
        if (isEmpty()) {
            setRootNode(newNode);
        } else {
            Optional<BiNode<E>> parentNode = search0((BiNode<E>) getRootNode(), new BiNode<>(parent));
            if (parentNode.isPresent()) {
                parentInsert(newNode, parentNode.get(), side);
            } else {
                throw new RuntimeException(String.format("Parent node not available %s", parent));
            }
        }
    }

    private void parentInsert(BiNode<E> newNode, BiNode<E> parentNode, BiSide side) {
        if (parentNode.equals(newNode)) {
            throw new RuntimeException(String.format("Duplicate value %s", newNode.getData()));
        } else {
            if (side == BiSide.LEFT && parentNode.getLeft() == null) {
                parentNode.setLeft(newNode);
            } else if (side == BiSide.RIGHT && parentNode.getRight() == null) {
                parentNode.setRight(newNode);
            } else {
                throw new RuntimeException(String.format("Parent %s already has %s node", newNode.getData(), side));
            }
        }
    }

    @Override
    public Optional<E> search(E value) {
        Objects.requireNonNull(value);
        Optional<BiNode<E>> sNode = search0((BiNode<E>) getRootNode(), new BiNode<>(value));
        return sNode.map(BiNode::getData);
    }

    private Optional<BiNode<E>> search0(BiNode<E> node, BiNode<E> searchNode) {
        if (node != null) {
            if (node.equals(searchNode)) {
                return Optional.of(node);
            } else {
                Optional<BiNode<E>> leftNode = search0(node.getLeft(), searchNode);
                Optional<BiNode<E>> rightNode = search0(node.getRight(), searchNode);
                return leftNode.isPresent() ? leftNode : rightNode;
            }
        }
        return Optional.empty();
    }

    @Override
    public void remove(E value) {
        Objects.requireNonNull(value);
        setRootNode(delete0((BiNode<E>) getRootNode(), new BiNode<>(value)));
    }

    private BiNode<E> delete0(BiNode<E> node, BiNode<E> toBeDelete) {
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
