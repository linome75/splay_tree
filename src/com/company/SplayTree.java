package com.company;


import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class SplayTree<T extends Comparable<T>> implements Set {
    private static class Node<T> {
        T value;
        Node<T> left, right, parent;

        Node(T value, Node<T> left, Node<T> right, Node<T> parent) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    private Node<T> root = new Node<T>(null, null, null, null);
    private int size = 0;

    public void setParent(Node<T> child, Node<T> parent) {
        if (child != null) child.parent = parent;
    }

    public void keepParent(Node<T> parent) {
        setParent(parent.left, parent);
        setParent(parent.right, parent);
    }

    private void zig(Node<T> child, Node<T> parent) {
        Node<T> grandparent = parent.parent;
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

    private void zigZig(Node<T> child, Node<T> parent) {
        zig(parent, parent.parent);
        zig(child, parent);
    }

    private void zigZag(Node<T> child, Node<T> parent) {
        zig(child, parent);
        zig(child, child.parent);
    }

    private boolean splay(Node<T> node) {
        if (node.parent == null) {
            root = node;
            return true;
        }
        Node<T> parent = node.parent;
        Node<T> grandParent = parent.parent;
        if (grandParent == null) {
            zig(node, parent);
            root = node;
            return true;
        } else {
            if ((grandParent.left == parent && parent.left == node) ||
                    (grandParent.right == parent && parent.right == node)) {
                zigZig(node, parent);
            } else zigZag(node, parent);
        }
        return splay(node);
    }

    public boolean find(T value) {
        return find(root, value);
    }

    private boolean find(Node<T> node, T value) {
        if (node.value == null) return false;
        int comparison = value.compareTo(node.value);
        if (comparison == 0)
            return splay(node);
        if (comparison < 0 && node.left != null)
            return find(node.left, value);
        if (comparison > 0 && node.right != null)
            return find(node.right, value);
        return splay(node);
    }

    private boolean contains(Node<T> node, T value) {
        if (node.value == null) return false;
        int comparison = value.compareTo(node.value);
        if (comparison == 0) return true;
        if (comparison < 0 && node.left != null)
            return contains(node.left, value);
        if (comparison > 0 && node.right != null)
            return contains(node.right, value);
        return false;
    }

    public Node<T> split(T value) {
        return split(root, value);
    }

    private Node<T> split(Node<T> node, T value) {
        if (node == null) return null;
        find(node, value);
        node = root;
        int comparison = value.compareTo(node.value);
        if (comparison == 0) {
            setParent(node.left, null);
            setParent(node.right, null);
            return new Node<T>(value, node.left, node.right, null);
        }
        if (comparison > 0) {
            Node<T> rightNode = node.right;
            setParent(rightNode, null);
            node.right = null;
            return new Node<T>(value, node, rightNode, null);
        } else {
            Node<T> leftNode = node.left;
            setParent(leftNode, null);
            node.left = null;
            return new Node<T>(value, node, leftNode, null);
        }
    }

    public boolean insert(T value) {
        if (!contains(value)) size++;
        return insert(root, value);
    }

    private boolean insert(Node<T> node, T value) {
        if (root.value == null) {
            root.value = value;
            return true;
        }
            node = split(node, value);
            keepParent(node);
        root = node;
        return true;
    }

    public Node<T> merge(Node<T> left, Node<T> right) {
        if (right == null) {
            root = left;
            return left;
        }
        if (left == null) {
            root = right;
            return right;
        }
        find(right, left.value);
        right = root;
        right.left = left;
        left.parent = right;
        return right;
    }

//    public Node<T remove(int key) {
//        if (!findToRemove(root, key)) return null;
//        find(key);
//        setParent(root.left, null);
//        setParent(root.right, null);
//        size--;
//        return merge(root.left, root.right);
//    }

    public void getTree() {
        getTree(root);
    }

    public void getTree(Node<T> node) {
        if (node == null) {
            System.out.println("null");
            return;
        }
        System.out.println(node.value);
        if (node.left != null) {
            System.out.println("left[");
            getTree(node.left);
        }
        if (node.right != null) {
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
        return (root == null);
    }

    @Override
    public boolean contains(Object o) {
        return contains(root, (T) o);
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
        if (!contains((T) o)) {
            size++;
            return insert(root, (T) o);
        } else return find(root, (T) o);
    }

    @Override
    public boolean remove(Object o) {
        T value = (T) o;
        if (!contains(value)) return false;
        find(value);
        setParent(root.left, null);
        setParent(root.right, null);
        size--;
        merge(root.left, root.right);
        return true;
    }

    @Override
    public boolean addAll(Collection c) {
        Object[] arr = c.toArray();
        for (int i = 0; i < arr.length; i++)
            return add(arr[i]);
        return true;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean removeAll(Collection c) {
        Object[] arr = c.toArray();
        for (int i = 0; i < arr.length; i++)
            return remove(arr[i]);
        return true;
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

