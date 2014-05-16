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
        try {
            return sqrtFormula(1.0, n, 0);
        } catch (Exception e) {
            System.err.printf("Unable to calculate root for %d. Over 100 iterations and answer not reached!", n);
        }
        return -1;
    }

    private static double sqrtFormula(double guess, double squareRoot, int iteration)
            throws Exception {
        if (iteration > 100)
            throw new Exception("Over 100 iterations reached");

        double newGuess = (guess + squareRoot / guess) / 2.0;


        double newGuessSquaredMinusSquareRoot = (newGuess * newGuess - squareRoot);
        double check =  newGuessSquaredMinusSquareRoot / squareRoot;
        if ( check < 1e-10)
            return newGuess;
        else
            return sqrtFormula(newGuess, squareRoot, iteration++);



    }

    public static double cbrt(double n) {
        try {
            return cbrtFormula(1.0, n, 0);
        } catch (Exception e) {
            System.err.printf("Unable to calculate root for %d. Over 100 iterations and answer not reached!", n);
        }
        return -1;
    }

    private static double cbrtFormula(double guess, double cubeRoot, int iteration) throws Exception {
        if (iteration > 100)
            throw new Exception("Over 100 iterations reached");

        double newGuess = (guess * 2 + cubeRoot / (guess * guess)) / 3.0;


        double newGuessCubedMinusCubeRoot = newGuess * newGuess * newGuess - cubeRoot;
        double check =  newGuessCubedMinusCubeRoot / cubeRoot;
        if ( check < 1e-10)
            return newGuess;
        else
            return cbrtFormula(newGuess, cubeRoot, iteration++);



    }

}