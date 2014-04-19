/**
 * Implements Gaussian Blur. Can also be inherited for other similar algorithms.
 * To reuse this code, getTrasnform() should be overridden with your transform.
 *
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 2: SnapShop
 *
 */
public class Gaussian implements Filter
{
    // Used to index an array of int for color
    private final static int RED = 0, GREEN = 1, BLUE = 2;

    // Describes the 3x3 transform in 1 array. this method will be customized by inheriting filters.
    protected int[] getTransform()
    {
        return new int[] {1, 2, 1, 2, 4, 2, 1, 2, 1};
    }

    public void filter(PixelImage theImage)
    {
        // get the data from the image
        Pixel[][] data = theImage.getData();

        // Create an array where final data will be copied.
        Pixel[][] finalPixels = new Pixel[data.length][data[0].length];

        // go through each pixel, row by row and column by column.
        for(int row = 0; row < data.length; row++)
        {
            for(int column = 0; column < data[row].length; column++)
            {
                Pixel newPixel;

                // Don't transform pixels around the edges
                if (row == 0 || column == 0 || row == data.length - 1 || column == data[0].length - 1)
                {
                    newPixel = data[row][column];
                }
                else
                {
                    Pixel[] neighboringPixels = new Pixel[] {
                            data[row - 1][column - 1],
                            data[row - 1][column],
                            data[row - 1][column + 1],
                            data[row][column - 1],
                            data[row][column],
                            data[row][column + 1],
                            data[row + 1][column - 1],
                            data[row + 1][column],
                            data[row + 1][column + 1]
                    };

                    newPixel = transformPixels(neighboringPixels);
                }
                finalPixels[row][column] =  newPixel;
            }
        }

        // update the image with the moved pixels
        theImage.setData(finalPixels);
    }

    private Pixel transformPixels(Pixel[] pixels)
    {
        // Transform the pixels into a new set of colors
        int[] newColors = transformPixelsToColors(pixels);

        // Adjust weights by transform total
        adjustColorWeights(newColors);

        // Adjust all colors to be between 0 and 255
        constrainColors(newColors);

        return new Pixel(newColors[RED], newColors[GREEN], newColors[BLUE]);
    }

    // Uses the transform to create a new set of colors
    private int[] transformPixelsToColors(Pixel[] pixels)
    {
        // Get custom transform.
        int[] transform = getTransform();

        // where the new colors will be stored
        int[] colors = new int[] {0, 0, 0};

        // Transform each pixel to the new colors.
        for (int i = 0; i < pixels.length; i++)
        {
            colors[RED] += pixels[i].rgb[Pixel.RED] * transform[i];
            colors[GREEN] += pixels[i].rgb[Pixel.GREEN] * transform[i];
            colors[BLUE] += pixels[i].rgb[Pixel.BLUE] * transform[i];
        }

        return colors;
    }

    // Adjusts the colors by the total weight of the transform
    private void adjustColorWeights(int[] colors)
    {
        int[] transform = getTransform();

        int transformTotalWeight = 0;
        for (int i = 0; i < transform.length; i++)
        {
            transformTotalWeight += transform[i];
        }

        colors[RED] /= transformTotalWeight;
        colors[GREEN] /= transformTotalWeight;
        colors[BLUE] /= transformTotalWeight;
    }

    // Insures colors are 0-255
    private void constrainColors(int[] colors)
    {
        for (int i = 0; i < colors.length; i++)
        {
            colors[i] = Math.max(colors[i], 0);
            colors[i] = Math.min(colors[i], 255);
        }

    }
}
