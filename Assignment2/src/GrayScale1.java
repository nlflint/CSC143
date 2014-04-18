/**
 * Converts Pixel Images to gray scale using 1st method
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 2: SnapShop
 */
public class GrayScale1 implements Filter {

    // Sets scale factors for R, G, and B. This allows the class to be reused by grayscale2.
    protected double getRedScale() {return 0.3;}
    protected double getGreenScale() {return 0.59;}
    protected double getBlueScale() {return 0.11;}

    /**
     * Overrides filter method to convert to gray scale
     * @param  theImage The image to modify
     */
    public void filter(PixelImage theImage) {
        // get the data from the image
        Pixel[][] data = theImage.getData();

        for(int i = 0; i < data.length; i++)
        {
            for(int j = 0; j < data[i].length; j++)
            {
                Pixel pixel = data[i][j];

                // Calculates luminosity using method1
                double luminosity = 0;
                luminosity += pixel.rgb[Pixel.RED] * getRedScale();
                luminosity += pixel.rgb[Pixel.GREEN] * getGreenScale();
                luminosity += pixel.rgb[Pixel.BLUE] * getBlueScale();

                // Save new gray scale pixel
                data[i][j] = new Pixel((int)luminosity,(int)luminosity,(int)luminosity);
            }
        }

        // update the image with the moved pixels
        theImage.setData(data);

    }
}
