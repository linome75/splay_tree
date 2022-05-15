package com.company;

class SplayNode {
    SplayNode left, right, parent;
    int key;

    public SplayNode() {
        this(0, null, null, null);
    }

    public SplayNode(int value) {
        this(value, null, null, null);
    }

    public SplayNode(int value, SplayNode left, SplayNode right, SplayNode parent) {
        this.key = value;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

}
