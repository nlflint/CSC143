/**
 * Calculates square roots and cube roots
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 6: Newton-Raphson Roots
 */
public class NewtonRaphson {

    /**
     * Calculates the root from the given squared root.
     * @param n squared root
     * @return root of the squared root
     */
    public static double sqrt(double n) {
        // precondition, square root must be positive
        if (n < 0)
            throw new IllegalArgumentException("input square root must be greater than 0");

        // Special case for zeros
        if (n == 0)
            return 0;

        // enter a recursive loop
        return sqrtFormula(n, n, 0);
    }

    private static double sqrtFormula(double guess, double squareRoot, int iteration) {
        // Limit the recursion to 100 iterations
        if (iteration > 100)
            throw new ConvergenceException("Over 100 iterations reached");

        // Newton-Raphson for square roots
        double newGuess = (guess + squareRoot / guess) / 2.0;

        // Calculates if the new guess is within 1e10 significant digits
        double newGuessSquaredMinusSquareRoot = (newGuess * newGuess - squareRoot);
        double check =  newGuessSquaredMinusSquareRoot / squareRoot;
        if ( check < 1e-10)
            return newGuess;
        else {
            // If the guess is not close enough, then get closer
            return sqrtFormula(newGuess, squareRoot, ++iteration);
        }
    }

    public static double cbrt(double n) {
        // Special case for zeros
        if (n == 0)
            return 0;

        // Enter into a recursive loop
        return cbrtFormula(n, n, 0);
    }

    private static double cbrtFormula(double guess, double cubeRoot, int iteration) {
        // Limit the recursion to 100 iterations
        if (iteration > 100)
            throw new ConvergenceException("Over 100 iterations reached");

        // Newton-Raphson for cube roots
        double newGuess = (guess * 2 + cubeRoot / (guess * guess)) / 3.0;
        // Calculates if the new guess is within 1e10 significant digits
        double newGuessCubedMinusCubeRoot = newGuess * newGuess * newGuess - cubeRoot;
        double check =  newGuessCubedMinusCubeRoot / cubeRoot;
        if ( check < 1e-10)
            return newGuess;
        else
            // If the guess is not close enough, then get closer
            return cbrtFormula(newGuess, cubeRoot, ++iteration);
    }
}

/**
 * An exception for cases when Newton-Raphson takes more than 100 iterations to solve.
 */
class ConvergenceException extends RuntimeException {
    /**
     * Constructor.
     * @param message Exception message
     */
    public ConvergenceException(String message) {
        super(message);
    }
    
}