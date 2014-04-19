/**
 * Created by nate on 4/18/14.
 */
public class Laplacian extends Gaussian
{
    // Overrides method from Gaussian with Laplacian transform.
    protected int[] getTransform()
    {
        return new int[] {-1, -1, -1, -1, 8, -1, -1, -1, -1};
    }
}
