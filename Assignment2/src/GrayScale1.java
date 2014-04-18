/**
 * Converts Pixel Images to gray scale using 1st method
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 2: SnapShop
 */
public class GrayScale1 implements Filter {
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
                luminosity += pixel.rgb[Pixel.RED] * .30;
                luminosity += pixel.rgb[Pixel.GREEN] * .59;
                luminosity += pixel.rgb[Pixel.BLUE] * .11;

                // Save new gray scale pixel
                data[i][j] = new Pixel((int)luminosity,(int)luminosity,(int)luminosity);
            }
        }

        // update the image with the moved pixels
        theImage.setData(data);

    }
}
