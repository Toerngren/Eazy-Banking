package View;
import Utility.Printing;
import Utility.UserInput;
import controller.Service;


public class StartPage {
    private static final Service service = new Service();

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
                case "1": { //TODO Adrian
                    // Create method below and call on it here
                    String personalNumber = UserInput.readLine("Customer personal number:");
                    String firstName = UserInput.readLine("Customer firstname:");
                    String lastName = UserInput.readLine("Customer lastname:");
                    String email = UserInput.readLine("Customer email:");
                    String telephone = UserInput.readLine("Customer telephone number:");
                    String password = UserInput.readLine("Customer password:");
                    String pinCode = UserInput.readLine("Customer pin code:");
                    String message = service.createCustomer(personalNumber, firstName, lastName, email, telephone, password, pinCode);
                    System.out.println(message);
                    break;
                }
                case "2":
                    CustomerMenu.customerMenu();
                    break;
                case "3": {
                    EmployeeMenu.employeePage();
                }
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while(true);
    }
}