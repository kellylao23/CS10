import java.awt.*;
import java.awt.image.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

/**
 * Code provided for PS-1
 * Region growing algorithm: finds and holds regions in an image.
 * Each region is a list of contiguous points with colors similar to a target color.
 * Dartmouth CS 10, Winter 2025
 *
 * @author Tim Pierson, Dartmouth CS10, Winter 2025, based on prior terms RegionFinder
 */

/**
 * Loop over all the pixels
 *   If a pixel is unvisited and of the correct color
 *     Start a new region
 *     Keep track of which pixels need to be visited, initially just that one
 *     As long as there's some pixel that needs to be visited
 *       Get one to visit
 *       Add it to the region
 *       Mark it as visited
 *       Loop over all its neighbors
 *         If the neighbor is of the correct color
 *           Add it to the list of pixels to be visited
 *     If the region is big enough to be worth keeping, do so
 */
public class RegionFinder {
    private static final int maxColorDiff = 20;          // how similar a pixel color must be to the target color, to belong to a region
    private static final int minRegion = 50;             // how many points in a region to be worth considering

    private BufferedImage image;                            // the image in which to find regions
    private BufferedImage recoloredImage;                   // the image with identified regions recolored

    private ArrayList<ArrayList<Point>> regions = new ArrayList<ArrayList<Point>>();          // a region is a list of points
    // so the identified regions are in a list of lists of points

    public RegionFinder() {
        this.image = null;
    }

    public RegionFinder(BufferedImage image) {
        this.image = image;
    }

    public void setRegions(ArrayList<ArrayList<Point>> regions){this.regions = regions;}

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getRecoloredImage() {
        return recoloredImage;
    }

    /**
     * Sets regions to the flood-fill regions in the image, similar enough to the trackColor.
     */


    public void findRegions(Color targetColor) {

        BufferedImage visited = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){

                ArrayList<Point> curRegion = new ArrayList<>();
                Color cur = new Color(image.getRGB(j, i));
                ArrayList<Point> toVisit = new ArrayList<>();

                if(visited.getRGB(j, i) == 0 && colorMatch(targetColor, cur)){

                    Point curPoint = new Point(j, i);
                    curRegion.add(curPoint);
                    visited.setRGB(j, i, 1);
                    toVisit.add(curPoint);

                    while(!toVisit.isEmpty()){

                        Point point = toVisit.get(0);
                        toVisit.remove(0);
                        curRegion.add(point);

                        for (int a = point.y - 1; a < point.y + 2; a++){
                            for(int b = point.x - 1; b < point.x + 2; b++){
                                if((a >= 0) && (b < image.getWidth()) && (b >= 0) && (a < image.getHeight()) && (visited.getRGB(b, a) == 0)){
                                    Color pointColor = new Color(image.getRGB(b, a));

                                    if(colorMatch(targetColor, pointColor )){
                                        Point addPoint = new Point(b, a);
                                        toVisit.add(addPoint);
                                        visited.setRGB(b, a, 1);
                                    }
                                }
                            }
                        }
                    }
                    if(curRegion.size() >= minRegion){
                        regions.add(curRegion);
                    }
                }
            }
        }
        //  If a pixel is unvisited and of the correct color
        //    Start a new region
        //    Keep track of which pixels need to be visited, initially just that one
        //    As long as there's some pixel that needs to be visited
        //      Get one to visit
        //      Add it to the region
        //      Mark it as visited
        //      Loop over all its neighbors
        //        If the neighbor is of the correct color
        //          Add it to the list of pixels to be visited
        //    If the region is big enough to be worth keeping, do so


    }

    /**
     * Tests whether the two colors are "similar enough" (your definition, subject to the maxColorDiff threshold, which you can vary).
     */
    protected static boolean colorMatch(Color c1, Color c2) {
        int red = c1.getRed();
        int blue = c1.getBlue();
        int green = c1.getGreen();

        return Math.abs(c1.getRed() - c2.getRed()) <= maxColorDiff &&
                Math.abs(c1.getGreen() - c2.getGreen()) <= maxColorDiff &&
                Math.abs(c1.getBlue() - c2.getBlue()) <= maxColorDiff;
    }

    /**
     * Returns the largest region detected (if any region has been detected)
     */
    public ArrayList<Point> largestRegion() {

        int maxLength = 0;
        ArrayList<Point> maxRegion = new ArrayList<>();
        for(ArrayList<Point> array : regions ){
            if(array.size() > maxLength){
                maxLength = array.size();
                maxRegion = array;
            }
        }
        return maxRegion;
    }

    /**
     * Sets recoloredImage to be a copy of image,
     * but with each region a uniform random color,
     * so we can see where they are
     */
    public void recolorImage() {
        // First copy the original
        recoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
        // Now recolor the regions in it
        for(ArrayList<Point> region : regions){
            Color randomColor = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255) );
            for (Point point : region){
                recoloredImage.setRGB(point.x, point.y, randomColor.getRGB());
            }
        }

    }
}
