import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Code provided for PS-1
 * Webcam-based drawing
 * Dartmouth CS 10, Winter 2025
 *
 * @author Kelly Lao, Shane Wattenmaker, Dartmouth CS10, Winter 2025
 * @author Tim Pierson, Dartmouth CS10, Winter 2025 (based on CamPaint from previous terms)
 */
public class CamPaintFinal extends VideoGUI {
    private char displayMode = 'w';       // what to display: 'w': live webcam, 'r': recolored image, 'p': painting
    private RegionFinder finder;          // handles the finding
    private Color targetColor;              // color of regions of interest (set by mouse press)
    private Color paintColor = Color.blue;  // the color to put into the painting from the "brush"
    private BufferedImage painting;         // the resulting masterpiece
    private ArrayList<Color> brushes;       //
    private int currentBrushIndex;
    private boolean pause;

    /**
     * Initializes the region finder and the drawing
     */
    public CamPaintFinal() {
        finder = new RegionFinder();
        clearPainting();
        currentBrushIndex = 0;
        brushes = new ArrayList<>();
        brushes.add(paintColor);
    }

    /**
     * Resets the painting to a blank image
     */
    protected void clearPainting() {
        painting = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // Initialize the canvas to a white background
        for (int y = 0; y < painting.getHeight(); y++) {
            for (int x = 0; x < painting.getWidth(); x++) {
                painting.setRGB(x, y, Color.WHITE.getRGB());
            }
        }
    }

    /**
     * VideoGUI method, drawing one of live webcam, recolored image, or painting,
     * depending on display variable ('w', 'r', or 'p')
     */
    public void handleImage() {
        // When the target color is set, paint a square around that area
        if (targetColor == null || pause) {
            setImage1(image);
        } else {
            finder.setRegions(new ArrayList<>());
            finder.setImage(image);
            finder.findRegions(targetColor);
            ArrayList<Point> largestRegion = finder.largestRegion();
            finder.recolorImage();
            Color currentBrushColor = brushes.get(currentBrushIndex);
            // Paint a square at the given point
            if (!largestRegion.isEmpty()) {
                // Get the first point of the largest region
                Point center = largestRegion.get(0); // takes first pixel in the
                paintSquare(center.x, center.y, currentBrushColor); // Paint a square at the center
            }
        }
        // Display the relevant image
        if (displayMode == 'w') {
            setImage1(image); // live webcam
        } else if (displayMode == 'r') {
            setImage1(finder.getRecoloredImage());
        } else if (displayMode == 'p') {
            setImage1(painting); // painting
        }
    }

    /**
     * Paints a square at the specified coordinates
     */
    private void paintSquare(int centerX, int centerY, Color color) {
        int length = 25; // Set bounds for pixels in the square
        for (int y = -length; y <= length; y++) {
            for (int x = -length; x <= length; x++) {
                int pixelX = centerX + x;
                int pixelY = centerY + y;
                // Ensure the pixel coordinates are within image bounds
                if (pixelX >= 0 && pixelX < painting.getWidth() && pixelY >= 0 && pixelY < painting.getHeight()) {
                    painting.setRGB(pixelX, pixelY, color.getRGB()); // Set the pixel color
                }
            }
        }
    }

    /**
     * Overrides the Webcam method to set the track color.
     */
    @Override
    public void handleMousePress(int x, int y) {
        System.out.println("Got mouse press"); // Precaution
        if (image != null) {
            targetColor = new Color(image.getRGB(x, y));
            System.out.println("Tracking " + targetColor); // Precaution
        }
    }
    /**
     * Webcam method, here doing various drawing commands
     */
    @Override
    public void handleKeyPress(char k) {
        if (k == 'p' || k == 'r' || k == 'w') { // display: painting, recolored image, or webcam
            displayMode = k;
        } else if (k == 'c') { // clear
            clearPainting();
        } else if (k == 'o') { // save the recolored image
            ImageIOLibrary.saveImage(finder.getRecoloredImage(), "pictures/recolored.png", "png");
        } else if (k == 's') { // save the painting
            ImageIOLibrary.saveImage(painting, "pictures/painting.png", "png");
        } else if (k == 'n') { // new color
            Color newColor = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
            brushes.add(newColor);
            currentBrushIndex = brushes.size() - 1;
        } else if (k == 'b') { // next brush
            if (currentBrushIndex == brushes.size() - 1) {
                currentBrushIndex = 0;
            } else {
                currentBrushIndex += 1;
            }
        } else if (k == 'e') {
            pause = !pause;
        } else {
            System.out.println("Unexpected key " + k);
        }
    }
    public static void main(String[] args) {
        new CamPaintFinal();
    }
}