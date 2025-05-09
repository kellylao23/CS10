/**
 * @author Kelly Lao, CS10, Winter 2025
 * Write an implementation of the Set ADT called BSTSet that uses the Binary Search Tree (BST.java) from class to store the data.
 * Your Set implementation should conform to this SimpleSet.java interface.
 * Include test cases to show that your implementation works as intended.
 *
 * Hint: create an instance variable in your BSTSet class that stores the root of a BST.
 * You can use the Key of the BST to store your Set's values.
 * You won't need the Value of the BST, so you can set it to any type you choose (null is a good choice for the value).
 * To implement the Set functionality, call BST methods using the root of the BST.
 * Do not try to extend BST to implement Set functionality.
 *
 * Hint 2: for the iterator method, you might find it easiest to do an inorder traversal of your BST and store the items in a List.
 * You can then use the List as a basis for your iterator.
 */

import java.security.InvalidKeyException;
import java.util.*;

public class BSTSet<T extends Comparable<T>, V> implements SimpleSet<T>, Iterable<T>{
    public BST<T, V> root;
    private int size;
    private ArrayList<T> array;

    public BSTSet(T item) {
        this.root = new BST<>(item, null);
        this.size = 0;
    }

    /**
     * Add an item to the Set if it is not already present
     * Return true if the item is added, false otherwise
     */
    public boolean add(T item){
        if (contains(item)){
            root.insert(item, null);
            return false;
        }else{
            root.insert(item, null);
            size ++;
            return true;
        }
    }

    /**
     * Remove an item from the Set
     * Return true if the item is removed, false otherwise
     */
    public boolean remove(T item) {
        try{
            root.delete(item);
        }
        catch(Exception e){
            return false;
        }
        size--;
        return true;
    }

    /**
     * Removes all items from the Set
     */
    public void clear(){
        root = new BST<>(null, null);
        size = 0;
    }

    /**
     * Return true if the item is in the Set, false otherwise
     */
    public boolean contains(T item){
        try{
            root.find(item);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    /**
     * Return true if the Set is empty, false otherwise
     */
    public boolean isEmpty(){
        return (size == 0);
    }

    /**
     * Returns # elements in the set
     */
    public int size(){
        return size;
    }

    /**
     * Return a string representation of the Set
     */
    public String toString(){
        if (isEmpty()) {
            return "[]";
        }
        return "[" + toStringHelper(root) + "]";
    }

    public String toStringHelper(BST<T, V> key){
        String s = "";
            if (key.hasLeft()) {
                s += toStringHelper(key.getLeft());
            }
            s += key.getKey().toString() + " ";
            if (key.hasRight()) {
                s += toStringHelper(key.getRight());
            }
            return s;
    }

    /**
     * Returns an iterator to loop over the Set
     */
    public Iterator<T> iterator(){
        return new BSTSetIterator();
    }

    private class BSTSetIterator implements Iterator<T> {
        int index;
        ArrayList<T> list;

        public BSTSetIterator() {
            index = 0;
            accumulator(root, array);
        }

        public void accumulator(BST<T, V> key, ArrayList<T> item) {
            if (key.hasLeft()) {
                accumulator(key.getLeft(), item);
            }
            item.add(key.getKey());
            if (key.hasRight()) {
                accumulator(key.getRight(), item);
            }
        }

        public boolean hasNext() {
            return (index < list.size());
        }

        public T next() {
            if (!hasNext()){
                throw new IndexOutOfBoundsException();
            }
            index++;
            return list.get(index);
        }
    }

    public static void main(String[] args) {
        BSTSet<Integer, Integer> bstSet = new BSTSet<>(5);
        bstSet.add(1);
        bstSet.add(2);
        bstSet.add(3);
        bstSet.add(4);
        bstSet.add(5);

        for (int i : bstSet){
            System.out.println(i);
        }

        System.out.println(bstSet.remove(5));
        System.out.println(bstSet);
        System.out.println(bstSet.size());
        System.out.println(bstSet.contains(2));
        System.out.println(bstSet.isEmpty());
        bstSet.clear();
        System.out.println(bstSet);
    }
}