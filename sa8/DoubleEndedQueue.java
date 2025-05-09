/**
 * Implement a deque — a double ended queue — where items can be added or removed from the front or the back.
 * Your deque implementation should conform to this SimpleDeque.java interface.
 *
 * You can implement the deque using a doubly linked list or an array.
 * If you use a doubly linked list, keep a head and tail pointer (like SA-4),
 * and each element in the list should have a previous and next pointer,
 * instead of just the next pointer we implemented previously.
 * If you use an array, the array should grow when full (e.g., no limit to the number of items stored in the deque).
 * For full credit, the SimpleDeque methods except contains should run in constant time.
 *
 * Include test cases to show that your implementation works as intended.
 */
import java.lang.reflect.Array;
import java.util.*;

public class DoubleEndedQueue<T> implements SimpleDeque<T>{
    private Element top;
    private Element bottom;
    private int size;

    private class Element{
        public T data;
        public Element next;
        public Element prev;

        public Element(T data, Element next, Element prev){
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    public DoubleEndedQueue(){
        top = null;
        bottom = null;
        size = 0;
    }

    /**
     * Add item at the front of the deque
     *
     * @param item
     * runtime O(1)
     */
    public void addFirst(T item) {
        if (size == 0){
            top = new Element(item, null, null);
            bottom = top;
        }else{
            Element e = top;
            top = new Element(item, e, null);
            if (bottom.next != null){
                bottom = bottom.next;
            }
        }
        size++;
    }

    /**
     * Add item at the end of the deque
     *
     * @param item
     * runtime O(1)
     */
    public void addLast(T item) {
        if (size == 0){
            top = new Element(item, null, null);
            bottom = top;
        }else{
            bottom.next = new Element(item, null, bottom);
            bottom = bottom.next;
        }
        size++;
    }

    /**
     * Removes and returns the item at the front of the deque
     * throws exception if deque is empty
     * runtime O(1)
     */
    public T removeFirst() throws Exception {
        if (size == 0){
            throw new Exception("Deque is empty");
        }
        Element e = top;
        top = top.next;
        size--;
        return e.data;
    }

    /**
     * Removes and returns the item at the end of the deque
     * throws exception if deque is empty
     * runtime O(1)
     */
    public T removeLast() throws Exception {
        if (size == 0){
            throw new Exception("Deque is empty");
        }
        T tempData = bottom.data;
        bottom = bottom.prev;
        bottom.next = null;
        size--;
        return tempData;
    }

    /**
     * Returns the item at the front of the deque
     * throws exception if deque is empty
     * runtime O(1)
     */
    public T getFirst() throws Exception {
        if (size == 0) {
            throw new Exception("Deque is empty");
        }
        return top.data;
    }

    /**
     * Returns the item at the end of the deque
     * throws exception if deque is empty
     * runtime O(1)
     */
    public T getLast() throws Exception {
        if (size == 0) {
            throw new Exception("Deque is empty");
        }
        return bottom.data;
    }

    /**
     * Clears the deque
     * runtime O(1)
     */
    public void clear() {
        top = null;
        bottom = null;
        size = 0;
    }

    /**
     * Returns true if the deque contains the item, false otherwise
     *
     * @param item
     * runtime O(n)
     */
    public boolean contains(T item) {
        Element current = top;
        while (current != null) {
            if (current.data.equals(item)){
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Returns true if the deque is empty, false otherwise
     * runtime O(1)
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Returns # elements in the deque
     * runtime O(1)
     */
    public int size() {
        return size;
    }

    public static void main(String[] args) throws Exception {
        DoubleEndedQueue<Integer> deque = new DoubleEndedQueue<>();

        // Test adding elements
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(0);
        deque.addLast(3);

        System.out.println("Size:" + deque.size());
        System.out.println("Contains 1: " + deque.contains(1));
        System.out.println("Contains 0: " + deque.contains(0));

        System.out.println("First element: " + deque.getFirst());
        System.out.println("Removing first: " + deque.removeFirst());
        System.out.println("New first element: " + deque.getFirst());

        System.out.println("Last element: " + deque.getLast());
        System.out.println("Removing last: " + deque.removeLast());
        System.out.println("New last element: " + deque.getLast());
        System.out.println("New size: " + deque.size());

        System.out.println("Empty? " + deque.isEmpty());
        deque.clear();
        System.out.println("Empty? " + deque.isEmpty());
    }
}
