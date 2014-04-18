import java.util.Arrays;

/**
 * Created by nate on 4/9/14.
 */
public class triangular
{
    public static int[] triangular(int n) {
        int arrayLength = n * (n + 1) / 2;
        int[] finalArray = new int[arrayLength];

        int currentArrayIndex = 0;
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= i; j ++)
                finalArray[currentArrayIndex++] = j;


        return finalArray;

    }

    public static void main(String[] args)
    {

        test(1);
        test(0);
        test(4);
        test(3);
        test(10);


    }

    private static void test(int input)
    {
        System.out.printf("%20s : %s\n", input, Arrays.toString(triangular(input)));
    }
}
