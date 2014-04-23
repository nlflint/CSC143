import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for CSC143Triangle class
 *
 * @author Nathan Flint
 * @version Lab3: JUnit Testing
 */
@RunWith(JUnit4.class)
public class CSC143TriangleTest
{
    private final int givenWidth = 10;
    private final int givenHeight = 20;
    private final double expectedArea = 100.0;
    private CSC143Triangle testTriangle;

    @Before
    public void TestSetup()
    {
        testTriangle = new CSC143Triangle(givenWidth, givenHeight);
    }

    @Test
    public void GetWidthStoredByConstructor()
    {
        // Act
        int actualWidth = testTriangle.getWidth();

        // Assert
        Assert.assertEquals("Unexpected width", givenWidth, actualWidth);
    }

    @Test
    public void GetHeightStoredByConstructor()
    {
        // Act
        int actualHeight = testTriangle.getHeight();

        // Assert
        Assert.assertEquals("Unexpected height", givenHeight, actualHeight);
    }

    @Test
    public void AreaCalculatedProperly()
    {
        // Act
        double actualArea = testTriangle.getArea();

        // Assert
        Assert.assertEquals("Unexpected area", expectedArea ,actualArea, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void NegativeWidthNotAllowed()
    {
        // Arrange
        final int negativeWidth = -10;

        // Act
        CSC143Triangle triangle = new CSC143Triangle(negativeWidth, 20);

    }

    @Test(expected=IllegalArgumentException.class)
    public void NegativeHeightNotAllowed()
    {
        // Arrange
        final int negativeHeight = -10;

        // Act
        CSC143Triangle triangle = new CSC143Triangle(20, negativeHeight);
    }



    @Test
    public void AreaCanOverflowIntegerMaximum()
    {
        // Arrange
        final int width = 100000;
        final int height = 100000;
        final double expectedLargeArea = 10000000000.0;
        CSC143Triangle triangle = new CSC143Triangle(width, height);

        // Act
        double actualArea = triangle.getArea();

        // Assert
        Assert.assertEquals("Unexpected area", expectedLargeArea ,actualArea, 0);
    }
}
