import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Code provided for PS-1
 * Webcam-based drawing
 * Dartmouth CS 10, Winter 2025
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2025 (based on CamPaint from previous terms)
 */
public class CamPaint extends VideoGUI {
    private char displayMode = 'w';			// what to display: 'w': live webcam, 'r': recolored image, 'p': painting
    private RegionFinder finder;			// handles the finding
    private Color targetColor;          	// color of regions of interest (set by mouse press)
    private Color paintColor = Color.blue;	// the color to put into the painting from the "brush"
    private BufferedImage painting;         // the resulting masterpiece

    /**
     * Initializes the region finder and the drawing
     */
    public CamPaint() {
        finder = new RegionFinder(); // create new finder object
        clearPainting();
    }

    /**
     * Resets the painting to a blank image
     */
    protected void clearPainting() {
        painting = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); // creates blank buffered image of same dimension as image
    }
    /**
     * VideoGUI method, here drawing one of live webcam, recolored image, or painting,
     * depending on display variable ('w', 'r', or 'p')
     */
    @Override
    public void handleImage() {
        if (targetColor == null){ // checks if no pixel has been clicked
            setImage1(image);
        }
        if (targetColor != null){ // runs if a pixel has been clicked
            finder.setRegions(new ArrayList<>());
            finder.setImage(image);
            finder.findRegions(targetColor);
            ArrayList<Point> largestRegion = finder.largestRegion();
            finder.recolorImage();
            for (Point point : largestRegion) { // recolors points in painting
                painting.setRGB(point.x, point.y, paintColor.getRGB());
            }
        }
        if (displayMode == 'w') {
            setImage1(image); // live webcam
        } else if (displayMode == 'r') {
            setImage1(finder.getRecoloredImage()); // live recolored image
        } else if (displayMode == 'p') {
            setImage1(painting); // painting
        }
    }


    /**
     * Overrides the Webcam method to set the track color.
     */
    @Override
    public void handleMousePress(int x, int y) {
        System.out.println("Got mouse press"); //precaution
        if (image != null) {
            targetColor = new Color(image.getRGB(x, y)); // sets target color to color of pixel clicked
            System.out.println("tracking " + targetColor); //precaution
        }
    }

    /**
     * Webcam method, here doing various drawing commands
     */
    @Override
    public void handleKeyPress(char k) {
        if (k == 'p' || k == 'r' || k == 'w') { // display: painting, recolored image, or webcam
            displayMode = k;
        }
        else if (k == 'c') { // clear
            clearPainting();
        }
        else if (k == 'o') { // save the recolored image
            ImageIOLibrary.saveImage(finder.getRecoloredImage(), "pictures/recolored.png", "png");
        }
        else if (k == 's') { // save the painting
            ImageIOLibrary.saveImage(painting, "pictures/painting.png", "png");
        }
        else {
            System.out.println("unexpected key "+k);
        }
    }

    public static void main(String[] args) {
        new CamPaint();
    }
}
