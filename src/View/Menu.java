package View;

import Utility.*;

import java.util.ArrayList;
import java.util.List;

import businessLogic.Inbox_Customer;
import businessLogic.Transactions.Deposit;
import businessLogic.Transactions.Transaction;
import businessLogic.Transactions.Withdrawal;
import businessLogic.User.Customer;
import businessLogic.bankAccounts.BankAccount;
import businessLogic.bankAccounts.CheckingAccount;
import businessLogic.bankAccounts.SavingsAccount;
import controller.Service;


public class Menu {
    public static final String EOL = System.lineSeparator();
    Service service = new Service();

    public void forTest() {
        service.createCustomer(
                "1234",
                "admin",
                "admin",
                "test@gmail.com",
                "1234",
                "1234",
                "1234"
        );
        Customer c = service.getCustomerByPN("1234");
        SavingsAccount sa = new SavingsAccount(c.getPersonalNumber());
        CheckingAccount ca = new CheckingAccount(c.getPersonalNumber());
        service.addAccount(sa);
        service.addAccount(ca);
        c.addBankAccount(sa);
        c.addBankAccount(ca);
        Withdrawal st1 = new Withdrawal(0.0, sa.getAccountNumber(), "123123", "", "Mom" );
        Withdrawal st2 = new Withdrawal(0.0, ca.getAccountNumber(), "567567", "", "Loan" );
        c.addRecipient(st1);
        c.addRecipient(st2);
        Deposit deposit1 = new Deposit(100, sa.getAccountNumber());
        Withdrawal withdrawal = new Withdrawal(30, sa.getAccountNumber(), ca.getAccountNumber(), "");
        Deposit deposit2 = new Deposit(30, ca.getAccountNumber());

        sa.addTransaction(deposit1);
        ca.addTransaction(deposit2);
        sa.addTransaction(withdrawal);
    }

    public void startPage() {
        // TODO REMOVE TEST METHOD
        forTest();
        String option;
        do {
            Printing.startPage();
            option = UserInput.readLine("Please type an option number: ");
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
            option = UserInput.readLine("Please type an option number: ");
            switch (option) {
                case "0":
                    startPage();
                    break;
                case "1":
                    payTransferMenu(currentUser);
                    break;
                case "2":
                    myAccounts(currentUser);
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
                    System.out.println("Customer Support - coming soon");
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }

    /* ACCOUNTS MENU */
    public void myAccounts(Customer currentUser) {
        String option;

        do {
            System.out.println(service.printAccountsAndBalance(currentUser));
            Printing.accountsMenu();
            option = UserInput.readLine("Please type an option number: ");
            switch (option) {

                case "0":
                    customerMenu(currentUser);
                    break;
                case "1":
                    System.out.println("Open Account - coming soon");
                    break;
                case "2":
                    System.out.println("Close Account - coming soon");
                    break;
                case "3":
                    payTransferMenu(currentUser);
                    break;
                case "4":
                    printTransactionHistory();
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
            Printing.payTransferMenu();
            option = UserInput.readLine("Please type an option number: ");
            switch (option) {

                case "0":
                    customerMenu(currentUser);
                    break;
                case "1":
                    System.out.println( EOL + "Please choose an account to deposit to or return to the menu: ");
                    String toAccount = chooseAccount(currentUser);
                    deposit(toAccount);
                    break;
                case "2":
                    System.out.println(EOL + "Please choose an account to transfer from or return to the menu: ");
                    String fromAccountNumber = chooseAccount(currentUser);
                    String toAccountNumber = service.chooseSecondAccount(currentUser, fromAccountNumber);
                    transfer(fromAccountNumber, toAccountNumber);
                    break;
                case "3":
                    System.out.println(EOL + "Please choose your account or return to the menu: ");
                    fromAccountNumber = chooseAccount(currentUser);
                    toAccountNumber = UserInput.readLine("Please enter account number for payment / transfer (6 digits): ");
                    double amount = UserInput.readDouble("Enter amount: ");
                    String note = UserInput.readLine("Enter note (optional): ");
                    System.out.println(service.payTransfer(fromAccountNumber, toAccountNumber, amount, note));
                    askToSaveRecipientMenu(currentUser,fromAccountNumber, toAccountNumber, note);
                    break;
                case "4":
                    System.out.println(service.printTransactionsAndRecipients(currentUser.getSavedRecipients()));
                    break;
                case "5":
                    transactionHistoryMenu(currentUser);
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }

    public void askToSaveRecipientMenu(Customer currentUser,String fromAccountNumber, String toAccountNumber, String note) {
        String option;

        do {
            option = UserInput.readLine("Would you like to save the recipient for future payments/transfers? " + EOL +
                    "Type 1 for Yes, 2 for No." + EOL);
            switch (option) {

                case "1":
                    String name = UserInput.readLine("Enter transaction/recipient name: ");
                    System.out.println(service.saveRecipient(currentUser, fromAccountNumber, toAccountNumber, note, name));
                    customerMenu(currentUser);
                    break;
                case "2":
                    customerMenu(currentUser);
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("2")));
        UserInput.exitScanner();
    }


    public String chooseAccount(Customer currentUser) {
        String option;
        List<BankAccount> accounts = currentUser.getBankAccounts();
        String operationResult = "";

        do {
            System.out.println(service.printAccounts(currentUser));
            option = UserInput.readLine("");
            switch (option) {

                //todo for Case 0 ->  if previous menu was payTransfer - return to that menu,
                // if Accounts, return to Accounts.
                case "0":
                    payTransferMenu(currentUser);
                    break;
                case "1":
                    for (BankAccount account : accounts) {
                        if (account instanceof CheckingAccount)
                            return account.getAccountNumber();
                    }
                    break;
                case "2":
                    for (BankAccount account : accounts) {
                        if (account instanceof SavingsAccount)
                            return account.getAccountNumber();
                    }
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
        return operationResult;
    }


    /* TRANSACTION HISTORY */
    public void transactionHistoryMenu(Customer currentUser) {
        String option;

        do {
            Printing.transactionHistoryMenu();
            option = UserInput.readLine("Please type an option number: ");
            switch (option) {

                case "0":
                    payTransferMenu(currentUser);
                    break;
                case "1":
                    String accountNumber = chooseAccount(currentUser);
                    BankAccount account = service.getAccountByAccountNumber(accountNumber);
                    System.out.println(service.printTransactionsAndRecipients(account.getTransactionList()));
                    break;
                case "2":
                    System.out.println("View deposits - coming soon");
                    break;
                case "3":
                    System.out.println("View withdrawals - coming soon");
                    break;
                case "4":
                    System.out.println("View total deposits for a period - coming in v2");
                    break;
                case "5":
                    System.out.println("View total withdrawals for a period - coming in v2");
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
            option = UserInput.readLine("Please type an option number: ");
            switch (option) {

                case "0":
                    startPage();
                    break;
                case "1":
                    viewLoan(currentUser);
                    break;
                case "2":
                    registerLoanApplication(currentUser);
                    break;
                case "3":
                    //registerIncreaseApplication(currentUser);
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
            option = UserInput.readLine("Please type an option number: ");
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
            option = UserInput.readLine("Please type an option number: ");
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
    public void viewLoan(Customer currentUser){
        String loan = service.viewLoan(currentUser.getPersonalNumber());
        System.out.println(" Current loan debt: " + loan);
    }

    public void registerLoanApplication(Customer currentUser){
        double monthlyIncome = UserInput.readDouble("What is your monthly salary?");
        double currentLoanDebt = UserInput.readDouble("What is the sum of your current loan debt?");
        double currentCreditDebt = UserInput.readDouble("What is the sum of your current credit debt?");
        int appliedLoanAmount = UserInput.readInt("How much would you want to borrow? From 0 - 500 000 SEK" + EOL);
        int appliedLoanDuration = UserInput.readInt("What duration would you like on the loan? From 1-5 years" + EOL);
        String message = service.applyLoan(currentUser.getPersonalNumber(), monthlyIncome, currentLoanDebt, currentCreditDebt,appliedLoanAmount, appliedLoanDuration);
        System.out.println(message);
    }
//Todo Anna - "works" but missing loanDebt -> from Loan Class, need to be approved by Employee (this is not done yet)
    /*
    public void registerIncreaseApplication (Customer currentUser){
        String debt = service.viewLoan(currentUser.getPersonalNumber());
        System.out.println ("Current loan debt: "+ debt + "SEK");
        double monthlyIncome = UserInput.readDouble("What is your monthly salary?" );
        double currentLoanDebt = UserInput.readDouble("What is the sum of your current loan debt?" );
        double currentCreditDebt = UserInput.readDouble("What is the sum of your current credit debt?" );
        int appliedLoanAmount = UserInput.readInt("How much would you want to borrow? From 0 - 500 000 SEK" );
        int appliedLoanDuration = UserInput.readInt("What duration would you like on the loan? From 1-5 years" );
        String message = service.increaseLoan(currentUser.getPersonalNumber(), monthlyIncome, currentLoanDebt, currentCreditDebt,appliedLoanAmount, appliedLoanDuration,loanDebt);
        System.out.println(message);
    }

 */
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

    public void deposit(String toAccount) {
        double amount = UserInput.readDouble("Enter amount to deposit: ");
        String message = service.deposit(toAccount, amount);
        System.out.println(message);
    }

    public void withdraw(String fromAccountNumber) {
        double amount = UserInput.readDouble("Enter amount: ");
        String message = service.withdraw(fromAccountNumber, amount);
        System.out.println(message);
    }

    public void transfer(String fromAccount, String toAccount) {
        double amount = UserInput.readDouble("Enter amount to transfer: ");
        String message = service.transferFundsBetweenAccounts(amount, fromAccount, toAccount);
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
    // todo Adrian - move business logic to Service class
    public Employee registerEmployee() {

            String employeeUsername = UserInput.readLine("Enter your username: ");
            String employeePassword = UserInput.readLine("Enter your password: ");
            if(service.employeeLoginCheck(employeeUsername, employeePassword)){ // If we want to add more employees change in this method

                return new Employee(employeeUsername, employeePassword);
            }
            return null;
    }
}