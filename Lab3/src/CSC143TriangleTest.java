import org.junit.*;

/**
 * Tests for CSC143Triangle class
 *
 * @author Nathan Flint
 * @version Lab3: JUnit Testing
 */
public class CSC143TriangleTest
{
    private final int givenBase = 21;
    private final int givenHeight = 33;
    private final double expectedArea = 346.5;
    private CSC143Triangle testTriangle;

    /**
     * Initialize test data
     */
    @Before
    public void testSetup()
    {
        testTriangle = new CSC143Triangle(givenBase, givenHeight);
    }

    /**
     * Verify getBase return the base of the triangle
     */
    @Test
    public void base()
    {
        // Act
        int actualBase = testTriangle.getBase();

        // Assert
        Assert.assertEquals("Unexpected width", givenBase, actualBase);
    }

    /**
     * Verify getHeight returns the height of the triangle
     */
    @Test
    public void height()
    {
        // Act
        int actualHeight = testTriangle.getHeight();

        // Assert
        Assert.assertEquals("Unexpected height", givenHeight, actualHeight);
    }

    /**
     * Verify getArea returns the Base * Height / 2
     */
    @Test
    public void area()
    {
        // Act
        double actualArea = testTriangle.getArea();

        // Assert
        Assert.assertEquals("Unexpected area", expectedArea ,actualArea, 0);
    }

    /**
     * Verify that negative base length throws exception
     */
    @Test(expected=IllegalArgumentException.class)
    public void negativeBaseNotAllowed()
    {
        // Arrange
        final int negativeBase = -10;

        // Act
        CSC143Triangle triangle = new CSC143Triangle(negativeBase, 20);

    }

    /**
     * Verify that negative height length throws exception
     */
    @Test(expected=IllegalArgumentException.class)
    public void negativeHeightNotAllowed()
    {
        // Arrange
        final int negativeHeight = -10;

        // Act
        CSC143Triangle triangle = new CSC143Triangle(20, negativeHeight);
    }

    /**
     * Verify that area can be greater than integer maximum
     */
    @Test
    public void areaCanOverflowIntegerMaximum()
    {
        // Arrange
        final int base = 100000;
        final int height = 100000;
        final double expectedLargeArea = 5000000000.0;
        CSC143Triangle triangle = new CSC143Triangle(base, height);

        // Act
        double actualArea = triangle.getArea();

        // Assert
        Assert.assertEquals("Unexpected area", expectedLargeArea ,actualArea, 0);
    }
}
