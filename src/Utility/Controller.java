package Utility;

import Menus.StartPage;
import User.Customer;
import User.Employee;
import User.UserAccount;

public class Controller { // This is like our facade. Where we place all our business logic

    public void launchMenu() {
        StartPage startPage = new StartPage();
        startPage.startPage();
    }

    public String createCustomer(String name, String email, int personalNumber, String password, String telephone, String pincode) {
        UserAccount customer = new Customer(name, email, personalNumber, password, telephone, pincode);
        // Add into appropriate List
        return "Customer successfully.";
    }

    public String createEmployee(String name, String email, int personalNumber, String password, String telephone, int employeeID, String pincode){
        UserAccount employee = new Employee(name, email, personalNumber, password, telephone, employeeID, pincode);
        // Add into appropriate List
        return "Employee created successfully.";
    }
}