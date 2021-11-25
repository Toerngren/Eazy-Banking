package Menus;
import Utility.Controller;
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
                    Controller controller = new Controller();
                    String name = UserInput.readLine("Customer name:");
                    String email = UserInput.readLine("Customer email:");
                    int personalNumber = UserInput.readInt("Customer personal number:");
                    String password = UserInput.readLine("Customer password:");
                    String telephone = UserInput.readLine("Customer telephone number:");
                    String pincode = UserInput.readLine("Customer pincode:");
                    String message = controller.createCustomer(name, email, personalNumber, password, telephone, pincode);
                    System.out.println(message);
                    }
                    break;
                case "4":{
                    Controller controller = new Controller();
                    String name = UserInput.readLine("Employee name:");
                    String email = UserInput.readLine("Employee email:");
                    int personalNumber = UserInput.readInt("Employee personal number:");
                    String password = UserInput.readLine("Employee password:");
                    String telephone = UserInput.readLine("Employee telephone number:");
                    int employeeID = UserInput.readInt("Employee ID:");
                    String pincode = UserInput.readLine("Employee pincode:");
                    String message = controller.createEmployee(name, email, personalNumber, password, telephone, employeeID, pincode);
                    System.out.println(message);
                }
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
}
