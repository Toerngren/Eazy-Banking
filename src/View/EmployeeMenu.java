package View;

import Utility.Printing;
import Utility.UserInput;

public class EmployeeMenu {

    public static void adminPage() { // Change to employeePage

        String option;

        do {
            Printing.employeeMenu();
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
