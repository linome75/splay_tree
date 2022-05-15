package com.company;

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
        keepParent(grandparent);
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

//
//    public void rotate(SplayNode parent, SplayNode child) {
//        SplayNode keeper;
//        SplayNode grandparent = parent.parent;
//        if (grandparent != null) {
//            if (grandparent.left == parent)
//                grandparent.left = child;
//            else grandparent.right = child;
//        }
//        if (parent.left == child) {
//            keeper = parent.left;
//            parent.left = child.right;
//            child.right = keeper;
//        } else {
//            keeper = parent.right;
//            parent.right = child.left;
//            child.left = keeper;
//        }
//        keepParent(child);
//        keepParent(parent);
//        child.parent = grandparent;
//
//    }
}

