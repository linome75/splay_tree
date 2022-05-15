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

   private SplayNode splay(SplayNode target) {
        if (target.parent == null) return target;
        SplayNode parent = target.parent;
        SplayNode grandParent = parent.parent;
        if (grandParent == null) {
            zig(target, parent);
            return target;
        } else {
            if ((grandParent.left == parent && parent.left == target)||
                    (grandParent.right == parent && parent.right == target)){
                zigZig(target, parent);
            } else zigZag(target, parent);
        }
        return splay(target);
   }

   public SplayNode find(SplayNode node, int key){
        if (node == null) return null;
        if (key == node.key)
            return splay(node);
        if (key < node.key && node.left != null)
            return find(node.left, key);
        if (key > node.key && node.right != null)
            return find(node.right, key);
        return splay(node);
   }

//   private SplayTree split(SplayNode root, int key){
//        if (root == null) return null;
//   }
}

