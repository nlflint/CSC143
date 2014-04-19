/**
 * Flips a PixelImage veritically
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 2: SnapShop
 */
public class FlipVerticalFilter implements Filter
{

    /**
     * Flips the givel pixel image vertically
     * @param  theImage The image to modify
     */
    public void filter(PixelImage theImage) {

        // get the data from the image
        Pixel[][] data = theImage.getData();

        // Swap rows from outer to inner.
        for (int i = 0; i < data.length / 2; i++)
        {
            // Define index to rows that will be swapped
            int sourceRow = i;
            int destinationRow = data.length - 1 - i;

            // Swap source and destination rows using a temp value.
            Pixel[] tempPixels = data[sourceRow];
            data[sourceRow] = data[destinationRow];
            data[destinationRow] = tempPixels;
        }

        // update the image with the moved pixels
        theImage.setData(data);

    }
}
