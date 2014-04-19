/**
 * Implements the LaplacianFilter filter
 *
 * Challenge Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 2: SnapShop
 */
public class LaplacianFilter extends GaussianFilter
{
    // Overrides method with custom transform.
    protected int[] getTransform()
    {
        return new int[] {-1, -1, -1, -1, 8, -1, -1, -1, -1};
    }
}
