package datastructures.heap;

/**
 * A simple generic MinHeap implementation.
 * Used by DifficultyEvaluator to pick the smallest performance score.
 */
public class MinHeap<T extends Comparable<T>> {

    private T[] heap;
    private int size;

    @SuppressWarnings("unchecked")
    public MinHeap(int capacity) {
        heap = (T[]) new Comparable[capacity];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(T value) {
        if (size == heap.length) return;

        heap[size] = value;
        bubbleUp(size);
        size++;
    }

    public T peek() {
        return size == 0 ? null : heap[0];
    }

    public int size() {
        return size;
    }

    public int getCapacity() {
        return heap.length;
    }

    public T extractMin() {
        if (size == 0) return null;

        T min = heap[0];
        heap[0] = heap[size - 1];
        size--;

        bubbleDown(0);
        return min;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (heap[index].compareTo(heap[parent]) >= 0) break;

            swap(index, parent);
            index = parent;
        }
    }

    private void bubbleDown(int index) {
        while (index < size) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int smallest = index;

            if (left < size && heap[left].compareTo(heap[smallest]) < 0)
                smallest = left;

            if (right < size && heap[right].compareTo(heap[smallest]) < 0)
                smallest = right;

            if (smallest == index) break;

            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        T t = heap[i];
        heap[i] = heap[j];
        heap[j] = t;
    }
}
