package com.company;


import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class SplayTree implements Set {
    private SplayNode root;
    private int size;
    public SplayTree() {
        root = null;
    }

    public SplayNode getRoot() {
        return root;
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
        child.parent = grandparent;
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
        if (target.parent == null) {
            root = target;
            return target;
        }
        SplayNode parent = target.parent;
        SplayNode grandParent = parent.parent;
        if (grandParent == null) {
            zig(target, parent);
            root = target;
            return target;
        } else {
            if ((grandParent.left == parent && parent.left == target) ||
                    (grandParent.right == parent && parent.right == target)) {
                zigZig(target, parent);
            } else zigZag(target, parent);
        }
        return splay(target);
    }

    public SplayNode find(int key) {
        return find(root, key);
    }

    private SplayNode find(SplayNode node, int key) {
        if (node == null) return null;
        if (key == node.key)
            return splay(node);
        if (key < node.key && node.left != null)
            return find(node.left, key);
        if (key > node.key && node.right != null)
            return find(node.right, key);
        return splay(node);
    }

    private boolean findToRemove(SplayNode node, int key) {
        if (node.key == key) return true;
        if (key < node.key && node.left != null)
            return findToRemove(node.left, key);
        if (key > node.key && node.right != null)
            return findToRemove(node.right, key);
        return false;
    }

    public SplayNode split(int key) {
        return split(root, key);
    }

    private SplayNode split(SplayNode node, int key) {
        if (node == null) return new SplayNode(key);
        node = find(node, key);
        if (node.key == key) {
            setParent(node.left, null);
            setParent(node.right, null);
            return new SplayNode(key, node.left, node.right, null);
        }
        if (node.key < key) {
            SplayNode rightNode = node.right;
            setParent(rightNode, null);
            node.right = null;
            return new SplayNode(key, node, rightNode, null);
        } else {
            SplayNode leftNode = node.left;
            setParent(leftNode, null);
            node.left = null;
            return new SplayNode(key, node, leftNode, null);
        }
    }

    public SplayNode insert(int key) {
        size++;
        return insert(root, key);
    }

    public SplayNode insert(SplayNode node, int key) {
        node = split(node, key);
        keepParent(node);
        root = node;
        return node;
    }

    public SplayNode merge(SplayNode left, SplayNode right) {
        if (right == null) {
            root = left;
            return left;
        }
        if (left == null) {
            root = right;
            return right;
        }
        right = find(right, left.key);
        right.left = left;
        left.parent = right;
        return right;
    }

    public SplayNode remove(int key) {
        if (!findToRemove(root, key)) return null;
        find(key);
        setParent(root.left, null);
        setParent(root.right, null);
        size--;
        return merge(root.left, root.right);
    }

    public void getTree() {
        getTree(root);
    }

    public void getTree(SplayNode node) {
        System.out.println(node.key);
        if (node.left != null) {
            System.out.println("left[");
            getTree(node.left);
        }
        if (node.right != null){
            System.out.println("right[");
            getTree(node.right);
        }
        System.out.println("]");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}

