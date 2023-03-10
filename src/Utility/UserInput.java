package Utility;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class UserInput {
    public static final String EOL = System.lineSeparator();
    public static Scanner input = new Scanner(System.in);

    public static int readInt(String message) {
        try {
            System.out.print(message);
            int value = input.nextInt();
            input.nextLine();
            return value;
        } catch (InputMismatchException e) {
            System.out.println("Invalid entry. Please only use digits.");
            input.nextLine();
        }
        return readInt(message);
    }

    public static String readLine(String message) {
        System.out.print(message);
        String sentence = input.nextLine().trim();
        return sentence;
    }

    public static double readDouble(String message) {
        try {
            System.out.print(message);
            double value = input.nextDouble();
            input.nextLine();
            return value;
        } catch (InputMismatchException e) {
            System.out.println("Invalid entry. Please only use digits.");
            input.nextLine();
        }
        return readDouble(message);
    }

    public static String readLineYesNo(String message) {
        System.out.print(message);
        String sentence = input.nextLine().trim().toLowerCase(Locale.ROOT);
        if (sentence.equals("yes")) {
            return sentence;
        } else if (sentence.equals("no")) {
            return sentence;
        } else {
            System.out.println("Please write either Yes or No");
            return "";
        }
    }

    public static void exitScanner() {
        input.close();
    }
}

