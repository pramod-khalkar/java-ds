package org.javads.tree.unbalance;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import org.javads.tree.AbstractTree;
import org.javads.tree.Node;
import org.javads.tree.NodePosition;

/**
 * Date: 04/01/22
 * Time: 11:32 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class BinaryTree<E> extends AbstractTree<E> {

    private final Random random;

    public BinaryTree() {
        super();
        random = new Random();
    }

    @Override
    public Node<E> insert(E value) {
        Node<E> newNode = super.insert(value);
        Node<E> tNode = randomInsert(getRootNode(), newNode, (random.nextInt(2) == 0) ? NodePosition.LEFT : NodePosition.RIGHT);
        setRootNode(tNode);
        return newNode;
    }

    private Node<E> randomInsert(Node<E> node, Node<E> newNode, NodePosition nodePosition) {
        if (node == null) {
            return newNode;
        } else if (nodePosition == NodePosition.LEFT) {
            if (node.getLeft() == null) {
                node.setLeft(randomInsert(node.getLeft(), newNode, nodePosition));
            } else {
                node.setRight(randomInsert(node.getRight(), newNode, nodePosition));
            }
        } else if (nodePosition == NodePosition.RIGHT) {
            if (node.getRight() == null) {
                node.setRight(randomInsert(node.getRight(), newNode, nodePosition));
            } else {
                node.setLeft(randomInsert(node.getLeft(), newNode, nodePosition));
            }
        } else {
            throw new RuntimeException(String.format("Duplicate value %s", newNode.getData()));
        }
        return node;
    }

    public Node<E> insert(E value, E parent, NodePosition nodePosition) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(parent);
        Node<E> newNode = new Node<>(value);
        if (isEmpty()) {
            setRootNode(newNode);
        } else {
            Optional<Node<E>> parentNode = search0(getRootNode(), new Node<>(parent));
            if (parentNode.isPresent()) {
                parentInsert(newNode, parentNode.get(), nodePosition);
            } else {
                throw new RuntimeException(String.format("Parent node not available %s", parent));
            }
        }
        return newNode;
    }

    private void parentInsert(Node<E> newNode, Node<E> parentNode, NodePosition nodePosition) {
        if (parentNode.equals(newNode)) {
            throw new RuntimeException(String.format("Duplicate value %s", newNode.getData()));
        } else {
            if (nodePosition == NodePosition.LEFT && parentNode.getLeft() == null) {
                parentNode.setLeft(newNode);
            } else if (nodePosition == NodePosition.RIGHT && parentNode.getRight() == null) {
                parentNode.setRight(newNode);
            } else {
                throw new RuntimeException(String.format("Parent %s already has %s node", newNode.getData(), nodePosition));
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

    @Override
    public void insert(E... elements) {

    }

    @Override
    public void delete(E... elements) {

    }

    @Override
    public Stream<E> preOrderTraverseStream() {
        return null;
    }

    @Override
    public Stream<E> inOrderTraverseStream() {
        return null;
    }

    @Override
    public Stream<E> postOrderTraverseStream() {
        return null;
    }

    @Override
    public List<E> preOrderTraverse() {
        return null;
    }

    @Override
    public List<E> postOrderTraverse() {
        return null;
    }

    @Override
    public List<E> inOrderTraverse() {
        return null;
    }
}
