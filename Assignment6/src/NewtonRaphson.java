/**
 * Calculates square roots and cube roots
 *
 * Grading Level: Challenge
 *
 * @author Nathan Flint
 * @version Assignment 6: Newton-Raphson Roots
 */
public class NewtonRaphson {

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

    private static double sqrtFormula(double guess, double squareRoot, int iteration)
            throws ConvergenceException {
        if (iteration > 100)
            throw new ConvergenceException("Over 100 iterations reached");

        double newGuess = (guess + squareRoot / guess) / 2.0;


        double newGuessSquaredMinusSquareRoot = (newGuess * newGuess - squareRoot);
        double check =  newGuessSquaredMinusSquareRoot / squareRoot;
        if ( check < 1e-10)
            return newGuess;
        else {
            double ret = sqrtFormula(newGuess, squareRoot, ++iteration);
            return ret;
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
        if (iteration > 100)
            throw new ConvergenceException("Over 100 iterations reached");

        double newGuess = (guess * 2 + cubeRoot / (guess * guess)) / 3.0;

        double newGuessCubedMinusCubeRoot = newGuess * newGuess * newGuess - cubeRoot;
        double check =  newGuessCubedMinusCubeRoot / cubeRoot;
        if ( check < 1e-10)
            return newGuess;
        else
            return cbrtFormula(newGuess, cubeRoot, ++iteration);
    }

}

class ConvergenceException extends RuntimeException {
    public ConvergenceException(String message) {
        super(message);
    }
    
}