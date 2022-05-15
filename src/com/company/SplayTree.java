package com.company;

import jdk.internal.net.http.common.Pair;

public class SplayTree {
    private SplayNode root;

    public SplayTree() {
        root = null;
    }

    public void setParent(SplayNode child, SplayNode parent) {
        if (child != null) child.parent = parent;
    }

    public void keepParent(SplayNode parent) {
        setParent(parent.left, parent);
        setParent(parent.right, parent);
    }

    private void zig(SplayNode child, SplayNode parent) {
        SplayNode grandparent = parent.parent;
        if (grandparent != null) {
            if (grandparent.left == parent)
                grandparent.left = child;
            else grandparent.right = child;
        }
        if (parent.left == child) {
            parent.left = child.right;
            child.right = parent;
        } else {
            parent.right = child.left;
            child.left = parent;
        }
        if (grandparent != null) {
            keepParent(grandparent);
        }
        keepParent(child);
        keepParent(parent);
    }

    private void zigZig(SplayNode child, SplayNode parent) {
        zig(parent, parent.parent);
        zig(child, parent);
    }

    private void zigZag(SplayNode child, SplayNode parent) {
        zig(child, parent);
        zig(child, child.parent);
    }

    private SplayNode splay(SplayNode target) {
        if (target.parent == null) return target;
        SplayNode parent = target.parent;
        SplayNode grandParent = parent.parent;
        if (grandParent == null) {
            zig(target, parent);
            return target;
        } else {
            if ((grandParent.left == parent && parent.left == target) ||
                    (grandParent.right == parent && parent.right == target)) {
                zigZig(target, parent);
            } else zigZag(target, parent);
        }
        return splay(target);
    }

    public SplayNode find(SplayNode node, int key) {
        if (node == null) return null;
        if (key == node.key)
            return splay(node);
        if (key < node.key && node.left != null)
            return find(node.left, key);
        if (key > node.key && node.right != null)
            return find(node.right, key);
        return splay(node);
    }

    public Pair<SplayNode, SplayNode> split(SplayNode node, int key) {
        if (node == null) return null;
        node = find(node, key);
        if (node.key == key) {
            setParent(node.left, null);
            setParent(node.right, null);
            return new Pair<SplayNode, SplayNode>(node.left, node.right);
        }
        if (node.key < key) {
            SplayNode rightNode = node.right;
            setParent(rightNode, null);
            node.right = null;
            return new Pair<SplayNode, SplayNode>(node, rightNode);
        } else {
            SplayNode leftNode = node.left;
            setParent(leftNode, null);
            node.left = null;
            return new Pair<SplayNode, SplayNode>(node, leftNode);
        }
    }
}

