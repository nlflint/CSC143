import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for CSC143Rectangle class
 *
 * @author Nathan Flint
 * @version Lab3: JUnit Testing
 */
@RunWith(JUnit4.class)
public class CSC143CircleTest
{
    private final int givenDiameter = 20;
    private final double expectedArea = 314.1592653589793;
    private CSC143Circle testCircle;

    @Before
    public void testSetup()
    {
        testCircle = new CSC143Circle(givenDiameter);
    }

    @Test
    public void diameter()
    {
        // Act
        int actualDiameter = testCircle.getWidth();

        // Assert
        Assert.assertEquals("Unexpected diameter", givenDiameter, actualDiameter);
    }

    @Test
    public void area()
    {
        // Act
        double actualArea = testCircle.getArea();

        // Assert
        Assert.assertEquals("Unexpected area", expectedArea ,actualArea, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void negativeDiameterNotAllowed()
    {
        // Arrange
        final int negativeDiameter = -10;

        // Act
        CSC143Circle circle = new CSC143Circle(negativeDiameter);

    }

    @Test
    public void areaCanOverflowIntegerMaximum()
    {
        // Arrange
        final int diameter = 100000;
        final double expectedLargeArea = 7853981633.974483096157;
        CSC143Circle circle = new CSC143Circle(diameter);

        // Act
        double actualArea = circle.getArea();

        // Assert
        Assert.assertEquals("Unexpected area", expectedLargeArea ,actualArea, .000001);
    }


}

