package nmmu.wrap301;

import java.util.Scanner;

/**
 * This class contains the logic code for the menu application. The menu actions are individual methods
 * that are called from the menu class.
 */
public class Controller {
    public String userName = "unknown human";

    public void whatIsYourNameAction() {
        System.out.print("What is your name?");
        Scanner in = new Scanner(System.in);
        userName = in.nextLine();
        System.out.println("Hello " + userName);
    }

    public void helloWorldAction() {
        if (userName == null)
            System.out.println("Hello World");
        else
            System.out.println("Hello World and " + userName + "!");
    }

    public void fiveTimesTableAction() {
        for (int n = 1; n <= 10; n++)
            System.out.printf("%d x 5 = %d\n", n, n * 5);
    }

    public void isPrimeAction() {
        System.out.print("Enter n:");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        int numFactors = 0;
        for (int i = 2; i < n; i++)
            if (n % i == 0) numFactors++;

        if (numFactors == 0)
            System.out.println(n + " is prime.");
        else
            System.out.println(n + " is not prime.");
    }

    public void isOddAction() {
        System.out.print("Enter n:");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        if (n % 2 == 0)
            System.out.println(n + " is not odd.");
        else
            System.out.println(n + " is odd.");
    }
}
