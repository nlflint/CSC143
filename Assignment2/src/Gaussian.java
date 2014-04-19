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
        Pixel newPixel = transformPixelsToColors(pixels);

        // Adjust weights by transform total
        adjustColorWeights(newPixel);

        // Adjust all colors to be between 0 and 255
        constrainColors(newPixel);

        return newPixel;
    }

    // Uses the transform to create a new set of colors
    private Pixel transformPixelsToColors(Pixel[] pixels)
    {
        // Get custom transform.
        int[] transform = getTransform();

        // where the new colors will be stored
        int red = 0, green = 0, blue = 0;

        // Transform each pixel to the new colors.
        for (int i = 0; i < pixels.length; i++)
        {
            red += pixels[i].rgb[Pixel.RED] * transform[i];
            green += pixels[i].rgb[Pixel.GREEN] * transform[i];
            blue += pixels[i].rgb[Pixel.BLUE] * transform[i];
        }

        return new Pixel(red, green, blue);
    }

    // Adjusts the colors by the total weight of the transform
    private void adjustColorWeights(Pixel newPixel)
    {
        int[] transform = getTransform();

        int transformTotalWeight = 0;
        for (int i = 0; i < transform.length; i++)
        {
            transformTotalWeight += transform[i];
        }

        newPixel.rgb[Pixel.RED] /= transformTotalWeight;
        newPixel.rgb[Pixel.GREEN] /= transformTotalWeight;
        newPixel.rgb[Pixel.BLUE] /= transformTotalWeight;
    }

    // Insures colors are 0-255
    private void constrainColors(Pixel newPixel)
    {
        for (int i = 0; i < newPixel.rgb.length; i++)
        {
            newPixel.rgb[i] = Math.max(newPixel.rgb[i], 0);
            newPixel.rgb[i] = Math.min(newPixel.rgb[i], 255);
        }

    }
}
