package test;

import datastructures.stack.ArrayStack;

public class TestStack {
    public static void main(String[] args) {
        ArrayStack<String> stack = new ArrayStack<>(10);

        stack.push("A");
        stack.push("B");
        stack.push("C");

        System.out.println(stack.pop());  // C
        System.out.println(stack.pop());  // B
        System.out.println(stack.pop());  // A
    }
}
