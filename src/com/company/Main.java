package com.company;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        SplayTree<Integer> tree = new SplayTree<Integer>();
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(4);
        set.add(5);
        set.add(6);
        tree.addAll(set);
       tree.getTree();
        System.out.println();
        //tree.remove(5);
       // tree.getTree();
        Integer[] asd = new Integer[3];
        System.out.println(tree.toArray(asd)[0]);
        tree.getTree();
//        tree.remove(4);
    }
}
