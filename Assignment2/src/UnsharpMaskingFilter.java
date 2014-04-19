/**
 * Implements the unsharp masking filter
 *
 * Challenge Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 2: SnapShop
 */
public class UnsharpMaskingFilter extends GaussianFilter
{
    // Overrides method with custom transform.
    protected int[] getTransform()
    {
        return new int[] {-1, -2, -1, -2, 28, -2, -1, -2, -1};
    }
}
