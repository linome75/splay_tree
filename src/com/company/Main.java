package com.company;

public class Main {

    public static void main(String[] args) {
        SplayTree tree = new SplayTree();
        tree.insert(4);
        tree.insert(6);
        tree.insert(5);
        tree.getTree();
        System.out.println();
        tree.find(4);
        tree.getTree();
        System.out.println();
        tree.remove(7);
        tree.getTree();
    }
}
