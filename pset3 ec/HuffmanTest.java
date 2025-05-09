import java.io.IOException;

public class HuffmanTest {
    public static void main(String[] args) {
        try {
            HuffmanTree huffmanTree = new HuffmanTree();
            String pathName = "pset3/hello.txt";
            String compressedFile = pathName.substring(0, pathName.lastIndexOf('.')) + "_compressed.txt";
            String decompressedFile = pathName.substring(0, pathName.lastIndexOf('.')) + "_decompressed.txt";

            huffmanTree.compressFile(pathName, compressedFile);
            huffmanTree.decompressFile(compressedFile, decompressedFile);

        } catch (IOException e) {
            e.printStackTrace(); // asked chat for error to throw
        }
    }
}