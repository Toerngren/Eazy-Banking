package View;
import Utility.*;
import businessLogic.User.Customer;
import businessLogic.User.Employee;
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
                    if (service.onlyDigits(personalNumber)) {
                        if (!service.containsCustomer(personalNumber)) {
                            System.out.println("No customer with that personal number.");
                            startPage();
                        }
                        String password = UserInput.readLine("Please enter your password: ");
                        Customer foundCustomer = service.findCustomer(personalNumber);
                        if (foundCustomer.verifyPassword(password)) {
                            customerMenu(foundCustomer);
                        } else {
                            System.out.println("Wrong password.");
                        }
                    }
                    System.out.println("Personal number needs to only contain digits.");
                    break;
                case "3": {
                    Employee employee = registerEmployee();
                    if(employee == null){
                        System.out.println("Invalid username or password.");
                        break;
                    }
                    employeeMenu();
                }
                break;
                case"4":
                    System.out.println("no feature yet");
                    break;

                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (true);
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
                    customerProfileMenu(currentUser);
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
                    boolean pep = false;
                    boolean fatca = false;
                    String occupation = UserInput.readLine("What is your occupation?");
                    double salary = UserInput.readDouble("Please input your yearly salary before taxes:");
                    String pepQuestion = UserInput.readLine("Are you a politically exposed customer? Type 1 for yes and 2 for no.");
                    if (pepQuestion.equals("1")){
                        pep = true;
                    } else if (pepQuestion.equals("2")){
                        pep = false;
                    } else {
                        System.out.println("Choose either 1 or 2.");
                    }
                    String fatcaQuestion = UserInput.readLine("Do you pay taxes in the US? Type 1 for yes and 2 for no.");
                    if (fatcaQuestion.equals("1")){
                        fatca = true;
                    } else if (fatcaQuestion.equals("2")){
                        fatca = false;
                    } else {
                        System.out.println("Choose either 1 or 2.");
                    }
                    System.out.println(service.registerKYC(currentUser, occupation, salary, pep, fatca));

                    break;
                case "2":
                    System.out.println(service.viewKYC(currentUser));
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
                case "3":
                    employeeKYCMenu();
                    break;
                case"4":
                    System.out.println(service.printAllApprovedKYCs());
                    break;
                case "5": {
                    String message = service.printAllCustomers();
                    System.out.println(message);
                    break;
                }
                case"6": {
                    String delete = UserInput.readLine("Enter personalnumber of customer you wish to remove: ");
                    String message = service.deleteCustomer(delete);
                    System.out.println(message);
                    break;
                }
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }

    // Todo @Christoph / Adrian, add exceptions so that email must contain @ and so on.
    public void customerProfileMenu(Customer currentUser) {
        String option;
        do {
            Printing.customerProfileMenu();
            option = UserInput.readLine("");
            switch (option) {
                case "0":
                    customerMenu(currentUser);
                    break;
                case "1":
                    System.out.println(currentUser.toString());
                    break;
                case "2":
                    String telephoneNumber = UserInput.readLine("Please enter your new telephone number:");
                    service.editCustomerTelephone(currentUser.getPersonalNumber(), telephoneNumber);
                    System.out.println("Telephone number successfully updated!");
                    break;
                case "3":
                    String email = UserInput.readLine("Please enter your new email:");
                    service.editCustomerEmail(currentUser.getPersonalNumber(), email);
                    System.out.println("E-mail successfully updated!");
                    break;
                case "4":
                    String password = UserInput.readLine("Please enter your new password:");
                    service.editCustomerPassword(currentUser.getPassword(), password);
                    System.out.println("Password successfully changed.");
                    break;
                case "5":
                    String pinCode = UserInput.readLine("Please enter your new PIN-code:");
                    service.editCustomerPincode(currentUser.getPinCode(), pinCode);
                    System.out.println("PIN-code successfully changed.");
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }

    public void employeeKYCMenu() {
        String option;
        do {
            Printing.employeeKYCMenu();;
            option = UserInput.readLine("");
            switch (option) {
                case "0":
                    employeeMenu();
                    break;
                case "1":
                    System.out.println(service.showUnapprovedKYC());
                    if (service.showUnapprovedKYC().equals("No KYC registered for this customer.")){
                        employeeMenu();
                    } else {
                        String review = UserInput.readLine("Do you want to approve this KYC? 1 for yes 2 for no.");
                        System.out.println(service.reviewUnapprovedKYC(review));
                    }
                    break;
                case "2":
                    System.out.println(service.numberOfUnapprovedKYCs());
                    break;
                case "3":
                    System.out.println(service.numberOfApprovedKYCs());
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }



    public void registerCustomer() {
        String personalNumber = UserInput.readLine("Customer personal number: ");
        if (!service.onlyDigits(personalNumber)) {
            System.out.println("Please only enter digits.");
            startPage();
        }
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
    public String getAccountNumber() {
        return "";
    }

    public void deposit() {
        String accountNumber = UserInput.readLine("Enter account number: ");
        double amount = UserInput.readDouble("Enter amount to deposit: ");
        String message = service.deposit(accountNumber, amount);
        System.out.println(message);
    }

    public void withdraw() {
        String accountNumber = UserInput.readLine("Enter account number: ");
        double amount = UserInput.readDouble("Enter amount to withdraw: ");
        String message = service.withdraw(accountNumber, amount);
        System.out.println(message);
    }

    public void transfer() {
        String accountNumber1 = UserInput.readLine("Enter account number to transfer from: ");
        String accountNumber2 = UserInput.readLine("Enter account number to transfer to: ");
        double amount = UserInput.readDouble("Enter amount to transfer: ");
        String message = service.transferFunds(amount, accountNumber1, accountNumber2);
        System.out.println(message);
    }

    public void makePayment() {

    }

    public void printTransactionHistory() {

    }

    public void checkBalance() {
        String balance = service.checkBalance("123456");
        System.out.println(" Account balance = " + balance);

    }

    public Employee registerEmployee() {

            String employeeUsername = UserInput.readLine("Enter your username: ");
            String employeePassword = UserInput.readLine("Enter your password: ");
            if(service.employeeLoginCheck(employeeUsername, employeePassword)){ // If we want to add more employees change in this method

                return new Employee(employeeUsername, employeePassword);
            }
            return null;
    }
}