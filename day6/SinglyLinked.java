import java.util.Iterator;

/**
 * A singly-linked list implementation of the SimpleList interface
 * Also implements iterable so SinglyLinked can be used in for-each loops
 *
 * @author Tim Pierson, Dartmouth CS10, Fall 2024, based on SinglyLinked from prior terms, added iterator
 */
public class SinglyLinked<T> implements SimpleList<T>, Iterable<T>{
    private Element head; //front of the linked list - null pointer
    private int size; //# elements in the list

    /**
     * The linked elements in the list: each has a piece of data and a next pointer
     */
    private class Element{ //nested class
        private T data;
        private Element next;

        private Element(T data, Element next){
            this.data = data;
            this.next = next;
        }
    }

    public SinglyLinked() {
        head = null;
        size = 0;
    }

    /**
     * Return the number of elements in the List (they are indexed 0..size-1)
     * @return number of elements
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if there are no elements in the List, false otherwise
     * @return true or false
     */
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * Helper function, advancing to the nth Element in the List and returning it
     * @param n - number of items to advance
     * @return item at index n
     * @throws Exception for invalid index
     */

    private Element advance(int n) throws Exception{
        Element e = head;
        //safety check for valid index (don't assume caller checked!)
        if (e == null || n < 0 || n >= size){
            throw new Exception("invalid index");
        }

        //just follow the next pointer n times
        for (int i = 0; i < n; i++){
            e = e.next;
        }
        return e;
    }

    /**
     * Add item at index idx
     * @param idx index to add item
     * @param item item to add
     * @throws Exception invalid index
     */
    public void add(int idx, T item) throws Exception {
        //safety check for valid index (can add at size index)
        if (idx < 0 || idx > size) {
            throw new Exception("invalid index");
        }
        else if (idx == 0) {
            // Insert at head
            head = new Element(item, head); //new item gets next pointer set to head
        }
        else {
            // It's the next thing after element # idx-1
            Element e = advance(idx-1);
            // Splice it in
            e.next = new Element(item, e.next);	//create new element with next pointing at prior element's next
            //and prior element's next updated to point to this item
        }
        size++;
    }

    /**
     * Add item at end of List
     * @param item - item to add to List
     * @throws Exception
     */
    public void add(T item) throws Exception {
        add(size,item);
    }


    /**
     * Remove and return item at index idx
     * @param idx index to remove
     * @throws Exception
     */
    public T remove(int idx) throws Exception {
        T data = null; //data to return
        //safety check for valid index
        if (head == null || idx < 0 || idx >= size) {
            throw new Exception("invalid index");
        }
        else if (idx == 0) {
            data = head.data;
            head = head.next;
        }
        else {
            // It's the next thing after element # idx-1
            Element e = advance(idx-1);
            data = e.next.data;
            // Splice it out
            e.next = e.next.next;  //nice!
        }
        size--;
        return data;
    }

    /**
     * Return the item at index idx
     * @param idx index of item
     * @return item at index idx
     * @throws Exception invalid index
     */
    public T get(int idx) throws Exception {
        //safety check for valid index
        if (idx < 0 || idx >= size) {
            throw new Exception("invalid index");
        }
        Element e = advance(idx);
        return e.data;
    }

    /**
     * Overwrite the data at index idx
     * @param idx index to overwrite
     * @param item data to store at idx
     * @throws Exception invalid index
     */
    public void set(int idx, T item) throws Exception {
        //safety check for valid index
        if (idx < 0 || idx >= size) {
            throw new Exception("invalid index");
        }
        Element e = advance(idx);
        e.data = item;
    }

    /**
     * Return a String representation of the List
     * @return box and pointer diagram with end marked with [/]
     */
    public String toString() {
        String result = "";
        for (Element x = head; x != null; x = x.next)
            result += x.data + "->";
        result += "[/]";

        return result;
    }

    /**
     * Returns an iterator over the elements in this List.
     * @return iterator
     */
    public Iterator<T> iterator() {  //satisfy iterator requirement in SimpleSet interface
        return new ListIterator();
    }

    /**
     * Iterator class that implements the required functionality to use this List in a for each loop
     */
    private class ListIterator implements Iterator<T> {
        // Use curr to point to next item in List
        Element curr; //store current value

        public ListIterator() {
            curr = head;
        }

        /**
         * Does iterator have more items?
         *
         * @return true if more items, false otherwise
         */
        public boolean hasNext() {
            return curr != null;
        }

        /**
         * Return the next item in the iterator, advance to next item in list
         *
         * @return item stored at curr position in the list
         */
        public T next() {
            if (curr == null) {
                throw new IndexOutOfBoundsException();
            }
            T data = curr.data;
            curr = curr.next;
            return data;
        }
    }
    public T middleElement(){
        Element first = head; // Initialize first to head
        Element second = head; // Initialize second to head
        int count = 0; // Initialize count
        while (second.next != null){ // Traverse the list while second is not null
            second = second.next; // Move second one step
            if (count == 1){ // Check if count is 1
                first = first.next; // Move first one step
                count = -1; // Reset count
            }
            count += 1; // Increment count
        }
        return first.data; // Return the data at the first element
    }
}
