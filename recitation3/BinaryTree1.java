import java.util.ArrayList;
import java.util.List;

/**
 * Generic binary tree, storing data of generic type in each node
 *
 * @author Tim Pierson, Winter 2025, based on binary tree from prior terms
 */

public class BinaryTree1<E> {
    private BinaryTree1<E> left, right;	// children; can be null
    E data;

    /**
     * Constructor leaf node -- left and right are null
     */
    public BinaryTree1(E data) {
        this.data = data; this.left = null; this.right = null;
    }

    /**
     * Constructor inner node
     */
    public BinaryTree1(E data, BinaryTree1<E> left, BinaryTree1<E> right) {
        this.data = data; this.left = left; this.right = right;
    }

    /**
     * Getters
     */
    public BinaryTree1<E> getLeft() { return left; }
    public BinaryTree1<E> getRight() { return right; }
    public E getData() { return data; }

    /**
     * Setters
     */
    public void setData(E data) {this.data = data;}
    public void setLeft(BinaryTree1<E> child) { left = child; }
    public void setRight(BinaryTree1<E> child) { right = child; }

    /**
     * helper methods
     */
    public boolean isInner() { return left != null || right != null; }
    public boolean isLeaf() { return left == null && right == null; }
    public boolean isFull() { return left != null && right !=null;}
    public boolean hasLeft() { return left != null; }
    public boolean hasRight() { return right != null; }

    /**
     * Number of nodes (inner and leaf) in tree
     */
    public int size() {
        int num = 1;
        if (hasLeft()) num += left.size();
        if (hasRight()) num += right.size();
        return num;
    }

    /**
     * Longest length to a leaf node from here
     */
    public int height() {
        if (isLeaf()) return 0;
        int h = 0;
        if (hasLeft()) h = Math.max(h, left.height());
        if (hasRight()) h = Math.max(h, right.height());
        return h+1;		// inner: one higher than highest child
    }

    /**
     * Same structure and data
     * @param t2 compare with this tree
     * @return true if this tree and t2 have the same structure and same data in each node, false otherwise
     */
    public boolean equals(BinaryTree1<E> t2) {
        if (hasLeft() != t2.hasLeft() || hasRight() != t2.hasRight()) return false;
        if (!data.equals(t2.data)) return false;
        if (hasLeft() && !left.equals(t2.left)) return false;
        if (hasRight() && !right.equals(t2.right)) return false;
        return true;
    }

    /**
     * Check to see if value is in the tree
     * @param value value to seek
     * @return true if value in tree, false otherwise
     */
    public boolean contains(E value) {
        //see if this node's data is equal to value
        if (data.equals(value)) {
            return true;
        }
        else {
            //see if left or right child has value
            boolean leftResult = false;
            boolean rightResult = false;
            if (hasLeft()) leftResult = left.contains(value);
            if (hasRight()) rightResult = right.contains(value);
            return leftResult || rightResult; //true if left or right is true
        }
    }

    /**
     * Leaves, in order from left to right
     */
    public List<E> fringe() {
        List<E> f = new ArrayList<E>();
        addToFringe(f);
        return f;
    }

    /**
     * Helper for fringe, adding fringe data to the list
     */
    private void addToFringe(List<E> fringe) {
        if (isLeaf()) {
            fringe.add(data);
        }
        else {
            if (hasLeft()) left.addToFringe(fringe);
            if (hasRight()) right.addToFringe(fringe);
        }
    }
    /*
    * This produces a "shallow" copy of a tree, down to the given depth. By "shallow", I mean that
 *  * the tree structure is copied, but the same data elements are used (not copies of them). By
 *  * "down to the given depth", I mean that nodes deeper than the value are chopped out. For depth
 *  * 0, only the node itself is kept; its children are null. For depth 1, the children are kept but
 *  * their children are null.
            * @throws Exception
 */

    public BinaryTree<E> copyToDepth(int d) {
        if (d < 0) { //if there is no tree
            return null;
        }

        if(isLeaf()){ //if there are no children
            return new BinaryTree<>(data, null, null);
        }
        if (d == 0) { //if it is the root
            return new BinaryTree<>(data);
        }

        BinaryTree<E> left1 = null;
        BinaryTree<E> right1 = null;

        if (hasLeft()) {left1 = left.copyToDepth(d - 1); //copies child content to d-1 level
        }
        if (hasRight()) {right1 = right.copyToDepth(d - 1); //similar, but for right side
        }

        return new BinaryTree<>(data, left1, right1);
    }

    /**
     * Returns a string representation of the tree by calling a helper method for recursion
     * Two versions: (1) simple -- prints indented, (2) pretty printed -- prints parent/child lines
     * uncomment one version
     */
    public String toString() {
        //return toStringHelper(""); //simple version prints indented
        return toStringHelper("", ""); //pretty printed version includes lines to show parent/child
    }

    /**
     * Simple version: Recursively constructs a String representation of the tree from this node,
     * starting with the given indentation and indenting further going down the tree
     */
    public String toStringHelper(String indent) {
        String res = indent + data + "\n";
        if (hasLeft()) res += left.toStringHelper(indent + "  ");
        if (hasRight()) res += right.toStringHelper(indent + "  ");
        return res;
    }

    /**
     * Pretty printed version: Recursively constructs a String representation of the tree from this node,
     * starting with the given indentation and indenting further going down the tree
     * formatting inspired by: https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram-in-java
     */
    public String toStringHelper(String indent, String childIndent) {
        String res = indent + data + "\n";
        if (hasLeft()) {
            //not null left child
            res += left.toStringHelper(childIndent + "|-- ", childIndent + "|   ");
        }
        else if (!isLeaf()){
            //null left child
            res += childIndent + "|-- null\n";
        }
        if (hasRight()) {
            //not null right child
            res += right.toStringHelper(childIndent + "|-- ", childIndent + "    ");
        }
        else if (!isLeaf()){
            //null right child
            res += childIndent + "|-- null\n";
        }
        return res;
    }

    /**
     * Tree traversals
     */
    public void preOrderTraversal() {
        System.out.print(data +" ");
        if (hasLeft()) left.preOrderTraversal();
        if (hasRight()) right.preOrderTraversal();
    }

    public void inOrderTraversal() {
        if (hasLeft()) left.inOrderTraversal();
        System.out.print(data + " ");
        if (hasRight()) right.inOrderTraversal();
    }

    public void postOrderTraversal() {
        if (hasLeft()) left.postOrderTraversal();
        if (hasRight()) right.postOrderTraversal();
        System.out.print(data + " ");
    }


    public static void main(String[] args) throws Exception {
        //manually build a tree
        BinaryTree1<String> root = new BinaryTree1<String>("G");
        root.left = new BinaryTree1<String>("B");
        root.right = new BinaryTree1<String>("F");
        BinaryTree1<String>temp = root.left;
        temp.left = new BinaryTree1<String>("A");
        temp.right = new BinaryTree1<String>("C");
        temp = root.right;
        temp.left = new BinaryTree1<String>("D");
        temp.right = new BinaryTree1<String>("E");
        System.out.println("root");
        System.out.println(root);

        System.out.println("Fringe");
        System.out.println(root.fringe());

        System.out.println("Contains A: " + root.contains("A"));
        System.out.println("Contains Z: " + root.contains("Z"));

        //build an identical second tree
        BinaryTree1<String> root2 = new BinaryTree1<String>("G");
        root2.left = new BinaryTree1<String>("B");
        root2.right = new BinaryTree1<String>("F");
        BinaryTree1<String>temp2 = root2.left;
        temp2.left = new BinaryTree1<String>("A");
        temp2.right = new BinaryTree1<String>("C");
        temp2 = root2.right;
        temp2.left = new BinaryTree1<String>("D");
        temp2.right = new BinaryTree1<String>("E");
        System.out.println("root");
        System.out.println(root2);

        //check equals
        System.out.println("root and root2 equal: " + root.equals(root2));
        System.out.println("Updating root2 to have data Z");
        root2.setData("Z");
        System.out.println(root2);
        System.out.println("root and root2 equal: " + root.equals(root2));

        //traversals
        System.out.println("In-order traversal");
        root.inOrderTraversal();
        System.out.println("\nPre-order traversal");
        root.preOrderTraversal();
        System.out.println("\nPost-order traversal");
        root.postOrderTraversal();
    }
}
