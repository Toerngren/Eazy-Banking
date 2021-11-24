package Menus;

import Utility.Printing;
import Utility.UserInput;

import java.util.Scanner;

public class MyPage {

        public void myPage() {
            Scanner input = new Scanner(System.in);
            String option;

            do {
                Printing.myPage();
                option = input.nextLine();
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
                }
            } while (!(option.equals("0")));
            UserInput.exitScanner();
        }
    }