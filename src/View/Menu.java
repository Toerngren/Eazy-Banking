package View;
import Utility.Printing;
import Utility.UserInput;
import businessLogic.User.Customer;
import controller.Service;


public class Menu {
     Service service = new Service();

    public void startPage() {
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
                    registerCustomer();
                    break;
                case "2":
                    String personalNumber = UserInput.readLine("Please enter your personalnumber: ");
                    if (!service.containsCustomer(personalNumber)){
                        System.out.println("No customer with that personal number.");
                        startPage();
                    }
                    String password = UserInput.readLine("Please enter your password: ");
                    Customer foundCustomer = service.findCustomer(personalNumber);
                    if (foundCustomer.verifyPassword(password)){
                        customerMenu(foundCustomer);
                    } else {
                        System.out.println("Wrong password.");
                    }
                    break;
                case "3": {
                    String username = UserInput.readLine("Please enter your username: ");
                    String employeePassword = UserInput.readLine("Please enter your password: ");
                    if (service.employeeLoginCheck(username, employeePassword)){
                        employeeMenu();
                    } else {
                        System.out.println("Wrong username/password.");
                    }
                }
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while(true);
    }

    /* ACCOUNTS MENU */

    public void customerMenu(Customer currentUser) {
        String option;
        do {
            Printing.customerMenu();
            option = UserInput.readLine("");
            switch (option) {
                case "0":
                    startPage();
                    break;
                case "1":
                    payTransferMenu(currentUser);
                    break;
                case "2":
                    myAccount(currentUser);
                    break;
                case "3":
                    loanMenu(currentUser);
                    break;
                case "4":
                    System.out.println("no feature yet.");
                    break;
                case "5":
                    kycMenu(currentUser);
                    break;
                case "6":
                    System.out.println("no feature yet.");
                    break;
                case "7":
                    System.out.println("no feature yet.");
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }

    /* ACCOUNTS MENU */
    public void myAccount(Customer currentUser) {
        String option;

        do {
            Printing.accountMenu();
            option = UserInput.readLine("");
            switch (option) {

                case "0":
                    startPage();
                    break;
                case "1":
                    deposit();
                    break;
                case "2":
                    withdraw();
                    break;
                case "3":
                    transfer();
                    break;
                case "4":
                    makePayment();
                    break;
                case "5":
                    printTransactionHistory();
                    break;
                case "6":
                    checkBalance();
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }

    /* PAY AND TRANSFER MENU */
    public void payTransferMenu(Customer currentUser) {
        String option;

        do {
            Printing.PayTransferMenu();
            option = UserInput.readLine("");
            switch (option) {

                case "0":
                    startPage();
                    break;
                case "1":
                    System.out.println("no feature yet:");
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

    /* LOAN MENU */
    public void loanMenu(Customer currentUser) {
        String option;

        do {
            Printing.loanMenu();
            option = UserInput.readLine("");
            switch (option) {

                case "0":
                    startPage();
                    break;
                case "1":
                    System.out.println("no feature yet:");
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

    /* KYC MENU */
    public void kycMenu(Customer currentUser) {
        String option;

        do {
            Printing.KYCMenu();
            option = UserInput.readLine("");
            switch (option) {

                case "0":
                    startPage();
                    break;
                case "1":
                    System.out.println("no feature yet:");
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

    /* EMPLOYEE MENU */
    public void employeeMenu() {
        String option;
        do {
            Printing.employeeMenu();
            option = UserInput.readLine("");
            switch (option) {

                case "0":
                    startPage();
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


    public void registerCustomer(){
        String personalNumber = UserInput.readLine("Customer personal number: ");
        String firstName = UserInput.readLine("Customer firstname: ");
        String lastName = UserInput.readLine("Customer lastname: ");
        String email = UserInput.readLine("Customer email: ");
        String telephone = UserInput.readLine("Customer telephone number: ");
        String password = UserInput.readLine("Customer password: ");
        String pinCode = UserInput.readLine("Customer pin code: ");
        String message = service.createCustomer(personalNumber, firstName, lastName, email, password, telephone, pinCode);
        System.out.println(message);
    }

    /*
    public void loginCustomer(){
        String verify = "";
        String personalNumber = UserInput.readLine("Customer personal number:");
        String password = UserInput.readLine("Customer password:");
        if (!service.isCustomerExist(personalNumber)){
            verify = " PersonalNumber number does not exist.";
        }
        if (!service.verifyCustomer(personalNumber, password)){
            verify = "Password does not match.";
        }else {
            customerMenu();
        }
        System.out.println(verify);
    }
*/
    public  String getAccountNumber(){
        return "";
    }

    public void deposit(){
        String accountNumber = UserInput.readLine("Enter account number: ");
        double amount = UserInput.readDouble("Enter amount to deposit: ");
        String message = service.depositMoney(accountNumber, amount);
        System.out.println(message);
    }

    public  void withdraw(){
        String accountNumber = UserInput.readLine("Enter account number: ");
        double amount = UserInput.readDouble("Enter amount to withdraw: ");
        String message = service.withdrawMoney(accountNumber, amount);
        System.out.println(message);
    }

    public void transfer(){
        String accountNumber1 = UserInput.readLine("Enter account number to transfer from: ");
        String accountNumber2 = UserInput.readLine("Enter account number to transfer to: ");
        double amount = UserInput.readDouble("Enter amount to transfer: ");
        String message = service.transferMoney(accountNumber1, accountNumber2, amount);
        System.out.println(message);
    }

    public void makePayment(){

    }

    public void printTransactionHistory(){

    }

    public void checkBalance(){
        String balance = service.checkBalance(getAccountNumber());
        System.out.println(" Account balance = " + balance);
    }




}