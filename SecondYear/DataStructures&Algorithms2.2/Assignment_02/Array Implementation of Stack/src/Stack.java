import java.sql.Array;

public class Stack <T> {

    T [] stack;
    int capacity;
    int top;

    public Stack() {
        //initiates the array to 10 by default
        stack = (T[]) new Object [10];
        capacity = 10;
        top = -1;
    }

    public Stack(int size) {
        capacity = size;
        stack = (T[]) new Object [capacity];
        top = -1;
    }

    public T peek() {
        if(top == -1) {
            return null;
        }

        return stack[top];
    }

    public T pop() {
        if (top == -1) {
            return null;
        }
        T temp = stack[top];
        stack[top] = null;
        top--;
        return temp;
    }

    public void push(T data) {
        // Check if resizing is needed
        if (top + 1 == capacity) {
            capacity *= 2;
            T[] newStack = (T[]) new Object[capacity];

            for (int i = 0; i <= top; i++) {
                newStack[i] = stack[i];
            }

            stack = newStack;
        }

        stack[++top] = data;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void clear() {
        stack = (T[]) new Object [capacity];
        top = -1;
    }

    @Override
    public String toString() {
        String s = "[";
        for (int i = 0; i <= top; i++) {
            if(stack[i] != null) {
                s += stack[i].toString();
            }
            s += "->";
        }
        return s + "]";
    }
}
