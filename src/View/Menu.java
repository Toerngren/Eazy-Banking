package View;

import Utility.*;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import businessLogic.Transactions.Deposit;
import businessLogic.Transactions.Withdrawal;
import businessLogic.User.Customer;
import businessLogic.User.Employee;
import businessLogic.bankAccounts.BankAccount;
import businessLogic.bankAccounts.CheckingAccount;
import businessLogic.bankAccounts.SavingsAccount;
import controller.Service;


public class Menu {
    public static final String EOL = System.lineSeparator();
    Service service = new Service();
    Scanner input = new Scanner(System.in);
/*
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
 */

    public void startPage() {
        // TODO REMOVE TEST METHOD
        //forTest();
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
                    String username = UserInput.readLine("Input your username:");
                    String pinCode = UserInput.readLine("Input your PIN-code:");
                    if (service.verifyEmployee(username, pinCode)){
                        employeeMenu();
                    } else {
                        startPage();
                    }
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
            if (service.numberOfMessages(currentUser) > 0){
                System.out.println(System.lineSeparator() + "\u001B[32m" + "You have a new message!" + "\u001B[0m" + System.lineSeparator());
            }
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
                    customerSupportMenu(currentUser);
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
                /*case "1":
                    System.out.println("Open Account - coming soon");
                    break;
                case "2":
                    System.out.println("Close Account - coming soon");
                    break;

                 */
                case "1":
                    payTransferMenu(currentUser);
                    break;
                case "2":
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
                    String result = service.payTransfer(fromAccountNumber, toAccountNumber, amount, note);
                    System.out.println(result);
                    if (result.contains("successful")) {
                        askToSaveRecipientMenu(currentUser, fromAccountNumber, toAccountNumber, note);
                    }
                    break;
                case "4":
                    System.out.println(service.printAllRecipients(currentUser.getSavedRecipients()));
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
            //todo Margaret add handle user input
            option = UserInput.readLine("Would you like to save the recipient for future payments/transfers? " + EOL +
                    "Type 1 for Yes, 2 for No." + EOL);
            switch (option) {

                case "1":
                    //todo Margaret add handle user input
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
                    System.out.println(service.printAllTransactions(account.getTransactionList()));
                    break;
                case "2":
                    accountNumber = chooseAccount(currentUser);
                    account = service.getAccountByAccountNumber(accountNumber);
                    System.out.println(service.printAllDeposits(account.getTransactionList()));
                    break;
                case "3":
                    accountNumber = chooseAccount(currentUser);
                    account = service.getAccountByAccountNumber(accountNumber);
                    System.out.println(service.printAllWithdrawals(account.getTransactionList()));
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
                    customerMenu(currentUser);
                    break;
                case "1":
                    if (service.pendingKYC(currentUser)){
                        System.out.println("KYC is pending review.");
                    } else if (service.approvedKYC(currentUser)) {
                        System.out.println("KYC has already been approved.");
                    } else {
                        String occupation = UserInput.readLine("What is your occupation?");
                        System.out.println("Please input your yearly salary before taxes:");
                        while (!input.hasNextDouble()) {
                            input.nextLine();
                            System.out.println("Please only use digits.");
                        }
                        double salary = input.nextDouble();
                        String pepQuestion = UserInput.readLine("Are you a politically exposed customer? Type 1 for yes and 2 for no.");
                        String fatcaQuestion = UserInput.readLine("Do you pay taxes in the US? Type 1 for yes and 2 for no.");
                        System.out.println(service.registerKYC(currentUser, occupation, salary, pepQuestion, fatcaQuestion));
                    }
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
                    employeeCustomerSupportMenu();
                    break;
                case "5": {
                    String message = service.printAllCustomers();
                    System.out.println(message);
                    break;
                }
                case"6": {
                    String delete = UserInput.readLine("Enter personal number of customer you wish to remove: ");
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
            System.out.println(service.numberOfUnapprovedKYCs());
            Printing.employeeKYCMenu();
            option = UserInput.readLine("");
            switch (option) {
                case "0":
                    employeeMenu();
                    break;
                case "1":
                    if(!service.emptyReviewList()) {
                        System.out.println(service.KYCToBeReviewed());
                        String review = UserInput.readLine("Do you want to approve this KYC? 1 for yes 2 for no.");
                        System.out.println(service.reviewUnapprovedKYC(review));
                    } else {
                        System.out.println("There are currently no KYC's to review.");
                    }
                    break;
                case "2":
                    System.out.println(service.numberOfApprovedKYCs());
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }

    public void customerSupportMenu(Customer currentUser) {
        String option;

        do {
            Printing.customerSupportMenu();
            option = UserInput.readLine("");
            switch (option) {
                case "0":
                    customerMenu(currentUser);  //Return to Customer Menu
                    break;
                case "1":
                    String message = UserInput.readLine("Message to customer support:");
                    service.messageToEmployee(currentUser, message);
                    break;
                case "2":
                    System.out.println(service.viewMessage(currentUser));
                    String reply = UserInput.readLine("Would you like to reply? Yes or No.");
                    if (reply.equals("yes")){
                        String replyMessage = UserInput.readLine("What would you like to reply?");
                        service.messageToEmployee(currentUser, replyMessage);
                        service.removeMessage(currentUser);
                    } else if (reply.trim().toLowerCase(Locale.ROOT).equals("no")){
                        System.out.println("No reply has been sent.");
                        service.removeMessage(currentUser);
                    } else {
                        System.out.println("Input yes or no.");
                    }
                    break;
                case "3":
                    System.out.println(service.numberOfMessages(currentUser));
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }

    public void employeeCustomerSupportMenu() {
        String option;
        do {
            Printing.employeeSupportMenu();
            option = UserInput.readLine("");
            switch (option) {
                case "0":
                    employeeMenu();
                    break;
                case "1":
                    String personalNumber = UserInput.readLine("What customer would you like to write to? Input personal number:");
                    String message = UserInput.readLine("What message would you like to send?");
                    System.out.println(service.messageToCustomer(personalNumber, message));
                    break;
                case "2":
                    System.out.println(service.viewMessage());
                    String reply = UserInput.readLine("Would you like to reply? Yes or No.");
                    if (reply.equals("yes")){
                        String replyMessage = UserInput.readLine("What would you like to reply?");
                        service.messageToCustomer(service.fetchPersonalNumber(), replyMessage);
                        service.removeMessage();
                    } else if (reply.trim().toLowerCase(Locale.ROOT).equals("no")){
                        System.out.println("No reply has been sent.");
                        service.removeMessage();
                    } else {
                        System.out.println("Input yes or no.");
                    }
                    break;
                case "3":
                    System.out.println(service.numberOfMessages());
                    break;
                case"4":
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
        Double balance = service.checkBalance("123456");
        System.out.println(" Account balance = " + balance);
    }
}