package org.javads.nlinear.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 24/01/22
 * Time: 12:12 am
 * This file is project specific to java-ds
 * Author: Pramod Khalkar
 */
abstract class AbstractBiTree<E> extends AbstractTree<E> implements BiTree<E> {

    protected AbstractBiTree() {
        super();
    }

    @Override
    public List<E> preOrderTraverse() {
        if (getRootNode() == null) {
            return Collections.emptyList();
        } else {
            List<E> list = new ArrayList<>();
            preOrderTraverse0((BiNode<E>) getRootNode(), list);
            return list;
        }
    }

    protected void preOrderTraverse0(BiNode<E> tNode, List<E> list) {
        if (tNode == null) {
            return;
        }
        list.add(tNode.getData());
        preOrderTraverse0(tNode.getLeft(), list);
        preOrderTraverse0(tNode.getRight(), list);
    }

    @Override
    public List<E> postOrderTraverse() {
        if (getRootNode() == null) {
            return Collections.emptyList();
        } else {
            List<E> list = new ArrayList<>();
            postOrderTraverse0((BiNode<E>) getRootNode(), list);
            return list;
        }
    }

    protected void postOrderTraverse0(BiNode<E> tNode, List<E> list) {
        if (tNode == null) {
            return;
        }
        postOrderTraverse0(tNode.getLeft(), list);
        postOrderTraverse0(tNode.getRight(), list);
        list.add(tNode.getData());
    }


    @Override
    public List<E> inOrderTraverse() {
        if (getRootNode() == null) {
            return Collections.emptyList();
        } else {
            List<E> list = new ArrayList<>();
            inOrderTraverse0((BiNode<E>) getRootNode(), list);
            return list;
        }
    }

    protected void inOrderTraverse0(BiNode<E> tNode, List<E> list) {
        if (tNode == null) {
            return;
        }
        inOrderTraverse0(tNode.getLeft(), list);
        list.add(tNode.getData());
        inOrderTraverse0(tNode.getRight(), list);
    }
}
