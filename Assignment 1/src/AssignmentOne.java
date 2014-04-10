/**
 * Prints my name, a fact and some output based on command line arguments
 *
 * @author Nathan Flint
 * @version Assignment 1: Class tools
 */
public class AssignmentOne
{
    /**
     * Application method
     * @param args The command line arguments
     */
    public static void main(String[] args)
    {
        // Prints my name
        System.out.println("My name is Nathan Flint.");

        // Prints my fact
        System.out.println("Abraham Lincoln was the 16th President of the United States.");

        // Handles the commandline arguments
        if (args.length == 0)
            System.out.println("No command-line arguments given.");
        else if (args.length == 1 && args[0].equals("OOP"))
            System.out.println("The command-line arguments say \"OOP\".");
        else if (args.length == 2 && args[0].equals("CSC") && args[1].equals("143"))
            System.out.println("The command-line arguments say \"CSC 143\".");
        else
            System.out.println("The command-line arguments are not recognized.");
    }

}
