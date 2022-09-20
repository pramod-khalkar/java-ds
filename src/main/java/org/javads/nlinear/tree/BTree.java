package org.javads.nlinear.tree;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.javads.nlinear.Node;

/**
 * @author : Pramod Khalkar
 * @since : 27/08/22, Sat
 * description: This file belongs to java-ds
 * Source : https://www.codeproject.com/script/Articles/ViewDownloads.aspx?aid=1158559
 **/
public class BTree<E extends Comparable<? super E>> extends AbstractTree<E> {
    public final static int REBALANCE_FOR_LEAF_NODE = 1;
    public final static int REBALANCE_FOR_INTERNAL_NODE = 2;
    private BTNode<E> mIntermediateInternalNode = null;
    private int mNodeIdx = 0;

    public BTree() {
    }

    @Override
    public void insert(E value) {
        if (getRootNode() == null) {
            setRootNode(createDefaultNode());
        }
        //If node full with upper limit then split it
        BTNode<E> root = (BTNode<E>) getRootNode();
        if (root.currentIndex == BTNode.UPPER_BOUND) {
            //the root is full split it
            BTNode<E> btNode = createDefaultNode();
            btNode.isLeaf = false;
            btNode.children[0] = root;
            root = btNode;
            setRootNode(root);
            splitNode(root, 0, btNode.children[0]);
        }
        insertDataAtNode(root, value);
    }

    //insert value at specified root
    private void insertDataAtNode(BTNode rootNode, E value) {
        int i;
        int currentKeyNum = rootNode.currentIndex;
        if (rootNode.isLeaf) {
            if (rootNode.currentIndex == 0) {
                //empty root
                rootNode.data[0] = new Data<>(value);
                ++(rootNode.currentIndex);
                return;
            }
            //check for duplicate value
            for (i = 0; i < rootNode.currentIndex; ++i) {
                if (value.compareTo((E) rootNode.data[i].value) == 0) {
                    // Find existing value/data, overwrite its value only
                    rootNode.data[i].value = value;
                    return;
                }
            }
            i = currentKeyNum - 1;
            Data<E> existingValue = (Data<E>) rootNode.data[i];
            while ((i > -1) && (value.compareTo(existingValue.value) < 0)) {
                rootNode.data[i + 1] = existingValue;
                --i;
                if (i > -1) {
                    existingValue = rootNode.data[i];
                }
            }
            i = i + 1;
            rootNode.data[i] = new Data<>(value);
            ++(rootNode.currentIndex);
            return;
        }
        //below for internal node, not so need to find child node where it is belong
        i = 0;
        int noOfKeys = rootNode.currentIndex;
        Data<E> currentKey = rootNode.data[i];
        while ((i < noOfKeys) && (value.compareTo(currentKey.value) > 0)) {
            ++i;
            if (i < noOfKeys) {
                currentKey = rootNode.data[i];
            } else {
                --i;
                break;
            }
        }
        if ((i < noOfKeys) && (value.compareTo(currentKey.value) == 0)) {
            // The key already existed so replacing it
            currentKey.value = value;
            return;
        }
        BTNode<E> btNode;
        if (value.compareTo(currentKey.value) > 0) {
            btNode = BTNode.getRightChildAtIndex(rootNode, i);
            i = i + 1;
        } else {
            if ((i - 1 >= 0) && (value.compareTo((E) rootNode.data[i - 1].value) > 0)) {
                btNode = BTNode.getRightChildAtIndex(rootNode, i - 1);
            } else {
                btNode = BTNode.getLeftChildAtIndex(rootNode, i);
            }
        }
        if (btNode.currentIndex == BTNode.UPPER_BOUND) {
            //if child node is full then split it and insert at starting
            splitNode(rootNode, i, btNode);
            insertDataAtNode(rootNode, value);
            return;
        }
        insertDataAtNode(btNode, value);
    }

    // Split a child with respect to its parent at a specified node
    private void splitNode(BTNode parentNode, int nodeIdx, BTNode btNode) {
        int i;
        BTNode<E> newNode = createDefaultNode();
        newNode.isLeaf = btNode.isLeaf;

        // Since the node is full,
        // new node must share LOWER_BOUND (aka t - 1) value from the node
        newNode.currentIndex = BTNode.LOWER_BOUND;

        // Copy right half of the keys from the node to the new node
        for (i = 0; i < BTNode.LOWER_BOUND; ++i) {
            newNode.data[i] = btNode.data[i + BTNode.MIN_DEGREE];
            btNode.data[i + BTNode.MIN_DEGREE] = null;
        }

        // If the node is an internal node (not a leaf),
        // copy the its child pointers at the half right as well
        if (!btNode.isLeaf) {
            for (i = 0; i < BTNode.MIN_DEGREE; ++i) {
                newNode.children[i] = btNode.children[i + BTNode.MIN_DEGREE];
                btNode.children[i + BTNode.MIN_DEGREE] = null;
            }
        }

        // The node at this point should have LOWER_BOUND (aka min degree - 1) value at this point.
        // We will move its right-most key to its parent node later.
        btNode.currentIndex = BTNode.LOWER_BOUND;

        // Do the right shift for relevant child pointers of the parent node
        // so that we can put the new node as its new child pointer
        for (i = parentNode.currentIndex; i > nodeIdx; --i) {
            parentNode.children[i + 1] = parentNode.children[i];
            parentNode.children[i] = null;
        }
        parentNode.children[nodeIdx + 1] = newNode;

        // Do the right shift all the keys of the parent node the right side of the node index as well
        // so that we will have a slot for move a median key from the splitted node
        for (i = parentNode.currentIndex - 1; i >= nodeIdx; --i) {
            parentNode.data[i + 1] = parentNode.data[i];
            parentNode.data[i] = null;
        }
        parentNode.data[nodeIdx] = btNode.data[BTNode.LOWER_BOUND];
        btNode.data[BTNode.LOWER_BOUND] = null;
        ++(parentNode.currentIndex);
    }

    private BTNode<E> createDefaultNode() {
        BTNode<E> btNode = new BTNode<>(null);
        btNode.isLeaf = true;
        btNode.currentIndex = 0;
        return btNode;
    }

    @Override
    public Optional<E> search(E value) {
        E result = search0(value);
        return Optional.ofNullable(result);
    }

    private E search0(E value) {
        BTNode<E> currentNode = (BTNode<E>) getRootNode();
        Data<E> currentKey;
        int i, numberOfKeys;
        while (currentNode != null) {
            numberOfKeys = currentNode.currentIndex;
            i = 0;
            currentKey = currentNode.data[i];
            while ((i < numberOfKeys) && (value.compareTo(currentKey.value) > 0)) {
                ++i;
                if (i < numberOfKeys) {
                    currentKey = currentNode.data[i];
                } else {
                    --i;
                    break;
                }
            }

            if ((i < numberOfKeys) && (value.compareTo(currentKey.value) == 0)) {
                return currentKey.value;
            }

            if (value.compareTo(currentKey.value) > 0) {
                currentNode = BTNode.getRightChildAtIndex(currentNode, i);
            } else {
                currentNode = BTNode.getLeftChildAtIndex(currentNode, i);
            }
        }
        return null;
    }

    @Override
    public void remove(E value) {
        mIntermediateInternalNode = null;
        Data<E> data = deleteKey(null, (BTNode<E>) getRootNode(), value, 0);
    }

    private Data<E> deleteKey(BTNode<E> parentNode, BTNode<E> btNode, E key, int nodeIdx) {
        int i;
        int nIdx;
        Data<E> retVal;
        if (btNode == null) {
            // The tree is empty
            return null;
        }
        if (btNode.isLeaf) {
            nIdx = searchKey(btNode, key);
            if (nIdx < 0) {
                // Can't find the specified key
                return null;
            }
            retVal = btNode.data[nIdx];
            if ((btNode.currentIndex > BTNode.LOWER_BOUND) || (parentNode == null)) {
                // Remove it from the node
                for (i = nIdx; i < btNode.currentIndex - 1; ++i) {
                    btNode.data[i] = btNode.data[i + 1];
                }
                btNode.data[i] = null;
                --(btNode.currentIndex);
                if (btNode.currentIndex == 0) {
                    // btNode is actually the root node
                    // mRoot = null;
                    setRootNode(null);
                }
                return retVal;
            }
            // Find the left sibling
            BTNode<E> rightSibling;
            BTNode<E> leftSibling = BTNode.getLeftSiblingAtIndex(parentNode, nodeIdx);
            if ((leftSibling != null) && (leftSibling.currentIndex > BTNode.LOWER_BOUND)) {
                // Remove the key and borrow a key from the left sibling
                moveLeftLeafSiblingKeyWithKeyRemoval(btNode, nodeIdx, nIdx, parentNode, leftSibling);
            } else {
                rightSibling = BTNode.getRightSiblingAtIndex(parentNode, nodeIdx);
                if ((rightSibling != null) && (rightSibling.currentIndex > BTNode.LOWER_BOUND)) {
                    // Remove a key and borrow a key the right sibling
                    moveRightLeafSiblingKeyWithKeyRemoval(btNode, nodeIdx, nIdx, parentNode, rightSibling);
                } else {
                    // Merge to its sibling
                    boolean isRebalanceNeeded = false;
                    boolean bStatus;
                    if (leftSibling != null) {
                        // Merge with the left sibling
                        bStatus = doLeafSiblingMergeWithKeyRemoval(btNode, nodeIdx, nIdx, parentNode, leftSibling, false);
                        if (!bStatus) {
                            isRebalanceNeeded = false;
                        } else if (parentNode.currentIndex < BTNode.LOWER_BOUND) {
                            // Need to rebalance the tree
                            isRebalanceNeeded = true;
                        }
                    } else {
                        // Merge with the right sibling
                        bStatus = doLeafSiblingMergeWithKeyRemoval(btNode, nodeIdx, nIdx, parentNode, rightSibling, true);
                        if (!bStatus) {
                            isRebalanceNeeded = false;
                        } else if (parentNode.currentIndex < BTNode.LOWER_BOUND) {
                            // Need to rebalance the tree
                            isRebalanceNeeded = true;
                        }
                    }
                    if (isRebalanceNeeded && (getRootNode() != null)) {
                        rebalanceTree((BTNode<E>) getRootNode(), parentNode, parentNode.data[0].value);
                    }
                }
            }
            return retVal;  // Done with handling for the leaf node
        }

        //
        // At this point the node is an internal node
        //
        nIdx = searchKey(btNode, key);
        if (nIdx >= 0) {
            // We found the key in the internal node
            // Find its predecessor
            mIntermediateInternalNode = btNode;
            mNodeIdx = nIdx;
            BTNode<E> predecessorNode = findPredecessor(btNode, nIdx);
            Data<E> predecessorKey = predecessorNode.data[predecessorNode.currentIndex - 1];

            // Swap the data of the deleted key and its predecessor (in the leaf node)
            Data<E> deletedKey = btNode.data[nIdx];
            btNode.data[nIdx] = predecessorKey;
            predecessorNode.data[predecessorNode.currentIndex - 1] = deletedKey;

            // mIntermediateNode is done in findPrecessor
            return deleteKey(mIntermediateInternalNode, predecessorNode, deletedKey.value, mNodeIdx);
        }
        //
        // Find the child subtree (node) that contains the key
        //
        i = 0;
        Data<E> currentKey = btNode.data[0];
        while ((i < btNode.currentIndex) && (key.compareTo(currentKey.value) > 0)) {
            ++i;
            if (i < btNode.currentIndex) {
                currentKey = btNode.data[i];
            } else {
                --i;
                break;
            }
        }
        BTNode<E> childNode;
        if (key.compareTo(currentKey.value) > 0) {
            childNode = BTNode.getRightChildAtIndex(btNode, i);
            if (childNode.data[0].value.compareTo(btNode.data[btNode.currentIndex - 1].value) > 0) {
                // The right-most side of the node
                i = i + 1;
            }
        } else {
            childNode = BTNode.getLeftChildAtIndex(btNode, i);
        }
        return deleteKey(btNode, childNode, key, i);
    }

    //
    // Remove the specified key and move a key from the right leaf sibling to the node
    // Note: The node and its sibling must be leaves
    //
    private void moveRightLeafSiblingKeyWithKeyRemoval(BTNode<E> btNode,
                                                       int nodeIdx,
                                                       int keyIdx,
                                                       BTNode<E> parentNode,
                                                       BTNode<E> rightSiblingNode) {
        // Shift to the right where the key is deleted
        for (int i = keyIdx; i < btNode.currentIndex - 1; ++i) {
            btNode.data[i] = btNode.data[i + 1];
        }
        btNode.data[btNode.currentIndex - 1] = parentNode.data[nodeIdx];
        parentNode.data[nodeIdx] = rightSiblingNode.data[0];

        for (int i = 0; i < rightSiblingNode.currentIndex - 1; ++i) {
            rightSiblingNode.data[i] = rightSiblingNode.data[i + 1];
        }
        --(rightSiblingNode.currentIndex);
    }

    //
    // Remove the specified key and move a key from the left leaf sibling to the node
    // Note: The node and its sibling must be leaves
    //
    private void moveLeftLeafSiblingKeyWithKeyRemoval(BTNode<E> btNode,
                                                      int nodeIdx,
                                                      int keyIdx,
                                                      BTNode<E> parentNode,
                                                      BTNode<E> leftSiblingNode) {
        // Use the parent key on the left side of the node
        nodeIdx = nodeIdx - 1;
        // Shift to the right to where the key will be deleted
        for (int i = keyIdx; i > 0; --i) {
            btNode.data[i] = btNode.data[i - 1];
        }
        btNode.data[0] = parentNode.data[nodeIdx];
        parentNode.data[nodeIdx] = leftSiblingNode.data[leftSiblingNode.currentIndex - 1];
        --(leftSiblingNode.currentIndex);
    }

    //
    // Do the leaf sibling merge
    // Return true if we need to perform futher re-balancing action
    // Return false if we reach and update the root hence we don't need to go futher for re-balancing the tree
    //
    private boolean doLeafSiblingMergeWithKeyRemoval(BTNode<E> btNode,
                                                     int nodeIdx,
                                                     int keyIdx,
                                                     BTNode<E> parentNode,
                                                     BTNode<E> siblingNode,
                                                     boolean isRightSibling) {
        int i;
        if (nodeIdx == parentNode.currentIndex) {
            // Case node index can be the right most
            nodeIdx = nodeIdx - 1;
        }
        if (isRightSibling) {
            // Shift the remained keys of the node to the left to remove the key
            for (i = keyIdx; i < btNode.currentIndex - 1; ++i) {
                btNode.data[i] = btNode.data[i + 1];
            }
            btNode.data[i] = parentNode.data[nodeIdx];
        } else {
            // Here we need to determine the parent node id based on child node id (nodeIdx)
            if (nodeIdx > 0) {
                if (siblingNode.data[siblingNode.currentIndex - 1].value.compareTo(parentNode.data[nodeIdx - 1].value) < 0) {
                    nodeIdx = nodeIdx - 1;
                }
            }
            siblingNode.data[siblingNode.currentIndex] = parentNode.data[nodeIdx];
            // siblingNode.mKeys[siblingNode.mCurrentKeyNum] = parentNode.mKeys[0];
            ++(siblingNode.currentIndex);

            // Shift the remained keys of the node to the left to remove the key
            for (i = keyIdx; i < btNode.currentIndex - 1; ++i) {
                btNode.data[i] = btNode.data[i + 1];
            }
            btNode.data[i] = null;
            --(btNode.currentIndex);
        }

        if (isRightSibling) {
            for (i = 0; i < siblingNode.currentIndex; ++i) {
                btNode.data[btNode.currentIndex + i] = siblingNode.data[i];
                siblingNode.data[i] = null;
            }
            btNode.currentIndex += siblingNode.currentIndex;
        } else {
            for (i = 0; i < btNode.currentIndex; ++i) {
                siblingNode.data[siblingNode.currentIndex + i] = btNode.data[i];
                btNode.data[i] = null;
            }
            siblingNode.currentIndex += btNode.currentIndex;
            btNode.data[btNode.currentIndex] = null;
        }

        // Shift the parent keys accordingly after the merge of child nodes
        for (i = nodeIdx; i < parentNode.currentIndex - 1; ++i) {
            parentNode.data[i] = parentNode.data[i + 1];
            parentNode.children[i + 1] = parentNode.children[i + 2];
        }
        parentNode.data[i] = null;
        parentNode.children[parentNode.currentIndex] = null;
        --(parentNode.currentIndex);

        if (isRightSibling) {
            parentNode.children[nodeIdx] = btNode;
        } else {
            parentNode.children[nodeIdx] = siblingNode;
        }

        if ((getRootNode() == parentNode) && (((BTNode<E>) getRootNode()).currentIndex == 0)) {
            // Only root left
            setRootNode(parentNode.children[nodeIdx]);
            ((BTNode<E>) getRootNode()).isLeaf = true;
            return false;  // Root has been changed, we don't need to go futher
        }
        return true;
    }

    //
    // Re-balance the tree upward from the lower node to the upper node
    //
    private void rebalanceTree(BTNode<E> upperNode, BTNode<E> lowerNode, E key) {
        //
        // Find the child subtree (node) that contains the key
        //
        BTNode<E> parentNode, childNode;
        Data<E> currentKey;
        int i;
        parentNode = upperNode;
        while ((parentNode != lowerNode) && !parentNode.isLeaf) {
            currentKey = parentNode.data[0];
            i = 0;
            while ((i < parentNode.currentIndex) && (key.compareTo(currentKey.value) > 0)) {
                ++i;
                if (i < parentNode.currentIndex) {
                    currentKey = parentNode.data[i];
                } else {
                    --i;
                    break;
                }
            }
            if (key.compareTo(currentKey.value) > 0) {
                childNode = BTNode.getRightChildAtIndex(parentNode, i);
                if (childNode.data[0].value.compareTo(parentNode.data[parentNode.currentIndex - 1].value) > 0) {
                    // The right-most side of the node
                    i = i + 1;
                }
            } else {
                childNode = BTNode.getLeftChildAtIndex(parentNode, i);
            }

            if (childNode == null) {
                break;
            }

            if (key.compareTo(currentKey.value) == 0) {
                break;
            }
            parentNode = childNode;
        }
    }

    // Find the predecessor node for a specified node
    //
    private BTNode<E> findPredecessor(BTNode<E> btNode, int nodeIdx) {
        if (btNode.isLeaf) {
            return btNode;
        }
        BTNode<E> predecessorNode;
        if (nodeIdx > -1) {
            predecessorNode = BTNode.getLeftChildAtIndex(btNode, nodeIdx);
            if (predecessorNode != null) {
                mIntermediateInternalNode = btNode;
                mNodeIdx = nodeIdx;
                btNode = findPredecessor(predecessorNode, -1);
            }
            return btNode;
        }
        predecessorNode = BTNode.getRightChildAtIndex(btNode, btNode.currentIndex - 1);
        if (predecessorNode != null) {
            mIntermediateInternalNode = btNode;
            mNodeIdx = btNode.currentIndex;
            btNode = findPredecessorForNode(predecessorNode, -1);
        }
        return btNode;
    }

    //
    // Find predecessor node of a specified node
    //
    private BTNode<E> findPredecessorForNode(BTNode<E> btNode, int keyIdx) {
        BTNode<E> predecessorNode;
        BTNode<E> originalNode = btNode;
        if (keyIdx > -1) {
            predecessorNode = BTNode.getLeftChildAtIndex(btNode, keyIdx);
            if (predecessorNode != null) {
                btNode = findPredecessorForNode(predecessorNode, -1);
                rebalanceTreeAtNode(originalNode, predecessorNode, keyIdx, REBALANCE_FOR_LEAF_NODE);
            }
            return btNode;
        }
        predecessorNode = BTNode.getRightChildAtIndex(btNode, btNode.currentIndex - 1);
        if (predecessorNode != null) {
            btNode = findPredecessorForNode(predecessorNode, -1);
            rebalanceTreeAtNode(originalNode, predecessorNode, keyIdx, REBALANCE_FOR_LEAF_NODE);
        }
        return btNode;
    }

    //
    // Re-balance the tree at a specified node
    // Params:
    // parentNode = the parent node of the node needs to be re-balanced
    // btNode = the node needs to be re-balanced
    // nodeIdx = the index of the parent node's child array where the node belongs
    // balanceType = either REBALANCE_FOR_LEAF_NODE or REBALANCE_FOR_INTERNAL_NODE
    //   REBALANCE_FOR_LEAF_NODE: the node is a leaf
    //   REBALANCE_FOR_INTERNAL_NODE: the node is an internal node
    // Return:
    // true if it needs to continue rebalancing further
    // false if further rebalancing is no longer needed
    //
    private boolean rebalanceTreeAtNode(BTNode<E> parentNode, BTNode<E> btNode, int nodeIdx, int balanceType) {
        if (balanceType == REBALANCE_FOR_LEAF_NODE) {
            if ((btNode == null) || (btNode.equals((BTNode<E>) getRootNode()))) {
                return false;
            }
        } else if (balanceType == REBALANCE_FOR_INTERNAL_NODE) {
            if (parentNode == null) {
                // Root node
                return false;
            }
        }
        if (btNode.currentIndex >= BTNode.LOWER_BOUND) {
            // The node doesn't need to rebalance
            return false;
        }
        BTNode<E> rightSiblingNode;
        BTNode<E> leftSiblingNode = BTNode.getLeftSiblingAtIndex(parentNode, nodeIdx);
        if ((leftSiblingNode != null) && (leftSiblingNode.currentIndex > BTNode.LOWER_BOUND)) {
            // Do right rotate
            performRightRotation(btNode, nodeIdx, parentNode, leftSiblingNode);
        } else {
            rightSiblingNode = BTNode.getRightSiblingAtIndex(parentNode, nodeIdx);
            if ((rightSiblingNode != null) && (rightSiblingNode.currentIndex > BTNode.LOWER_BOUND)) {
                // Do left rotate
                performLeftRotation(btNode, nodeIdx, parentNode, rightSiblingNode);
            } else {
                // Merge the node with one of the siblings
                boolean bStatus;
                if (leftSiblingNode != null) {
                    bStatus = performMergeWithLeftSibling(btNode, nodeIdx, parentNode, leftSiblingNode);
                } else {
                    bStatus = performMergeWithRightSibling(btNode, nodeIdx, parentNode, rightSiblingNode);
                }
                if (!bStatus) {
                    return false;
                }
            }
        }
        return true;
    }

    //
    // Do the left rotation
    //
    private void performLeftRotation(BTNode<E> btNode, int nodeIdx, BTNode<E> parentNode, BTNode<E> rightSiblingNode) {
        int parentKeyIdx = nodeIdx;
        // Move the parent key and relevant child to the deficient node
        btNode.data[btNode.currentIndex] = parentNode.data[parentKeyIdx];
        btNode.children[btNode.currentIndex + 1] = rightSiblingNode.children[0];
        ++(btNode.currentIndex);

        // Move the leftmost key of the right sibling and relevant child pointer to the parent node
        parentNode.data[parentKeyIdx] = rightSiblingNode.data[0];
        --(rightSiblingNode.currentIndex);
        // Shift all keys and children of the right sibling to its left
        for (int i = 0; i < rightSiblingNode.currentIndex; ++i) {
            rightSiblingNode.data[i] = rightSiblingNode.data[i + 1];
            rightSiblingNode.children[i] = rightSiblingNode.children[i + 1];
        }
        rightSiblingNode.children[rightSiblingNode.currentIndex] = rightSiblingNode.children[rightSiblingNode.currentIndex + 1];
        rightSiblingNode.children[rightSiblingNode.currentIndex + 1] = null;
    }

    //
    // Search the specified key within a node
    // Return index of the keys if it finds
    // Return -1 otherwise
    //
    private int searchKey(BTNode<E> btNode, E key) {
        for (int i = 0; i < btNode.currentIndex; ++i) {
            if (key.compareTo(btNode.data[i].value) == 0) {
                return i;
            } else if (key.compareTo(btNode.data[i].value) < 0) {
                return -1;
            }
        }
        return -1;
    }

    //
    // Do the right sibling merge
    // Return true if it should continue further
    // Return false if it is done
    //
    private boolean performMergeWithRightSibling(BTNode<E> btNode, int nodeIdx, BTNode<E> parentNode, BTNode<E> rightSiblingNode) {
        // Copy the parent key to right-most slot of the node
        btNode.data[btNode.currentIndex] = parentNode.data[nodeIdx];
        ++(btNode.currentIndex);

        // Copy keys and children of the right sibling to the node
        for (int i = 0; i < rightSiblingNode.currentIndex; ++i) {
            btNode.data[btNode.currentIndex + i] = rightSiblingNode.data[i];
            btNode.children[btNode.currentIndex + i] = rightSiblingNode.children[i];
        }
        btNode.currentIndex += rightSiblingNode.currentIndex;
        btNode.children[btNode.currentIndex] = rightSiblingNode.children[rightSiblingNode.currentIndex];
        rightSiblingNode.currentIndex = 0;  // Abandon the sibling node

        // Shift all relevant keys and children of the parent node to the left
        // since it lost one of its keys and children (by moving it to the child node)
        int i;
        for (i = nodeIdx; i < parentNode.currentIndex - 1; ++i) {
            parentNode.data[i] = parentNode.data[i + 1];
            parentNode.children[i + 1] = parentNode.children[i + 2];
        }
        parentNode.data[i] = null;
        parentNode.children[parentNode.currentIndex] = null;
        --(parentNode.currentIndex);

        // Make sure the parent point to the correct child after the merge
        parentNode.children[nodeIdx] = btNode;

        if ((parentNode.equals(getRootNode())) && (parentNode.currentIndex == 0)) {
            // Root node is updated.  It should be done
            setRootNode(btNode);
            return false;
        }
        return true;
    }

    //
    // Do the right rotation
    //
    private void performRightRotation(BTNode<E> btNode, int nodeIdx, BTNode<E> parentNode, BTNode<E> leftSiblingNode) {
        int parentKeyIdx = nodeIdx;
        if (nodeIdx >= parentNode.currentIndex) {
            // This shouldn't happen
            parentKeyIdx = nodeIdx - 1;
        }

        // Shift all keys and children of the deficient node to the right
        // So that there will be available left slot for insertion
        btNode.children[btNode.currentIndex + 1] = btNode.children[btNode.currentIndex];
        for (int i = btNode.currentIndex - 1; i >= 0; --i) {
            btNode.data[i + 1] = btNode.data[i];
            btNode.children[i + 1] = btNode.children[i];
        }

        // Move the parent key and relevant child to the deficient node
        btNode.data[0] = parentNode.data[parentKeyIdx];
        btNode.children[0] = leftSiblingNode.children[leftSiblingNode.currentIndex];
        ++(btNode.currentIndex);

        // Move the leftmost key of the right sibling and relevant child pointer to the parent node
        parentNode.data[parentKeyIdx] = leftSiblingNode.data[leftSiblingNode.currentIndex - 1];
        leftSiblingNode.children[leftSiblingNode.currentIndex] = null;
        --(leftSiblingNode.currentIndex);
    }

    //
    // Do a left sibling merge
    // Return true if it should continue further
    // Return false if it is done
    //
    private boolean performMergeWithLeftSibling(BTNode<E> btNode, int nodeIdx, BTNode<E> parentNode, BTNode<E> leftSiblingNode) {
        if (nodeIdx == parentNode.currentIndex) {
            // For the case that the node index can be the right most
            nodeIdx = nodeIdx - 1;
        }
        // Here we need to determine the parent node's index based on child node's index (nodeIdx)
        if (nodeIdx > 0) {
            if (leftSiblingNode.data[leftSiblingNode.currentIndex - 1].value.compareTo(parentNode.data[nodeIdx - 1].value) < 0) {
                nodeIdx = nodeIdx - 1;
            }
        }

        // Copy the parent key to the node (on the left)
        leftSiblingNode.data[leftSiblingNode.currentIndex] = parentNode.data[nodeIdx];
        ++(leftSiblingNode.currentIndex);

        // Copy keys and children of the node to the left sibling node
        for (int i = 0; i < btNode.currentIndex; ++i) {
            leftSiblingNode.data[leftSiblingNode.currentIndex + i] = btNode.data[i];
            leftSiblingNode.children[leftSiblingNode.currentIndex + i] = btNode.children[i];
            btNode.data[i] = null;
        }
        leftSiblingNode.currentIndex += btNode.currentIndex;
        leftSiblingNode.children[leftSiblingNode.currentIndex] = btNode.children[btNode.currentIndex];
        btNode.currentIndex = 0;  // Abandon the node

        // Shift all relevant keys and children of the parent node to the left
        // since it lost one of its keys and children (by moving it to the child node)
        int i;
        for (i = nodeIdx; i < parentNode.currentIndex - 1; ++i) {
            parentNode.data[i] = parentNode.data[i + 1];
            parentNode.children[i + 1] = parentNode.children[i + 2];
        }
        parentNode.data[i] = null;
        parentNode.children[parentNode.currentIndex] = null;
        --(parentNode.currentIndex);

        // Make sure the parent point to the correct child after the merge
        parentNode.children[nodeIdx] = leftSiblingNode;

        if ((parentNode.equals(getRootNode())) && (parentNode.currentIndex == 0)) {
            // Root node is updated.  It should be done
            setRootNode(leftSiblingNode);
            return false;
        }
        return true;
    }


    public static class BTNode<E> implements Node {
        public static final int MIN_DEGREE = 2;// TODO: 29/08/22 Degree should be able to set from constructor
        public static final int LOWER_BOUND = MIN_DEGREE - 1;
        public static final int UPPER_BOUND = (MIN_DEGREE * 2) - 1;
        public boolean isLeaf;
        public int currentIndex;
        public Data<E>[] data;
        public BTNode[] children;

        public BTNode(E data) {
            this.data = new Data[UPPER_BOUND];
            this.isLeaf = true;
            this.currentIndex = 0;
            this.children = new BTNode[UPPER_BOUND + 1];
        }

        protected static BTNode getChildNodeAtIndex(BTNode btNode, int keyIdx, int nDirection) {
            if (btNode.isLeaf) {
                return null;
            }
            keyIdx += nDirection;
            if ((keyIdx < 0) || (keyIdx > btNode.currentIndex)) {
                return null;
            }
            return btNode.children[keyIdx];
        }

        protected static BTNode getLeftChildAtIndex(BTNode btNode, int keyIdx) {
            return getChildNodeAtIndex(btNode, keyIdx, 0);
        }

        protected static BTNode getRightChildAtIndex(BTNode btNode, int keyIdx) {
            return getChildNodeAtIndex(btNode, keyIdx, 1);
        }

        protected static BTNode getLeftSiblingAtIndex(BTNode parentNode, int keyIdx) {
            return getChildNodeAtIndex(parentNode, keyIdx, -1);
        }

        protected static BTNode getRightSiblingAtIndex(BTNode parentNode, int keyIdx) {
            return getChildNodeAtIndex(parentNode, keyIdx, 1);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("values").append(valuesToString(this.data));
            String children = (this.children != null && this.children.length > 0)
                    ? Arrays.stream(this.children)
                    .filter(Objects::nonNull)
                    .map(v -> v.data)
                    .map(this::valuesToString)
                    .collect(Collectors.joining(",", "[", "]")) : "[]";
            sb.append("children").append(children);
            return sb.toString();
        }

        private String valuesToString(Data[] values) {
            return (this.data != null && this.data.length > 0)
                    ? Arrays.stream(this.data)
                    .filter(Objects::nonNull)
                    .map(Data::toString)
                    .collect(Collectors.joining(",", "[", "]")) : "[]";
        }
    }

    public static class Data<E> {
        protected E value;

        public Data(E value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value != null ? this.value.toString() : null;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }
    }
}
