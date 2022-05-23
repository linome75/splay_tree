package com.company;

public class Main {

    public static void main(String[] args) {
        SplayTree<Integer> tree = new SplayTree<Integer>();
        tree.insert(4);
//        tree.remove(4);
        tree.insert(6);
        tree.insert(5);
        tree.getTree();
        System.out.println();
        tree.find(4);
        tree.getTree();
        System.out.println();
//        tree.remove(4);
        tree.getTree();
    }
}
