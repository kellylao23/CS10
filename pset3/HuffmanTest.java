import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class HuffmanTest {
    public static void main(String[] args) throws IOException {
        HuffmanTree tree = new HuffmanTree();
        String pathName = "pset3/USConstitution.txt";
        String main = pathName.substring(0, pathName.lastIndexOf('.'));
        String compressedFile = main + "_compressed.txt";
        String decompressedFile = main + "_decompressed.txt";
        Map<Character, Long> frequencies = tree.countFrequencies(pathName);
        BinaryTree<CodeTreeElement> codetree = tree.makeCodeTree(frequencies);

        Map<Character, String> codes = tree.computeCodes(codetree);
        System.out.println(frequencies);
        System.out.println(codetree);
        System.out.println(codes);
        tree.compressFile(codes, pathName, compressedFile);
        tree.decompressFile(compressedFile, decompressedFile, codetree);
    }
}
