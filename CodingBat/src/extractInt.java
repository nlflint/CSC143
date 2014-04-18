/**
 * Created by nate on 4/9/14.
 */
public class extractInt {

    public static int extractInt(String str) {
        String number = "";
        for (char element : str.toCharArray())
            if (Character.isDigit(element))
                number += element;

        int finalValue = 0;
        if (number.length() > 0)
            finalValue = Integer.parseInt(number);

        return finalValue;

    }

    public static void main(String[] args)
    {

        test("aa1bc2d3");
        test("aa11b33");
        test("Chocolate");
        test("123abc123");
        test("123");


    }

    private static void test(String input)
    {
        System.out.printf("%20s : %d\n", input, extractInt(input));
    }
}
