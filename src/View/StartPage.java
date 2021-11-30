package View;
import Utility.Printing;
import Utility.UserInput;
import controller.Service;


public class StartPage {

    public static void startPage() {
        String option;
        do {
            Printing.startPage();
            option = UserInput.readLine("");
            switch (option) {
                case "0":
                    System.out.println("Closing");
                    System.exit(0);
                    break;
                case "1": {
                    Service controller = new Service();
                    String personalNumber = UserInput.readLine("Customer personal number:");
                    String firstName = UserInput.readLine("Customer firstname:");
                    String lastName = UserInput.readLine("Customer lastname:");
                    String email = UserInput.readLine("Customer email:");
                    String telephone = UserInput.readLine("Customer telephone number:");
                    String password = UserInput.readLine("Customer password:");
                    String pinCode = UserInput.readLine("Customer pin code:");
                    String message = controller.createCustomer(personalNumber, firstName, lastName, email, telephone, password, pinCode);
                    System.out.println(message);
                    break;
                }
                case "2":
                    CustomerMenu.customerMenu();
                    break;
                case "3": {
                    EmployeeMenu.adminPage(); // employeePage
                }
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while(true);
    }
}