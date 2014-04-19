/**
 * Rotates the image clockwise
 *
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 2: SnapShop
 */
public class RotateClockwiseFilter implements Filter
{

    /**
     * Overrides filter method to rotate clockwise
     * @param  theImage The image to modify
     */
    public void filter(PixelImage theImage)
    {
        // get the data from the image
        Pixel[][] data = theImage.getData();

        // final image stored here
        Pixel[][] finalData = new Pixel[data[0].length][data.length];

        // This just transposes the images
        for(int i = 0; i < data.length; i++)
        {
            for(int j = 0; j < data[i].length; j++)
            {
                Pixel pixel = data[i][j];

                // Save new gray scale pixel
                finalData[j][i] = pixel;
            }
        }

        // update the image with the moved pixels
        theImage.setData(finalData);

        // The image must now be mirrored horizontally to finish the rotation.
        FlipHorizontalFilter flip = new FlipHorizontalFilter();
        flip.filter(theImage);
    }
}
