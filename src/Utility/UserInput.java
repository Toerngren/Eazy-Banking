package Utility;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {

    public static Scanner input = new Scanner(System.in);

    public static int readInt(String message) {
        System.out.print(message);
        int value = input.nextInt();
        input.nextLine();
        return value;
    }

    public static String readLine(String message) {
        System.out.print(message);
        String sentence = input.nextLine();
        return sentence;
    }

    public static double readDouble(String message) {
        System.out.print(message);
        double value = 0;
        while (value == 0) {
            try {
                value = Double.parseDouble(input.next());
            } catch (Exception e) {
                System.out.print("Invalid input. Please type digits only: ");
            }
        }
        return value;
    }

    public static void exitScanner() {
        input.close();
    }
}

