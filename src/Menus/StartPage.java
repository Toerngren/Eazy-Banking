package Menus;
import businessLogic.Service;
import Utility.Printing;
import Utility.UserInput;


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
                case "1":
                    MyPage.myPage();
                    break;
                case "2":
                    AdminPage.adminPage();
                    break;
                case "3": {
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
                    }
                    break;
                case "4":

                    break;
                case "5":
                    System.out.println("No feature yet");
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while(true);
    }

    // create methods for each case
}
