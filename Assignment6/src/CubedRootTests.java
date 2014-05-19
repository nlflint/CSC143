import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nathanf on 5/19/2014.
 */
public class CubedRootTests {
    private final double defaultEpsilon = 0.00000001;

    @Test
    public void cubeRootEasy()
    {
        // Arrange data
        double cubeRoot = 27.0;
        double expectedRoot = 3.0;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.cbrt(cubeRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void large()
    {
        // Arrange data
        double cubeRoot = 1e7;
        double expectedRoot = 215.443469;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.cbrt(cubeRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void small()
    {
        // Arrange data
        double cubeRoot = 1.254e-4;
        double expectedRoot = 0.0500532765;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.cbrt(cubeRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void veryLarge()
    {
        // Arrange data
        double cubeRoot = 1e25;
        double expectedRoot = 2.15443469e8;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.cbrt(cubeRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void verySmall()
    {
        // Arrange data
        double cubeRoot = 1e-13;
        double expectedRoot = 4.64158883e-5;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.cbrt(cubeRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void negativeEasy()
    {
        // Arrange data
        double cubeRoot = -27.0;
        double expectedRoot = -3.0;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.cbrt(cubeRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void negativeVeryLarge()
    {
        // Arrange data
        double cubeRoot = -1e25;
        double expectedRoot = -2.15443469e8;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.cbrt(cubeRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void negativeVerySmall()
    {
        // Arrange data
        double cubeRoot = -1e-13;
        double expectedRoot = -4.64158883e-5;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.cbrt(cubeRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void one()
    {
        // Arrange data
        double cubeRoot = 1.0;
        double expectedRoot = 1.0;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.cbrt(cubeRoot), getEpsilon(expectedRoot));
    }


    @Test
    public void zero()
    {
        // Arrange data
        double cubeRoot = 0.0;
        double expectedRoot = 0.0;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.cbrt(cubeRoot), getEpsilon(expectedRoot));
    }

    @Test(expected = ConvergenceException.class)
    public void impossible() {
        NewtonRaphson.cbrt(Double.POSITIVE_INFINITY);
    }

    private double getEpsilon(double expectedResult) {
        if (expectedResult > 0)
            return expectedResult / 1e8;
        else
            return expectedResult / -1e8;
    }
}
