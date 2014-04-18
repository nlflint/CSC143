import java.util.Arrays;

/**
 * Created by nate on 4/9/14.
 */
public class balancePoint {
    public static int balancePoint(int[] array) {

        int leftSide = 0;
        int rightSide = 0;
        for (int num : array)
            rightSide += num;

        if (rightSide == 0 && array.length > 0)
            return -1;

        for(int i = 0; i < array.length; i++)
        {
            int number = array[i];
            leftSide += number;
            rightSide -= number;
            if (leftSide == rightSide)
                return i + 1;
        }


        return -1;

    }

    public static void main(String[] args)
    {

        test(new int[] {1, 1, 1, 2, 1});
        test(new int[] {2, 1, 1, 2, 1});
        test(new int[] {1, 1, 1, 1, 4});
        test(new int[] {4, 1, 1, 1, 1});
        test(new int[] {1, 1, 1, 1, 1, 1});
        test(new int[] {1, 20, 20, -10, 50, 1});
        test(new int[] {0, 0, 0});
        test(new int[] {1, -1});


    }

    private static void test(int[] input)
    {
        System.out.printf("%20s : %s\n", Arrays.toString(input), balancePoint(input));
    }
}
