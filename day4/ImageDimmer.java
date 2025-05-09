import java.awt.*;
import java.awt.image.BufferedImage;

/** Demonstrates reading an image, dimming all pixels, displaying the original and dimmed images
 *
 * @author Kelly Lao, Dartmouth CS10, Winter 2025
 */
public class ImageDimmer {

    /**
     * Dim every pixel by dividing the red, green, and blue color components by 2
     * @param originalImage image to dim
     * @return BufferedImage with each pixel dimmed
     */
    public BufferedImage dimImage(BufferedImage originalImage){
        //create blank image of the same size as the original
        BufferedImage dimmedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        //dim each pixel
        for (int y = 0; y < originalImage.getHeight(); y++){
            for (int x = 0; x < originalImage.getWidth(); x++){
                //get current color; scale each channel (but don't exceed 255); put new color
                Color color = new Color(originalImage.getRGB(x, y));
                int red = color.getRed()/2;
                int green = color.getGreen()/2;
                int blue = color.getBlue()/2;
                Color newColor = new Color(red, green, blue);
                dimmedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return dimmedImage;
    }

    public static void main(String[] args) {
        //load image and dim each pixel
        BufferedImage originalImage = ImageIOLibrary.loadImage("day4/baker.jpg");
        ImageDimmer dimmer = new ImageDimmer();
        BufferedImage dimmedImage = dimmer.dimImage(originalImage);

        //display results side by side
        ImageGUI gui = new ImageGUI("Dimmed", originalImage, dimmedImage);
    }
}