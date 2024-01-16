import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class ImageEditorPanel extends JPanel implements KeyListener {

    Color[][] pixels;
    final int IMAGE_WITH = 640;
    final int IMAGE_HEIGHT = 480;

    public ImageEditorPanel() {
        BufferedImage imageIn = null;
        try {
            // the image should be in the main project folder, not in \src or \bin
            imageIn = ImageIO.read(new File("phototwo.jpeg"));
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        pixels = makeColorArray(imageIn);
        setPreferredSize(new Dimension(pixels[0].length, pixels.length));
        setBackground(Color.BLACK);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        // paints the array pixels onto the screen
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                g.setColor(pixels[row][col]);
                g.fillRect(col, row, 1, 1);
            }
        }
    }

    public void run() {
        // call your image-processing methods here OR call them from keyboard event
        // handling methods
        // write image-processing methods as pure functions - for example: pixels =
        // pixels = fliphorzontial(pixels);
        // pixels = flipvert(pixels);
        // pixels = greyout(pixels);
        // pixels = blur(pixels);
        // pixels = brighten(pixels);
       // pixels = contrast(pixels);
      // pixels = vintage(pixels);

        repaint();
    }

  
  
  
  
    public static Color[][] vintage(Color[][] originalColors) {
        Color[][] vintageColors = new Color[originalColors.length][originalColors[0].length];

        for (int i = 0; i < originalColors.length; i++) {
            for (int r = 0; r < vintageColors[i].length; r++) {
                
            
           
            int red = originalColors[i][r].getRed();
            int green = originalColors[i][r].getGreen();
            int blue = originalColors[i][r].getBlue();

          
            int tanRed = (int) (0.393 * red + 0.769 * green + 0.189 * blue);
            int tanGreen = (int) (0.349 * red + 0.686 * green + 0.168 * blue);
            int tanBlue = (int) (0.272 * red + 0.534 * green + 0.131 * blue);

             tanRed = Math.min(255, Math.max(0, tanRed));
                tanGreen = Math.min(255, Math.max(0, tanGreen));
                tanBlue = Math.min(255, Math.max(0, tanBlue));

            vintageColors[i][r] = new Color(tanRed,tanGreen,tanBlue);

            }

        }
        return vintageColors;
    }

    private Color[][] contrast(Color[][] pixels2) {
        int rows = pixels2.length;
        int cols = pixels2[0].length;
        Color[][] contrastpix = new Color[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int getred = pixels2[r][c].getRed();
                int getblue = pixels2[r][c].getBlue();
                int getgreen = pixels2[r][c].getGreen();
                if (getred < 128) {
                    getred *= .8;
                } else {
                    getred *= 1.2;
                }

                if (getblue < 128) {
                    getblue *= .8;
                } else {
                    getblue *= 1.2;
                }

                if (getgreen < 128) {
                    getgreen *= .8;
                } else {
                    getgreen *= 1.2;
                }
                getred = Math.min(255, Math.max(0, getred));
                getgreen = Math.min(255, Math.max(0, getgreen));
                getblue = Math.min(255, Math.max(0, getblue));
                 
                contrastpix [r][c] = new Color(getred, getgreen, getblue);
                
            }

        }
        return contrastpix;
    }



    private Color[][] brighten(Color[][] pixels2) {
        int rows = pixels2.length;
        int cols = pixels2[0].length;
        Color[][] brightenpix = new Color[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int getred = pixels2[r][c].getRed();
                int getblue = pixels2[r][c].getBlue();
                int getgreen = pixels2[r][c].getGreen();
                if (getred < 225) {
                    getred += 30;

                }
                if (getblue < 225) {
                    getblue += 30;
                }
                if (getgreen < 225) {
                    getgreen += 30;
                }

                brightenpix[r][c] = new Color(getred, getgreen, getblue);
            }

        }

        return brightenpix;
    }

    public static Color[][] blur(Color[][] originalImage) {
        int rows = originalImage.length;
        int cols = originalImage[0].length;

        Color[][] blurredImage = new Color[rows][cols];
        int rad = 60; // Adjust the radius as needed

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // Calculate average RGB values of the pixel and its neighbors
                int redSum = 0, greenSum = 0, blueSum = 0;
                int count = 0;

                for (int i = r - rad; i <= r + rad; i++) {
                    for (int k = c - rad; k <= c + rad; k++) {
                        if (i >= 0 && i < rows && k >= 0 && k < cols) {
                            count++;
                            redSum += originalImage[i][k].getRed();
                            blueSum += originalImage[i][k].getBlue();
                            greenSum += originalImage[i][k].getGreen();
                        }
                    }
                }

                // Calculate average RGB values
                int avgRed = redSum / count;
                int avgGreen = greenSum / count;
                int avgBlue = blueSum / count;

                blurredImage[r][c] = new Color(avgRed, avgGreen, avgBlue);
            }
        }

        return blurredImage;
    }

    private Color[][] greyout(Color[][] pixels2) {
        for (int row = 0; row < pixels2.length; row++) {
            for (int col = 0; col < pixels2[row].length; col++) {
                Color colorpixel = pixels2[row][col];
                double redco = colorpixel.getRed();
                double blueco = colorpixel.getBlue();
                double greencol = colorpixel.getGreen();
                int greycol = (int) ((redco * .299) + (.114 * blueco) + (.587 * greencol));
                pixels2[row][col] = new Color(greycol, greycol, greycol);

            }
        }

        return pixels2;
    }

    private Color[][] flipvert(Color[][] pixels2) {
        int rows = pixels2.length;
        int columns = pixels2[0].length;

        Color[][] flippedArray = new Color[rows][columns];

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                flippedArray[row][col] = pixels2[rows - 1 - row][col];
            }
        }

        return flippedArray;
    }

    public Color[][] fliphorzontial(Color[][] pixels2) {
        int rows = pixels2.length;
        int columns = pixels2[0].length;

        Color[][] flippedArray = new Color[rows][columns];

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                flippedArray[row][col] = pixels2[row][columns - 1 - col];
            }
        }

        return flippedArray;
    }

    public Color[][] makeColorArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Color[][] result = new Color[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color c = new Color(image.getRGB(col, row), true);
                result[row][col] = c;
            }
        }
        // System.out.println("Loaded image: width: " +width + " height: " + height);
        return result;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'g') {
            pixels = greyout(pixels);
            
        }
        if (e.getKeyChar() == 'v') {
            pixels = vintage(pixels);
           
        }
        
         if (e.getKeyChar() == 'f') {
            pixels = flipvert(pixels);
           
        }
         if (e.getKeyChar() == 'h') {
            pixels = fliphorzontial(pixels);
           
        }
        if (e.getKeyChar() == 'b') {
           pixels = blur(pixels);
          
       }
        if (e.getKeyChar() == 'c' ) {
           pixels = contrast(pixels);
          
       }
        
        


        repaint();

        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
