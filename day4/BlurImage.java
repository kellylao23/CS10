import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Blurs each pixel in an image by averaging each pixel's color with its neighbors with "radius" r
 * where radius extends r rows and above to r row below, r cols left to r cols right
 *
 * @author Kelly Lao, Dartmouth CS10, Winter 2025
 */
public class BlurImage {
    public static void main(String[] args) {
        int radius = 25; //average r row above to r row below, r cols left to r cols right

        //load image and create a blank image called result
        BufferedImage image = ImageIOLibrary.loadImage("day4/baker.jpg");
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        //Nested loop over every pixel in original image
        for (int y = 0; y < image.getHeight(); y++){
            for (int x = 0; x < image.getWidth(); x++){
                int sumR = 0, sumG = 0, sumB = 0;
                int n = 0;
                //Nested loop over neighbors
                //but be careful not to go outside image (max, min stuff)
                for (int ny = Math.max(0, y-radius); ny < Math.min(image.getHeight(), y+1+radius); ny++){
                    for (int nx = Math.max(0, x-radius); nx < Math.min(image.getWidth(), x+1+radius); nx++){
                        //Add all the neighbors (&self) to the running totals
                        Color c = new Color(image.getRGB(nx, ny));
                        sumR += c.getRed();
                        sumG += c.getGreen();
                        sumB += c.getBlue();
                        n++;
                    }
                }
                Color newColor = new Color(sumR/n, sumG/n, sumB/n);
                result.setRGB(x, y, newColor.getRGB());
            }
        }
        ImageGUI gui = new ImageGUI("Blurred image", image, result);
    }
}
