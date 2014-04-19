/**
 * Rotates the image counter clockwise
 *
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 2: SnapShop
 */
public class RotateCounterClockwiseFilter implements Filter
{

    /**
     * Overrides filter method to rotate counter clockwise
     * @param  theImage The image to modify
     */
    public void filter(PixelImage theImage)
    {
        // Three clockwise rotations equals 1 counter clockwise rotation
        RotateClockwiseFilter rotate = new RotateClockwiseFilter();
        int rotations = 3;
        while (rotations-- > 0)
            rotate.filter(theImage);

    }
}
