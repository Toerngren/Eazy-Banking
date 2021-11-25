package Menus;

import Utility.Controller;
import Utility.Printing;
import Utility.UserInput;

import java.util.Scanner;

public class AdminPage {

    public static void adminPage() {

        String option;

        do {
            Printing.adminPage();
            option = UserInput.readLine("");
            switch (option) {

                case "0":
                    StartPage.startPage();
                    break;
                case "1":
                    System.out.println("no feature yet");
                    break;
                case "2":
                    System.out.println("no feature yet:");
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }
}
