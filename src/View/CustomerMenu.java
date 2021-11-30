package View;

import Utility.Printing;
import Utility.UserInput;


public class CustomerMenu {

        public static void customerMenu() {
            String option;

            do {
                Printing.customerMenu();
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