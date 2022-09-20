package org.javads.nlinear.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.javads.exception.NodeException;
import org.javads.nlinear.Node;

/**
 * Date: 01/01/22
 * Time: 9:05 pm
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
public class GeneralNaryTree<E> extends AbstractTree<E> implements UnBalanceNaryTree<E> {

    public GeneralNaryTree() {
        super();
    }

    @Override
    public void insert(E value) {
        insert(value, !isEmpty() ? ((GNode<E>) getRootNode()).getData() : value);
    }

    @Override
    public void insert(E value, E parent) {
        insert0(value, parent);
    }

    private void insert0(E value, E parent) {
        GNode<E> nNode = new GNode<>(value);
        GNode<E> parentNode = new GNode<>(parent);
        if (isEmpty()) {
            setRootNode(nNode);
        } else {
            GNode<E> root = (GNode<E>) getRootNode();
            if (root.equals(parentNode)) {
                root.getChildren().add(nNode);
            } else {
                GNode<E> parent0 = findParent(root, parentNode);
                if (parent0 == null) {
                    throw new NodeException(String.format("Could not find parent %s", parent));
                }
                parent0.getChildren().add(nNode);
            }
        }
    }

    private GNode<E> findParent(GNode<E> rootNode, GNode<E> parentNode) {
        GNode<E> pNode = null;
        if (rootNode != null) {
            for (GNode<E> n : rootNode.getChildren()) {
                if (n.equals(parentNode)) {
                    pNode = n;
                    break;
                } else {
                    pNode = findParent(n, parentNode);
                }
            }
        }
        return pNode;
    }

    @Override
    public void remove(E value) {
        GNode<E> tobeDeleted = new GNode<>(value);
        if (!isEmpty()) {
            if (tobeDeleted.equals(getRootNode())) {
                setRootNode(null);
            } else {
                delete0((GNode<E>) getRootNode(), tobeDeleted);
            }
        }
    }

    private void delete0(GNode<E> root, GNode<E> tobeDeleted) {
        if (root != null) {
            for (int i = 0; i < root.getChildren().size(); i++) {
                if (root.getChildren().get(i).equals(tobeDeleted)) {
                    root.getChildren().remove(i);
                    break;
                }
                delete0(root.getChildren().get(i), tobeDeleted);
            }
        }
    }

    @Override
    public Optional<E> search(E value) {
        Optional<GNode<E>> sNode = search0((GNode<E>) getRootNode(), new GNode<>(value));
        return sNode.map(GNode::getData);
    }

    private Optional<GNode<E>> search0(GNode<E> root, GNode<E> tobeSearched) {
        if (root.equals(tobeSearched)) {
            return Optional.of(root);
        } else {
            GNode<E> fNode = null;
            for (int i = 0; i < root.getChildren().size(); i++) {
                if (root.getChildren().get(i).equals(tobeSearched)) {
                    fNode = root.getChildren().get(i);
                    break;
                } else {
                    fNode = search0(root.getChildren().get(i), tobeSearched).orElse(null);
                }
            }
            return Optional.ofNullable(fNode);
        }
    }

    public static class GNode<E> implements Node {
        private E data;
        private final List<GNode<E>> children;

        public GNode(E data) {
            this.data = data;
            this.children = new LinkedList<>();
        }

        public E getData() {
            return data;
        }

        public void setData(E data) {
            this.data = data;
        }

        public List<GNode<E>> getChildren() {
            return children;
        }

        @Override
        public String toString() {
            return this.data != null ? this.data.toString() : null;
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
            GNode<E> node_ = (GNode<E>) obj;
            return Objects.equals(this.data, node_.data);
        }
    }
}
