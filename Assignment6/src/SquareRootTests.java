import org.junit.Assert;
import org.junit.Test;
//import static org.junit.runner;

/**
 * Square root tests
 * Grading Level: Challenge
 *
 * @author NathanF
 * @version Assignment 6: Newton-Raphson Roots
 */
public class SquareRootTests {
    @Test
    public void easy() {
         // Arrange data
        double squareRoot = 25.0;
        double expectedRoot = 5.0;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.sqrt(squareRoot), getEpsilon(expectedRoot));
    }

   @Test
    public void large() {
        // Arrange data
        double squareRoot = 1e7;
        double expectedRoot = 3162.277660;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.sqrt(squareRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void small() {
        // Arrange data
        double squareRoot = 1.254e-4;
        double expectedRoot = 0.011198214143;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.sqrt(squareRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void veryLarge() {
        // Arrange data
        double squareRoot = 1e16;
        double expectedRoot = 1e8;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.sqrt(squareRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void verySmall() {
        // Arrange data
        double squareRoot = 1e-16;
        double expectedRoot = 1e-8;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.sqrt(squareRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void veryVeryLarge() {
        // Arrange data
        double squareRoot = 1e58;
        double expectedRoot = 1e29;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.sqrt(squareRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void veryVerySmall() {
        // Arrange data
        double squareRoot = 1e-58;
        double expectedRoot = 1e-29;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.sqrt(squareRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void one() {
        // Arrange data
        double squareRoot = 1.0;
        double expectedRoot = 1.0;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.sqrt(squareRoot), getEpsilon(expectedRoot));
    }

    @Test
    public void zero() {
        // Arrange data
        double squareRoot = 0.0;
        double expectedRoot = 0.0;

        // Act and assert
        Assert.assertEquals(expectedRoot, NewtonRaphson.sqrt(squareRoot), getEpsilon(expectedRoot));
    }



    @Test(expected = ConvergenceException.class)
    public void tooLarge() {
        NewtonRaphson.sqrt(1e60);
    }

    @Test(expected = ConvergenceException.class)
    public void tooSmall() {
        NewtonRaphson.sqrt(1e-60);
    }

    @Test(expected = ConvergenceException.class)
    public void impossible() {
        NewtonRaphson.sqrt(Double.POSITIVE_INFINITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negative()
    {
        NewtonRaphson.sqrt(-4);
    }

    private double getEpsilon(double expectedResult) {
        double epsilon = expectedResult / 1e8;
        return epsilon;
    }
}
