package test;

import datastructures.queue.CircularQueue;

public class TestCircularQueue {

    public static void main(String[] args) {

        CircularQueue<Integer> q = new CircularQueue<>(3);

        System.out.println("Enqueue 1,2,3");
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        System.out.println(q); // Expected: [1, 2, 3]

        System.out.println("Enqueue 4 (overwrite oldest)");
        q.enqueue(4);
        System.out.println(q); // Expected: [2, 3, 4]

        System.out.println("Dequeue → " + q.dequeue()); // Expected: 2
        System.out.println(q); // Expected: [3, 4]

        System.out.println("Peek → " + q.peek()); // Expected: 3
        System.out.println("Size → " + q.size()); // Expected: 2
    }
}

