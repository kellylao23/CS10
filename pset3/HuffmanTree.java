/**
 * @author Kelly Lao, Shane Wattenmaker
 */

import org.bytedeco.opencv.opencv_core.Buffer;
import org.w3c.dom.CharacterData;

import java.io.*;
import java.sql.Array;
import java.util.*;

public class HuffmanTree implements Huffman{
    /**
     * Read file provided in pathName and count how many times each character appears
     *
     * @param pathName - path to a file to read
     * @return - Map with a character as a key and the number of times the character appears in the file as value
     * @throws IOException
     */
    public Map<Character, Long> countFrequencies(String pathName) throws IOException {
        // read in file and turns into string
        BufferedReader reader = new BufferedReader(new FileReader(pathName));

        ArrayList<Character> characters = new ArrayList<>();
        try{
            int charInt = reader.read();
            while (charInt != -1){
                char currChar = (char) charInt;
                characters.add(currChar);
                charInt = reader.read();
            }
        }
        catch (IOException e){
            throw new IOException("Empty File");
        }
        finally{
            reader.close();
        }

        Map<Character, Long> frequency = new TreeMap<>();

        for (Character c: characters){
            if (frequency.containsKey(c)){
                frequency.put(c, (long) (frequency.get(c) + 1));
            }
            else{
                frequency.put(c, (long) 1);
            }
        }
        return frequency;
    }

    /**
     * Construct a code tree from a map of frequency counts. Note: this code should handle the special
     * cases of empty files or files with a single character.
     *
     * @param frequencies a map of Characters with their frequency counts from countFrequencies
     * @return the code tree.
     */
    public BinaryTree<CodeTreeElement> makeCodeTree(Map<Character, Long> frequencies) {
        /**
         * Contains compare method allowing priority determination
         */
        class TreeComparator implements Comparator<BinaryTree<CodeTreeElement>>{
            /**
             *
             * @param first the first character's binary tree
             * @param second the second character's binary tree
             * @return -1, 0, 1 based on compareTo method which compares frequencies
             */
            public int compare(BinaryTree<CodeTreeElement> first, BinaryTree<CodeTreeElement> second){
                return first.getData().compareTo(second.getData());
            }
        }

        Comparator<BinaryTree<CodeTreeElement>> treeComparator = new TreeComparator(); // instantiating treeComparator object
        PriorityQueue<BinaryTree<CodeTreeElement>> priorityList = new PriorityQueue<>(treeComparator); // instantiating codeTree object
        // organizes priority list
        for (Character key : frequencies.keySet()){
            CodeTreeElement element = new CodeTreeElement(frequencies.get(key), key);
            BinaryTree<CodeTreeElement> charTree = new BinaryTree<>(element);
            priorityList.add(charTree);
        }

        // creating binary tree implementing priority queue
        BinaryTree<CodeTreeElement> t;
        while (priorityList.size() > 1){
            BinaryTree<CodeTreeElement> t1 = priorityList.remove(); // dequeue smallest tree
            BinaryTree<CodeTreeElement> t2 = priorityList.remove(); // dequeue second smallest tree
            CodeTreeElement r = new CodeTreeElement(t1.getData().getFrequency()+t2.getData().getFrequency(), null);
            t = new BinaryTree<>(r, t1, t2);
            priorityList.add(t);
        }
        if (priorityList.size() == 1){
            BinaryTree<CodeTreeElement> t0 = priorityList.remove();
            CodeTreeElement r = new CodeTreeElement(t0.getData().getFrequency(), null);
            t = new BinaryTree<>(r, t0, null);
            priorityList.add(t);
        }
        if (priorityList.isEmpty()){
           return null;
        }
        return priorityList.remove();
    }

    /**
     * Computes the code for all characters in the tree and enters them
     * into a map where the key is a character and the value is the code of 1's and 0's representing
     * that character.
     *
     * @param codeTree the tree for encoding characters produced by makeCodeTree
     * @return the map from characters to codes
     */
    public Map<Character, String> computeCodes(BinaryTree<CodeTreeElement> codeTree) {
        Map<Character, String> codes = new TreeMap<>();
        String pathSoFar = "";
        codeHelper(codeTree, codes, pathSoFar);
        return codes;
    }

    /**
     *
     * @param currTree
     * @param codes
     * @param pathSoFar
     */
    public void codeHelper(BinaryTree<CodeTreeElement> currTree, Map<Character, String> codes, String pathSoFar){
        if (currTree == null){
            return;
        }
        if (currTree.isLeaf()) {
            codes.put(currTree.getData().getChar(), pathSoFar);
        }
        if (currTree.hasLeft()) {
            codeHelper(currTree.getLeft(), codes, pathSoFar + "0");
        }
        if (currTree.hasRight()){
            codeHelper(currTree.getRight(), codes, pathSoFar + "1");
        }
    }

    /**
     * Compress the file pathName and store compressed representation in compressedPathName.
     *
     * @param codeMap            - Map of characters to codes produced by computeCodes
     * @param pathName           - File to compress
     * @param compressedPathName - Store the compressed data in this file
     * @throws IOException
     */
    public void compressFile(Map<Character, String> codeMap, String pathName, String compressedPathName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathName));
        BufferedBitWriter writer = new BufferedBitWriter(compressedPathName);
        // read all characters and put into arraylist
        int c;
        while ((c = reader.read()) != -1){
            char currChar = (char) c;
            String charCode = codeMap.get(currChar);
            for (int i =  0; i < charCode.length(); i++) {
                if (charCode.charAt(i) == '0') {
                    writer.writeBit(false);
                } else if (charCode.charAt(i) == '1') {
                    writer.writeBit(true);
                }
            }
        }
        reader.close();
        writer.close();
    }

    /**
     * Decompress file compressedPathName and store plain text in decompressedPathName.
     *
     * @param compressedPathName   - file created by compressFile
     * @param decompressedPathName - store the decompressed text in this file, contents should match the original file before compressFile
     * @param codeTree             - Tree mapping compressed data to characters
     * @throws IOException
     */
    public void decompressFile(String compressedPathName, String decompressedPathName, BinaryTree<CodeTreeElement> codeTree) throws IOException {
        BufferedBitReader input = new BufferedBitReader(compressedPathName);
        BufferedWriter output = new BufferedWriter(new FileWriter(decompressedPathName));
        BinaryTree<CodeTreeElement> currentNode = codeTree;
        while (input.hasNext()) {
            boolean bit = input.readBit();
            if (bit) {
                currentNode = currentNode.getRight();
            } else {
                currentNode = currentNode.getLeft();
            }

            // If we reached a leaf node, write the character
            if (currentNode.isLeaf()) {
                output.write(currentNode.getData().getChar());
                currentNode = codeTree;  // Reset back to the root for the next character
            }
        }
        input.close();
        output.close();
    }

}
