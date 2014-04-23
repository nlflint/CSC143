import org.junit.*;

/**
 * Tests for CSC143Rectangle class
 *
 * @author Nathan Flint
 * @version Lab3: JUnit Testing
 */
public class CSC143CircleTest
{
    private final int givenDiameter = 20;
    private final double expectedArea = 314.1592653589793;
    private CSC143Circle testCircle;

    /**
     * Initializes test data
     */
    @Before
    public void testSetup()
    {
        testCircle = new CSC143Circle(givenDiameter);
    }

    /**
     * Verify getDiameter returns diameter
     */
    @Test
    public void diameter()
    {
        // Act
        int actualDiameter = testCircle.getDiameter();

        // Assert
        Assert.assertEquals("Unexpected diameter", givenDiameter, actualDiameter);
    }

    /**
     * Verify that getArea returns the area of the circle
     */
    @Test
    public void area()
    {
        // Act
        double actualArea = testCircle.getArea();

        // Assert
        Assert.assertEquals("Unexpected area", expectedArea ,actualArea, 0);
    }

    /**
     * Verify that negative diameter length throws exception during instantiation
     */
    @Test(expected=IllegalArgumentException.class)
    public void negativeDiameterNotAllowed()
    {
        // Arrange
        final int negativeDiameter = -10;

        // Act
        CSC143Circle circle = new CSC143Circle(negativeDiameter);

    }

    /**
     * Verify that area can be greater than the integer maximum
     */
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

