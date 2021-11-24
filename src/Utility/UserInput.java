package Utility;
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
        System.out.println(message);
        double value = input.nextDouble();
        input.nextLine();
        return value;
    }

    public static void exitScanner() {
        input.close();
    }
}

