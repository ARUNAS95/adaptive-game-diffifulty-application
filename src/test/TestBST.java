package test;

import datastructures.bst.BalancedBST;

public class TestBST {
    public static void main(String[] args) {
        BalancedBST<Integer> bst = new BalancedBST<>();

        bst.insert(5);
        bst.insert(2);
        bst.insert(8);
        bst.insert(1);
        bst.insert(3);

        System.out.println("Inorder traversal of BST:");
        bst.inorderTraversal();
    }
}
