import java.io.*;
import java.util.*;

public class HuffmanTree implements Huffman {

    public Map<Character, Long> countFrequencies(String pathName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathName));
        Map<Character, Long> frequency = new TreeMap<>();
        int charInt;
        while ((charInt = reader.read()) != -1) {
            char currChar = (char) charInt;
            if (frequency.containsKey(currChar)){
                frequency.put(currChar, frequency.get(currChar + 1));
            }else{
                frequency.put(currChar, (long) 0);
            }
        }
        reader.close();
        return frequency;
    }

    public BinaryTree<CodeTreeElement> makeCodeTree(Map<Character, Long> frequencies) {
        if (frequencies.isEmpty()) {
            return null;
        }
        if (frequencies.size() == 1) {
            Map.Entry<Character, Long> entry = frequencies.entrySet().iterator().next();
            return new BinaryTree<>(new CodeTreeElement(entry.getValue(), entry.getKey()));
        }

        PriorityQueue<BinaryTree<CodeTreeElement>> priorityList = new PriorityQueue<>(Comparator.comparingLong(o -> o.getData().getFrequency()));

        for (Character key : frequencies.keySet()) {
            CodeTreeElement element = new CodeTreeElement(frequencies.get(key), key);
            BinaryTree<CodeTreeElement> charTree = new BinaryTree<>(element);
            priorityList.add(charTree);
        }

        while (priorityList.size() > 1) {
            BinaryTree<CodeTreeElement> t1 = priorityList.remove();
            BinaryTree<CodeTreeElement> t2 = priorityList.remove();
            CodeTreeElement r = new CodeTreeElement(t1.getData().getFrequency() + t2.getData().getFrequency(), null);
            BinaryTree<CodeTreeElement> t = new BinaryTree<>(r, t1, t2);
            priorityList.add(t);
        }
        if (priorityList.size() == 1) {
            return priorityList.remove();
        } else {
            return null;
        }
    }

    public Map<Character, String> computeCodes(BinaryTree<CodeTreeElement> codeTree) {
        Map<Character, String> codes = new TreeMap<>();
        codeHelper(codeTree, codes, "");
        return codes;
    }

    public void codeHelper(BinaryTree<CodeTreeElement> currTree, Map<Character, String> codes, String pathSoFar) {
        if (currTree == null) {
            return;
        }
        if (currTree.isLeaf()) {
            codes.put(currTree.getData().getChar(), pathSoFar);
        }
        codeHelper(currTree.getLeft(), codes, pathSoFar + "0");
        codeHelper(currTree.getRight(), codes, pathSoFar + "1");
    }

    public void compressFile(String pathName, String compressedPathName) throws IOException {
        Map<Character, Long> frequencies = countFrequencies(pathName);
        BinaryTree<CodeTreeElement> codeTree = makeCodeTree(frequencies);
        if (codeTree == null) {
            throw new IOException("Failed to create Huffman tree");
        }

        Map<Character, String> codeMap = computeCodes(codeTree);

        BufferedReader reader = new BufferedReader(new FileReader(pathName));
        BufferedBitWriter writer = new BufferedBitWriter(compressedPathName);

        BufferedWriter treeWriter = new BufferedWriter(new FileWriter(compressedPathName + ".tree"));
        writeCodeTree(codeTree, treeWriter);
        treeWriter.close();

        int c;
        while ((c = reader.read()) != -1) {
            char currChar = (char) c;
            String charCode = codeMap.get(currChar);
            for (int i = 0; i < charCode.length(); i++) {
                writer.writeBit(charCode.charAt(i) == '1');
            }
        }
        reader.close();
        writer.close();
    }

    public void decompressFile(String compressedPathName, String decompressedPathName) throws IOException {
        BufferedBitReader input = new BufferedBitReader(compressedPathName);
        BufferedWriter output = new BufferedWriter(new FileWriter(decompressedPathName));

        BufferedReader treeReader = new BufferedReader(new FileReader(compressedPathName + ".tree"));
        BinaryTree<CodeTreeElement> codeTree = readCodeTree(treeReader);
        treeReader.close();

        BinaryTree<CodeTreeElement> currentNode = codeTree;

        while (input.hasNext()) {
            boolean bit = input.readBit();
            if (bit) {
                currentNode = currentNode.getRight();
            } else {
                currentNode = currentNode.getLeft();
            }
            if (currentNode.isLeaf()) {
                output.write(currentNode.getData().getChar());
                currentNode = codeTree; // Reset back to the root of the tree
            }
        }
        input.close();
        output.close();
    }

    public void writeCodeTree(BinaryTree<CodeTreeElement> codeTree, BufferedWriter writer) throws IOException {
        if (codeTree.isLeaf()) {
            writer.write("1" + codeTree.getData().getChar());
        } else {
            writer.write("0");
            writeCodeTree(codeTree.getLeft(), writer);
            writeCodeTree(codeTree.getRight(), writer);
        }
    }

    private int index;

    public BinaryTree<CodeTreeElement> readCodeTree(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        index = 0;
        return readCodeTreeHelper(line);
    }

    private BinaryTree<CodeTreeElement> readCodeTreeHelper(String line) {
        if (index >= line.length()) {
            return null;
        }
        char currentChar = line.charAt(index);
        index++;
        if (currentChar == '1') {
            char character = line.charAt(index);
            index++;
            return new BinaryTree<>(new CodeTreeElement((long) 0, character)); // Leaf node with frequency 0
        } else {
            BinaryTree<CodeTreeElement> left = readCodeTreeHelper(line);
            BinaryTree<CodeTreeElement> right = readCodeTreeHelper(line);
            return new BinaryTree<>(new CodeTreeElement((long) 0, null), left, right); // Internal node
        }
    }
}