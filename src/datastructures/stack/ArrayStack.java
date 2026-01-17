package datastructures.stack;

/**
 * A simple array-based stack implementation.
 * Used to store past difficulty states for undo functionality.
 */
public class ArrayStack<T> {

    private T[] data;
    private int top = -1;

    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        data = (T[]) new Object[capacity];
    }

    public void push(T item) {
        data[++top] = item;
    }

    public T pop() {
        return (top == -1) ? null : data[top--];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }
    
    public T peek() {
        return (top == -1) ? null : data[top];
    }
}
