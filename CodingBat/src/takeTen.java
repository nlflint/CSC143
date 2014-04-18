import java.util.Arrays;

/**
 * Created by nate on 4/9/14.
 */
public class takeTen {
    public static int[] takeTen(int[] array) {

        int currentArrayIndex = 0;
        int[] finalArray = new int[array.length];

        for (int number : array)
            if (number != 10)
                finalArray[currentArrayIndex++] = number;

        return finalArray;

    }

    public static void main(String[] args)
    {

        test(new int[] {1, 10, 10, 2});
        test(new int[] {10, 2, 10});
        test(new int[] {1, 99, 10});
        test(new int[] {10, 13, 10, 14});
        test(new int[] {1});
        test(new int[] {10, 10});


    }

    private static void test(int[] input)
    {
        System.out.printf("%20s : %s\n", Arrays.toString(input), Arrays.toString(takeTen(input)));
    }
}

