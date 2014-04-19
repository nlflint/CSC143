/**
 * Implements the Edgy filter
 *
 * Challenge Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 2: SnapShop
 */
public class EdgyFilter extends GaussianFilter
{
    // Overrides method with custom transform.
    protected int[] getTransform()
    {
        return new int[] {-1, -1, -1, -1, 9, -1, -1, -1, -1};
    }
}
