/**
 * Created by nate on 4/18/14.
 */
public class UnsharpMasking extends Gaussian
{
    // Overrides method with custom transform.
    protected int[] getTransform()
    {
        return new int[] {-1, -2, -1, -2, 28, -2, -1, -2, -1};
    }
}
