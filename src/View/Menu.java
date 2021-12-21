package View;

import Utility.*;

import java.io.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import businessLogic.Loan.Loan;
import businessLogic.Transactions.Transaction;
import businessLogic.User.Customer;
import businessLogic.User.KYC;
import businessLogic.bankAccounts.BankAccount;
import com.google.gson.Gson;
import controller.Service;


public class Menu {
    public static final String EOL = System.lineSeparator();
    Service service = new Service();
    Scanner input = new Scanner(System.in);

    public void startPage() throws Exception {

        String option;
       jsonFromCustomer();
       jsonFromLoan();
       jsonFromTransaction();
       jsonFromKYC();
       jsonFromAccounts();


        do {
            Printing.startPage();
            option = UserInput.readLine("Please type an option number: ");
            switch (option) {
                case "0":
                    System.out.println("Closing");
                    jsonToCustomer();
                    jsonToLoan();
                    jsonToTransaction();
                    jsonToKYC();
                    jsonToAccounts();
                    System.exit(0);
                    break;
                case "1":
                    registerCustomer();
                    break;
                case "2":
                    String personalNumber = UserInput.readLine("Please enter your personal number: ");
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
                    String username = UserInput.readLine("Input your username: ");
                    String pinCode = UserInput.readLine("Input your PIN-code: ");
                    if (service.verifyEmployee(username, pinCode)) {
                        employeeMenu();
                    } else {
                        startPage();
                    }
                }
                break;
                case "4":
                    System.out.println("no feature yet");
                    break;

                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (true);
    }

    /* ACCOUNTS MENU */
    public void customerMenu(Customer currentUser) throws Exception {
        String option;
        do {
            if (service.numberOfMessages(currentUser) > 0) {
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
    public void myAccounts(Customer currentUser) throws Exception {
        String option = "";

        do {
            if (!service.approvedKYC(currentUser)) {
                System.out.println("\u001B[32m" + "Please register KYC first to use all bank services!" + " \u001B[0m");
                kycMenu(currentUser);
            } else {
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
                        transactionHistoryMenu(currentUser);
                        break;
                    default:
                        Printing.invalidEntry();
                        break;
                }
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }

    /* PAY AND TRANSFER MENU */
    public void payTransferMenu(Customer currentUser) throws Exception {
        String option = null;
        do {
            if (!service.approvedKYC(currentUser)) {
                System.out.println("\u001B[32m" + "Please register KYC first to use all bank services!" + " \u001B[0m");
                kycMenu(currentUser);
            } else {
                Printing.payTransferMenu();
                option = UserInput.readLine("Please type an option number: ");
                switch (option) {

                    case "0":
                        customerMenu(currentUser);
                        break;
                    case "1":
                        System.out.println(EOL + "Please choose an account to deposit to or return to the menu: ");
                        String toAccount = chooseAccount(currentUser);
                        deposit(toAccount, currentUser);
                        break;
                    case "2":
                        System.out.println(EOL + "Please choose an account to transfer from or return to the menu: ");
                        String fromAccountNumber = chooseAccount(currentUser);
                        String toAccountNumber = service.chooseSecondAccount(currentUser, fromAccountNumber);
                        transferToOwnAccount(fromAccountNumber, toAccountNumber, currentUser);
                        break;
                    case "3":
                        System.out.println(EOL + "Please choose your account or return to the menu: ");
                        fromAccountNumber = chooseAccount(currentUser);
                        toAccountNumber = UserInput.readLine("Please enter account number for payment / transfer (6 characters): ");
                        transferToAnyAccount(fromAccountNumber, toAccountNumber, currentUser);
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
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }


    public void askToSaveRecipientMenu(Customer currentUser, String fromAccountNumber, String toAccountNumber, String note) throws Exception {
        String option;

        do {
            System.out.println("Would you like to save the recipient for future payments/transfers?");
            do {
                option = UserInput.readLineYesNo("Type Yes or No: ");
                switch (option) {
                    case "yes":
                        String name = UserInput.readLine("Enter transaction/recipient name: ");
                        System.out.println(service.saveRecipient(currentUser, fromAccountNumber, toAccountNumber, note, name));
                        payTransferMenu(currentUser);
                        break;
                    case "no":
                        System.out.println("Recipient saving declined.");
                        payTransferMenu(currentUser);
                        break;
                    default:
                        break;
                }
            } while (!option.equals("yes") || !option.equals("no"));
        } while (!(option.equals("no")));
        UserInput.exitScanner();
    }

    public String chooseAccount(Customer currentUser) throws Exception {
        String option = "";
        String operationResult = "";

        do {
            if (!service.approvedKYC(currentUser)) {
                System.out.println(" \"\\u001B[32m\" + Please register KYC first to use all bank services!" + " \u001B[0m");
                kycMenu(currentUser);
            } else {
                System.out.println(service.printAccounts(currentUser));
                option = UserInput.readLine("");
                switch (option) {

                    case "0":
                        payTransferMenu(currentUser);
                        break;
                    case "1":
                        return service.getCheckingAccountNumber(currentUser);
                    case "2":
                        return service.getSavingsAccountNumber(currentUser);
                    default:
                        Printing.invalidEntry();
                        break;
                }
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
        return operationResult;
    }

    /* TRANSACTION HISTORY */
    public void transactionHistoryMenu(Customer currentUser) throws Exception {
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
                    System.out.println(service.printAllTransactions(
                            service.getAccountByAccountNumber(accountNumber).getTransactionList()));
                    break;
                case "2":
                    accountNumber = chooseAccount(currentUser);
                    System.out.println(service.printAllDeposits(
                            service.getAccountByAccountNumber(accountNumber).getTransactionList()));
                    break;
                case "3":
                    accountNumber = chooseAccount(currentUser);
                    System.out.println(service.printAllWithdrawals(
                            service.getAccountByAccountNumber(accountNumber).getTransactionList()));
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
    public String loanMenu(Customer currentUser) throws Exception {
        String option = "";
        String operationResult = "";

        do {
            if (!service.approvedKYC(currentUser)) {
                System.out.println("Please register KYC first to use all bank services!");
                kycMenu(currentUser);
            } else {
            Printing.loanMenu();
            option = UserInput.readLine("Please type an option number: ");
            switch (option) {

                case "0":
                    startPage();
                    break;
                case "1":
                    myLoanMenu(currentUser);
                    break;
                case "2":
                    registerLoanApplication(currentUser);
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
            }
        } while (!(option.equals("0"))) ;
        UserInput.exitScanner();
        return operationResult;
    }


    public void myLoanMenu(Customer currentUser) throws Exception {
        String option;

        do {
            Printing.myLoanMenu();
            option = UserInput.readLine("Please type an option number: ");
            switch (option) {

                case "0":
                    loanMenu(currentUser);
                    break;
                case "1":
                    viewLoan(currentUser);
                    break;
                case "2":
                    System.out.println(payLoan(currentUser));
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }


    /* KYC MENU */
    public void kycMenu(Customer currentUser) throws Exception {
        String option;
        double salary = 0.0;
        do {
            Printing.KYCMenu();
            option = UserInput.readLine("Please type an option number: ");
            switch (option) {
                case "0":
                    customerMenu(currentUser);
                    break;
                case "1":
                    if (service.pendingKYC(currentUser)) {
                        System.out.println("KYC is pending review.");
                    } else if (service.approvedKYC(currentUser)) {
                        System.out.println("KYC has already been approved.");
                    } else {
                        String occupation = UserInput.readLine("What is your occupation? ");
                        salary = UserInput.readDouble("Please input your yearly salary before taxes: ");
                        String pepQuestion = UserInput.readLine("Are you a politically exposed customer? Input Yes or No: ");
                        String fatcaQuestion = UserInput.readLine("Do you pay taxes in the US? Input Yes or No: ");
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
    public void employeeMenu() throws Exception {
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
                case "4":
                    employeeCustomerSupportMenu();
                    break;
                case "5": {
                    String message = service.printAllCustomers();
                    System.out.println(message);
                    break;
                }
                case "6": {
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
    public void customerProfileMenu(Customer currentUser) throws Exception {
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
                    String personalNumber1 = UserInput.readLine("Please enter your personalNumber");
                    String telephoneNumber = UserInput.readLine("Please enter your new telephone number: ");
                    String message1 = service.editCustomerTelephone(personalNumber1, telephoneNumber);
                    System.out.println(message1);
                    break;
                case "3":
                    String personalNumber2 = UserInput.readLine("Please enter your personalNumber");
                    String email = UserInput.readLine("Please enter your new email: ");
                    String message2 = service.editCustomerEmail(personalNumber2, email);
                    System.out.println(message2);
                    break;
                case "4":
                    String personalNumber3 = UserInput.readLine("Please enter your personalNumber");
                    String password = UserInput.readLine("Please enter your new password: ");
                    String message3 = service.editCustomerPassword(personalNumber3, password);
                    System.out.println(message3);
                    break;
                case "5":
                    String personalNumber4 = UserInput.readLine("Please enter your personalNumber");
                    String pinCode = UserInput.readLine("Please enter your new PIN-code: ");
                    String message4 = service.editCustomerPincode(personalNumber4, pinCode);
                    System.out.println(message4);
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }

    public void employeeKYCMenu() throws Exception {
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
                    if (!service.emptyReviewList()) {
                        System.out.println(service.KYCToBeReviewed());
                        String review = UserInput.readLine("Do you want to approve this KYC? Input Yes or No: ");
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

    public void customerSupportMenu(Customer currentUser) throws Exception {
        String option;

        do {
            Printing.customerSupportMenu();
            option = UserInput.readLine("");
            switch (option) {
                case "0":
                    customerMenu(currentUser);  //Return to Customer Menu
                    break;
                case "1":
                    String message = UserInput.readLine("Message to customer support: ");
                    service.messageToEmployee(currentUser, message);
                    break;
                case "2":
                    System.out.println(service.viewMessage(currentUser));
                    String reply = UserInput.readLine("Would you like to reply? Yes or No.");
                    if (reply.equals("yes")) {
                        String replyMessage = UserInput.readLine("What would you like to reply?");
                        service.messageToEmployee(currentUser, replyMessage);
                        service.removeMessage(currentUser);
                    } else if (reply.trim().toLowerCase(Locale.ROOT).equals("no")) {
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

    public void employeeCustomerSupportMenu() throws Exception {
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
                    if (reply.equals("yes")) {
                        String replyMessage = UserInput.readLine("What would you like to reply?");
                        service.messageToCustomer(service.fetchPersonalNumber(), replyMessage);
                        service.removeMessage();
                    } else if (reply.trim().toLowerCase(Locale.ROOT).equals("no")) {
                        System.out.println("No reply has been sent.");
                        service.removeMessage();
                    } else {
                        System.out.println("Input yes or no.");
                    }
                    break;
                case "3":
                    System.out.println(service.numberOfMessages());
                    break;
                case "4":
                    break;
                default:
                    Printing.invalidEntry();
                    break;
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }

    //TODO Adrian lägga till så att + funkar i telefonumret och PIN-code 4siffror
    public void registerCustomer() throws Exception {
        try {
            String personalNumber = UserInput.readLine("Customer personal number: ");
            if (!service.onlyDigits(personalNumber) || (!personalNumber.matches("[1-9][0-9]{9}")) || service.containsCustomer(personalNumber)) {
                throw new Exception("10 digits only or customer already exists in the system");
            }
            String firstName = UserInput.readLine("Customer firstname: ");
            if (firstName.isEmpty() || firstName.isBlank() || service.onlyDigitsName(firstName)) {
                throw new Exception("Name cannot be blank or contain digits.");
            }
            String lastName = UserInput.readLine("Customer lastname: ");
            if (lastName.isEmpty() || lastName.isBlank() || service.onlyDigitsLastName(lastName)) {
                throw new Exception("Name cannot be blank or contain digits.");
            }
            String email = UserInput.readLine("Customer email: ");
            if (email.isBlank() || !email.contains("@")) {
                throw new Exception("Email must contain @.");
            }
            String telephone = UserInput.readLine("Customer telephone number: ");
            if (telephone.isBlank() || !service.onlyDigitsT(telephone)) {
                throw new Exception("Telephone must contain digits only.");
            }
            String password = UserInput.readLine("Customer password: ");
            if (password.isBlank() || password.isEmpty()) {
                throw new Exception("You must have a password.");
            }
            String pinCode = UserInput.readLine("Customer PIN-code: ");
            if (pinCode.isEmpty() || pinCode.isBlank() || !service.onlyDigitsP(pinCode) || pinCode.length() != 4) {
                throw new Exception("PIN-code must be digits and contain four numbers.");
            }
            String message = service.createCustomer(personalNumber, firstName, lastName, email, password, telephone, pinCode);
            System.out.println(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void viewLoan(Customer currentUser) {
        String loan = service.viewLoan(currentUser.getPersonalNumber());
        System.out.println(loan);
    }

    public void registerLoanApplication(Customer currentUser) throws Exception {
        if (service.checkLoan(currentUser.getPersonalNumber())) {
            System.out.println("You already have a loan with Eazy Banking.");
            loanMenu(currentUser);
        }

        String typedPinCode = askForPinCode();
        if (!service.checkPinCode(typedPinCode,currentUser)) {
            System.out.println("Incorrect PIN-code");
            loanMenu(currentUser);
            }
        try {
        double monthlyIncome = UserInput.readDouble("What is your monthly salary? ");
            if(monthlyIncome < 0){
                throw new Exception("Minimum income is 0,00 SEK. ");
            }
        double currentLoanDebt = UserInput.readDouble("Wht is the sum of your current loan debt? ");
            if(currentLoanDebt < 0 ){
                throw new Exception("Minimum value is 0,00 SEK. ");
            }
        double currentCreditDebt = UserInput.readDouble("What is the sum of your current credit debt? ");
            if(currentCreditDebt < 0 ){
                throw new Exception("Minimum value is 0,00 SEK. ");
            }
        int appliedLoanAmount = UserInput.readInt("How much would you want to borrow? From 0 - 500 000 SEK " + EOL);
            if(appliedLoanAmount < 0 || appliedLoanAmount > 500000 ){
                throw new Exception("Choose loan amount between 0 - 500 000 SEK.");
            }
        int appliedLoanDuration = UserInput.readInt("What duration would you like on the loan? From 1-5 years " + EOL);
            if(appliedLoanDuration < 1 || appliedLoanDuration > 5 ){
                throw new Exception("Choose between 1 - 5 years.");
            }
        service.applyLoan(currentUser.getPersonalNumber(), monthlyIncome, currentLoanDebt, currentCreditDebt,appliedLoanAmount, appliedLoanDuration);
        String message = service.autoApproval(currentUser);
        System.out.println(message);

        }catch (Exception exception){
        System.out.println(exception.getMessage());
        }
    }

    public String payLoan (Customer currentUser) throws Exception {
        if (!service.checkLoan(currentUser.getPersonalNumber())) {
            return "No loans yet.";
        } else {
        String reply = UserInput.readLine("Would you like to pay loan? Yes or No: ");
     if (reply.equals("yes")){
         String message = service.withdraw(service.getSavingsAccountNumber(currentUser),service.getMonthlyPayment(currentUser));
         System.out.println(message);
    } else if (reply.trim().toLowerCase(Locale.ROOT).equals("no")){
        System.out.println("Loan has not been paid, remember to pay the loan before end of month.");
        myLoanMenu(currentUser);
    } else {
        System.out.println("Input yes or no.");
    }
        return reply;
    }
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

    public void deposit(String toAccount, Customer currentUser) {
        try {
            double amount = UserInput.readDouble("Enter amount to deposit: ");
            String typedPinCode = "";
            typedPinCode = askForPinCode();
            if (service.checkPinCode(typedPinCode, currentUser)) {
                String message = service.deposit(toAccount, amount);
                System.out.println(message);
            } else {
                System.out.println("\u001B[31m" + "Incorrect PIN-code => Deposit rejected." + "\u001B[0m");
            }
        } catch (Exception e) {
            System.out.println("PIN-code must be 4 digits => Action is declined.");
        }
    }

    public void withdraw(String fromAccountNumber, Customer currentUser) {
        try {
            double amount = UserInput.readDouble("Enter amount: ");
            String typedPinCode = "";
            typedPinCode = askForPinCode();
            if (service.checkPinCode(typedPinCode, currentUser)) {
                String message = service.withdraw(fromAccountNumber, amount);
                System.out.println(message);
            } else {
                System.out.println("\u001B[31m" + "Incorrect PIN-code => Withdrawal rejected." + "\u001B[0m");
            }
        } catch (Exception e) {
            System.out.println("PIN-code must be 4 digits => Action is declined.");
        }

    }

    public void transferToOwnAccount(String fromAccount, String toAccount, Customer currentUser) {
        try {
            double amount = UserInput.readDouble("Enter amount to transfer: ");
            String typedPinCode = askForPinCode();
            if (service.checkPinCode(typedPinCode, currentUser)) {
                String message = service.transferFundsBetweenAccounts(amount, fromAccount, toAccount);
                System.out.println(message);
            } else {
                System.out.println("\u001B[31m" + "Incorrect PIN-code => Transfer rejected." + "\u001B[0m");
            }
        } catch (Exception e) {
            System.out.println("PIN-code must be 4 digits => Action is declined.");
        }
    }

    public void transferToAnyAccount(String fromAccount, String toAccount, Customer currentUser) {
        try {
            if (toAccount.length() == 6) {
                double amount = UserInput.readDouble("Enter amount: ");
                String note = UserInput.readLine("Enter note (optional): ");
                String typedPinCode = askForPinCode();
                if (service.checkPinCode(typedPinCode, currentUser)) {
                    String result = service.payTransfer(fromAccount, toAccount, amount, note);
                    System.out.println(result);
                    if (result.contains("successful")) {
                        askToSaveRecipientMenu(currentUser, fromAccount, toAccount, note);
                    }
                } else {
                    System.out.println("\u001B[31m" + "Incorrect PIN-code => Transfer rejected." + "\u001B[0m");
                }
            } else {
                System.out.println("Account number must ne 6 characters");
            }
        } catch (Exception e) {
            System.out.println("PIN-code must be 4 digits => Action is declined.");
        }
    }

    public String askForPinCode() throws Exception {
        String typedPinCode = UserInput.readLine("Enter PIN-code to confirm: ");
        if (typedPinCode.isEmpty() || typedPinCode.isBlank() || typedPinCode.length() != 4) {
            throw new Exception("PIN-code must be 4 digits. Action is declined.");
        } else {
            return typedPinCode;
        }
    }

    public void jsonFromCustomer() throws FileNotFoundException {
        Gson gson = new Gson();

        Customer[] customerList = gson.fromJson(new FileReader("dit094_miniproject_group_3" + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
                "controller" + System.getProperty("file.separator") + "Customer.json"), Customer[].class);
        for (Customer customer : customerList) {
            service.getCustomerList().add(customer);
        }
    }
    public void jsonToCustomer() {
        Gson gson = new Gson();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("dit094_miniproject_group_3" + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
                    "controller" + System.getProperty("file.separator") + "Customer.json"));
            writer.write(gson.toJson(service.getCustomerList()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void jsonFromLoan() throws FileNotFoundException {
        Gson gson = new Gson();
        Loan[] loanList = gson.fromJson(new FileReader("dit094_miniproject_group_3" + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
                "controller" + System.getProperty("file.separator") + "Loan.json"), Loan[].class);
        for (Loan loan : loanList) {
            service.getLoanList().add(loan);
        }
    }
    public void jsonToLoan(){
        Gson gson = new Gson();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("dit094_miniproject_group_3" + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
                    "controller" + System.getProperty("file.separator") + "Loan.json"));
            writer.write(gson.toJson(service.getLoanList()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void jsonFromTransaction() throws FileNotFoundException {
        Gson gson = new Gson();
        Transaction[] transactions = gson.fromJson(new FileReader("dit094_miniproject_group_3" + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
                "controller" + System.getProperty("file.separator") + "Transactions.json"), Transaction[].class);
        for (Transaction transaction : transactions)
            service.getTransactions().add(transaction);
    }

    public void jsonToTransaction(){
        Gson gson = new Gson();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("dit094_miniproject_group_3" + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
                    "controller" + System.getProperty("file.separator") + "Transactions.json"));
            writer.write(gson.toJson(service.getTransactions()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void jsonFromKYC() throws FileNotFoundException {
        Gson gson = new Gson();

        KYC[] approvedKYCList = gson.fromJson(new FileReader("dit094_miniproject_group_3" + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
                "controller" + System.getProperty("file.separator") + "KYC.json"), KYC[].class);
        for (KYC kyc : approvedKYCList) {
            service.getApprovedKYCList().add(kyc);
        }
    }
    public void jsonToKYC(){
        Gson gson = new Gson();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("dit094_miniproject_group_3" + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
                    "controller" + System.getProperty("file.separator") + "KYC.json"));
            writer.write(gson.toJson(service.getApprovedKYCList()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void jsonFromAccounts() throws FileNotFoundException {
        Gson gson = new Gson();

        BankAccount[] accountsList = gson.fromJson(new FileReader("dit094_miniproject_group_3" + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
                "controller" + System.getProperty("file.separator") + "BankAccounts.json"), BankAccount[].class);
        for (BankAccount account : accountsList) {
            service.getAccountsList().add(account);
        }
    }
    public void jsonToAccounts(){
        Gson gson = new Gson();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("dit094_miniproject_group_3" + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") +
                    "controller" + System.getProperty("file.separator") + "BankAccounts.json"));
            writer.write(gson.toJson(service.getAccountsList()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}