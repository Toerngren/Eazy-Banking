package View;

import Utility.Printing;
import Utility.UserInput;
import controller.Service;

public class EmployeeMenu {
    private static final Service service = new Service();

    public static void employeePage() { // Change to employeePage

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
                case "3":
                    System.out.println("no feature yet:");
                    break;
                case "4": {
                    System.out.println("no feature yet:");
                }
                    break;
                case "5": { // for testing
                    String personalNumber = UserInput.readLine("Enter the personal number of customer you want to change.");
                    String newFirstName = UserInput.readLine("Enter new first name.");
                    String message = service.editCustomerFirstName(personalNumber, newFirstName);
                    System.out.println(message);
                }
                    break;
                case"6": {
                    String message = service.printAllCustomers();
                    System.out.println(message);
                }
                break;
                    default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }
}
