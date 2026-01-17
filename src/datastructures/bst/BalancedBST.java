package datastructures.bst;

/**
 * A self-balancing Binary Search Tree (AVL Tree)
 * used to store performance history sorted by a comparable key.
 */
public class BalancedBST<T extends Comparable<T>> {

    private BSTNode<T> root;

    /** Insert an item into the tree */
    public void insert(T data) {
        root = insertRec(root, data);
    }

    /** Recursive insert with AVL balancing */
    private BSTNode<T> insertRec(BSTNode<T> node, T data) {
        if (node == null) return new BSTNode<>(data);

        if (data.compareTo(node.data) < 0)
            node.left = insertRec(node.left, data);
        else
            node.right = insertRec(node.right, data);

        updateHeight(node);
        return balance(node);
    }

    private void updateHeight(BSTNode<T> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int height(BSTNode<T> node) {
        return (node == null) ? 0 : node.height;
    }

    private int getBalance(BSTNode<T> node) {
        return height(node.left) - height(node.right);
    }

    /** Balance the tree using AVL rotations */
    private BSTNode<T> balance(BSTNode<T> node) {
        int balance = getBalance(node);

        // Left heavy
        if (balance > 1) {
            if (getBalance(node.left) < 0)
                node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Right heavy
        if (balance < -1) {
            if (getBalance(node.right) > 0)
                node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private BSTNode<T> rotateRight(BSTNode<T> y) {
        BSTNode<T> x = y.left;
        BSTNode<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private BSTNode<T> rotateLeft(BSTNode<T> x) {
        BSTNode<T> y = x.right;
        BSTNode<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    /** Inorder traversal (for debugging) */
    public void inorderTraversal() {
        inorderRec(root);
    }

    private void inorderRec(BSTNode<T> node) {
        if (node == null) return;
        inorderRec(node.left);
        System.out.println(node.data);
        inorderRec(node.right);
    }
}
