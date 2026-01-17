package datastructures.queue;

/**
 * A fixed-size circular queue implementation used for storing
 * recent PerformanceMetrics in a sliding window.
 *
 * When the queue becomes full, new insertions overwrite
 * the oldest element automatically. This behavior is ideal
 * for adaptive difficulty systems that only care about the
 * most recent N samples.
 */
public class CircularQueue<T> {

    private T[] data;
    private int front;
    private int rear;
    private int size;
    private final int capacity;

    @SuppressWarnings("unchecked")
    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.data = (T[]) new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    /**
     * Inserts an item. If the queue is full, the oldest item will be overwritten.
     */
    public void enqueue(T item) {
        rear = (rear + 1) % capacity;
        data[rear] = item;

        if (size < capacity) {
            size++;
        } else {
            // queue is full → overwrite oldest
            front = (front + 1) % capacity;
        }
    }

    /**
     * Removes and returns the oldest item.
     */
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T item = data[front];
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    /**
     * Returns the oldest item without removing it.
     */
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return data[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int size() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns all items in the queue as a list without removing them.
     * Items are returned in order from oldest to newest.
     */
    public java.util.List<T> getAllItems() {
        java.util.List<T> items = new java.util.ArrayList<>();
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            items.add(data[index]);
        }
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CircularQueue[");
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            sb.append(data[index]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
