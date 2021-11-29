package View;

import Utility.Printing;
import Utility.UserInput;

public class MyPage {

        public static void myPage() {
            String option;

            do {
                Printing.myPage();
                option = UserInput.readLine("");
                switch (option) {

                    case "0":
                        StartPage.startPage();
                        break;
                    case "1":
                        MyAccount.myAccount();
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