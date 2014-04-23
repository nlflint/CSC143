import org.junit.*;

/**
 * Tests for CSC143Rectangle class
 *
 * @author Nathan Flint
 * @version Lab3: JUnit Testing
 */
public class CSC143RectangleTest
{
    private final int givenWidth = 10;
    private final int givenHeight = 20;
    private final double expectedArea = 200.0;
    private CSC143Rectangle testRectangle;

    /**
     * Test initialization
     */
    @Before
    public void testSetup()
    {
        testRectangle = new CSC143Rectangle(givenWidth, givenHeight);
    }

    /**
     * Verify the getWidth function returns width of rectangle
     */
    @Test
    public void width()
    {
        // Act
        int actualWidth = testRectangle.getWidth();

        // Assert
        Assert.assertEquals("Unexpected width", givenWidth, actualWidth);
    }

    /**
     * Verify getHeight function returns height of the rectangle
     */
    @Test
    public void height()
    {
        // Act
        int actualHeight = testRectangle.getHeight();

        // Assert
        Assert.assertEquals("Unexpected height", givenHeight, actualHeight);
    }

    /**
     * Verify getArea returns the area of the rectangle
     */
    @Test
    public void area()
    {
        // Act
        double actualArea = testRectangle.getArea();

        // Assert
        Assert.assertEquals("Unexpected area", expectedArea ,actualArea, 0);
    }

    /**
     * Verify negative width not allowed
     */
    @Test(expected=IllegalArgumentException.class)
    public void negativeWidthNotAllowed()
    {
        // Arrange
        final int negativeWidth = -10;

        // Act
        CSC143Rectangle rectangle = new CSC143Rectangle(negativeWidth, 20);

    }

    /**
     * Verify negative height not allowed
     */
    @Test(expected=IllegalArgumentException.class)
    public void negativeHeightNotAllowed()
    {
        // Arrange
        final int negativeHeight = -10;

        // Act
        CSC143Rectangle rectangle = new CSC143Rectangle(20, negativeHeight);
    }

    /**
     * Verify that if area can be greater than maximum possible integer
     */
    @Test
    public void areaCanOverflowIntegerMaximum()
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
