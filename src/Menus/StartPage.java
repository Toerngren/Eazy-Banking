package Menus;
import Utility.*;
import java.util.Scanner;

public class StartPage {

    public static Scanner input = new Scanner(System.in);

    public static void startPage() {
        String option;
        do {
            Printing.startPage();
            option = input.nextLine();
            switch (option) {
                case "0":
                    System.out.println("Closing");
                    System.exit(0);
                    break;
                case "1":
                    new MyPage().myPage();
                    break;
                case "2":
                    new AdminPage().adminPage();
                    break;
                case "3":
                    System.out.println("No feature yet");
                    break;
                case "4":
                    System.out.println("No feature yet");
                    break;
                case "5":
                    System.out.println("No feature yet");
                    break;
                default:
                    Printing.invalidEntry();
            }
        } while(!(option.equals("0")));
        UserInput.exitScanner();
    }
}
