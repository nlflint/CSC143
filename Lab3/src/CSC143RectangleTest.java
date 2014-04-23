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
public class CSC143RectangleTest
{
    private final int givenWidth = 10;
    private final int givenHeight = 20;
    private final double expectedArea = 200.0;
    private CSC143Rectangle testRectangle;

    @Before
    public void TestSetup()
    {
        testRectangle = new CSC143Rectangle(givenWidth, givenHeight);
    }

    @Test
    public void GetWidthStoredByConstructor()
    {
        // Act
        int actualWidth = testRectangle.getWidth();

        // Assert
        Assert.assertEquals("Unexpected width", givenWidth, actualWidth);
    }

    @Test
    public void GetHeightStoredByConstructor()
    {
        // Act
        int actualHeight = testRectangle.getHeight();

        // Assert
        Assert.assertEquals("Unexpected height", givenHeight, actualHeight);
    }

    @Test
    public void AreaIsProductOfWidthHeight()
    {
        // Act
        double actualArea = testRectangle.getArea();

        // Assert
        Assert.assertEquals("Unexpected area", expectedArea ,actualArea, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void NegativeWidthNotAllowed()
    {
        // Arrange
        final int negativeWidth = -10;

        // Act
        CSC143Rectangle rectangle = new CSC143Rectangle(negativeWidth, 20);

    }

    @Test(expected=IllegalArgumentException.class)
    public void NegativeHeightNotAllowed()
    {
        // Arrange
        final int negativeHeight = -10;

        // Act
        CSC143Rectangle rectangle = new CSC143Rectangle(20, negativeHeight);
    }



    @Test
    public void AreaCanOverflowIntegerMaximum()
    {
        // Arrange
        final int width = 100000;
        final int height = 100000;
        final double expectedLargeArea = 10000000000.0;
        CSC143Rectangle rectangle = new CSC143Rectangle(width, height);

        // Act
        double actualArea = rectangle.getArea();

        // Assert
        Assert.assertEquals("Unexpected area", expectedLargeArea ,actualArea, 0);
    }


}
