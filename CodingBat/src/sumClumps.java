import java.util.Arrays;

/**
 * Created by nate on 4/9/14.
 */
public class sumClumps {
    public static int sumClumps(int[] array) {
        if (array.length < 1)
            return 0;

        int currentClump = array[0];
        int currentClumpLength = 1;
        int finalSum = 0;

        for (int i = 1; i < array.length; i++)
        {
            int currentNumber = array[i];
            if (currentNumber == currentClump)
            {
                currentClumpLength++;
                continue;
            }
            else if (currentClumpLength > 1)
                finalSum += currentClump * currentClumpLength;

            currentClump = currentNumber;
            currentClumpLength = 1;

        }

        if (currentClumpLength > 1)
            finalSum += currentClump * currentClumpLength;

        return finalSum;

    }

    public static void main(String[] args)
    {

        test(new int[] {1, 2, 2, 3, 4, 4});
        test(new int[] {1, 1, 2, 1, 1});
        test(new int[] {1, 1, 1, 1, 1});
        test(new int[] {1, 1, 1, 2, 3, 3});
        test(new int[] {1, 2, 3, 4, 5, 6});


    }

    private static void test(int[] input)
    {
        System.out.printf("%20s : %s\n", Arrays.toString(input), sumClumps(input));
    }
}
