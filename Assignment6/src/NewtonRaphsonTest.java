import org.junit.Assert;
import org.junit.Test;
//import static org.junit.runner;

/**
 * Created by nate on 5/15/14.
 */
public class NewtonRaphsonTest {
    private final double defaultEpsilon = 0.00000001;

    @Test
    public void squareRootEasy()
    {
        Assert.assertEquals(5.0, NewtonRaphson.sqrt(25.0), defaultEpsilon);
    }

    @Test
    public void squareRootEasy2()
    {
        Assert.assertEquals(12.0, NewtonRaphson.sqrt(144.0), defaultEpsilon);
    }

    @Test
    public void squareRootEasySmall()
    {
        Assert.assertEquals(0.5, NewtonRaphson.sqrt(0.25), defaultEpsilon);
    }

    @Test
    public void squareRootLarge()
    {
        Assert.assertEquals(3162.27766017, NewtonRaphson.sqrt(1e7), defaultEpsilon);
    }

    @Test
    public void squareRootSmall()
    {
        Assert.assertEquals(0.01119821414, NewtonRaphson.sqrt(1.254e-4), defaultEpsilon);
    }

    @Test
    public void cubeRootEasy()
    {
        Assert.assertEquals(3.0, NewtonRaphson.cbrt(27.0), defaultEpsilon);
    }
}
