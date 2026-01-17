package datastructures.bst;

/**
 * Node used in the Balanced BST (AVL Tree) structure.
 */
public class BSTNode<T extends Comparable<T>> {
    public T data;
    public BSTNode<T> left, right;
    public int height;

    public BSTNode(T data) {
        this.data = data;
        this.height = 1;
    }
}
