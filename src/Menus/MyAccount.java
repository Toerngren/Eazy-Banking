package Menus;

import Utility.Printing;
import Utility.UserInput;

public class MyAccount {

    public static void myAccount() {
        String option;

        do {
            Printing.myAccount();
            option = UserInput.readLine("");
            switch (option) {
                case "0" -> StartPage.startPage();
                case "1" -> System.out.println("no feature yet");
                case "2" -> System.out.println("no feature yet:");
                case "3" -> System.out.println("no feature yet:");
                case "4" -> System.out.println("no feature yet:");
                case "5" -> System.out.println("no feature yet:");
                case "6" -> System.out.println("no feature yet:");
                default -> Printing.invalidEntry();
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }

}