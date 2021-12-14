package controller;

import businessLogic.Loan.IncreaseLoan;
import businessLogic.Loan.Loan;
import businessLogic.Loan.LoanApplication;
import businessLogic.Transactions.Deposit;
import businessLogic.Transactions.Transaction;
import businessLogic.Transactions.Withdrawal;
import businessLogic.User.Employee;
import businessLogic.User.KYC;
import businessLogic.User.Customer;
import businessLogic.bankAccounts.BankAccount;
import businessLogic.bankAccounts.CheckingAccount;
import businessLogic.bankAccounts.SavingsAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Utility.Utilities;

public class Service { // This is like our facade. Where we place all our business logic

    public static final String EOL = System.lineSeparator();

    private List<Customer> customerList;
    private List<BankAccount> accountsList;
    private List<KYC> reviewKYCList;
    private List<Transaction> transactions;
    private List<Transaction> savedRecipients;
    private List<KYC> approvedKYCList;
    private List<Loan> loanList;
    private List<LoanApplication> loanApplicationList;
    private Employee employee;
    // private Account loggedInAccount;

    public Service() {
        customerList = new ArrayList<>();
        accountsList = new ArrayList<>();
        reviewKYCList = new ArrayList<>();
        transactions = new ArrayList<>();
        savedRecipients = new ArrayList<>();
        approvedKYCList = new ArrayList<>();
        loanList = new ArrayList<>();
        loanApplicationList = new ArrayList<>();
        this.employee = new Employee("admin", "admin");
    }

    public String createCustomer(String personalNumber, String firstName, String lastName, String email,
                                 String telephone, String password, String pinCode) {
        Customer customer = new Customer(personalNumber, firstName, lastName, email, telephone, password, pinCode);
        customerList.add(customer);
        return System.lineSeparator() + "You have now been registered!" + System.lineSeparator();
    }

    public void createKYC(String personalNumber, String occupation, double salary, boolean pep, boolean fatca, boolean approved) {
        KYC kyc = new KYC(personalNumber, occupation, salary, pep, fatca, approved);
        reviewKYCList.add(kyc);
    }


    //todo Adrian
    public String verifyCustomerID(String personalNumber, String password) {
        return "";
    }

    public int getCustomerIndex(String personalNumber) {
        for (int i = 0; i < this.accountsList.size(); i++) {
            if (this.accountsList.get(i).verifyAccountNumber(personalNumber)) {
                return i;
            }
        }
        return -1;
    }

    public KYC findKYC(Customer customer) {
        if (reviewKYCList.size() > 0) {
            for (KYC kyc : reviewKYCList) {
                if (customer.getPersonalNumber().equals(kyc.getPersonalNumber())) {
                    return kyc;
                }
            }
        }
        if (approvedKYCList.size() > 0) {
            for (KYC kyc : approvedKYCList) {
                if (customer.getPersonalNumber().equals(kyc.getPersonalNumber())) {
                    return kyc;
                }

            }
        }
        return null;
    }

    public boolean pendingKYC(Customer customer){
        for (KYC kyc: reviewKYCList){
            if(customer.getPersonalNumber().equals(kyc.getPersonalNumber())){
                return true;
            }
        } return false;
    }

    public String customerDisplayKYC(KYC kyc){
        String pepStatus = "";
        String fatcaStatus = "";
        if (kyc.isPep()){
            pepStatus = "Yes";
        } else {
            pepStatus = "No";
        }
        if (kyc.isFatca()){
            fatcaStatus = "Yes";
        } else {
            fatcaStatus = "No";
        }

        String result =
                "Occupation: " + kyc.getOccupation() + System.lineSeparator() +
                        "Salary: " + kyc.getSalary() + System.lineSeparator() +
                        "Politically exposed customer: " + pepStatus + System.lineSeparator() +
                        "Affected by FATCA: " + fatcaStatus + System.lineSeparator();
        return result;
    }



    public String viewKYC (Customer customer) {
        if (findKYC(customer) == null) {
            return "No KYC registered yet.";
        } else if (approvedKYC(customer)){
            KYC customersKYC = findKYC(customer);
            return "Status: Approved." + EOL + customerDisplayKYC(customersKYC);
        } else if (pendingKYC(customer)){
            KYC customersKYC = findKYC(customer);
            return "Status: Under review. " + EOL + customerDisplayKYC(customersKYC);
        } return "";
    }


    public boolean emptyReviewList(){
        return reviewKYCList.isEmpty();
    }

    public String KYCToBeReviewed(){
        if (reviewKYCList.isEmpty()){
            return "There are currently no KYC's to review.";
        }
        KYC unapprovedKYC = findUnapprovedKYC();
        if (unapprovedKYC == null){
            return "No KYCs to review";
        }
        return employeeDisplayKYC(unapprovedKYC);
    }

    public String employeeDisplayKYC(KYC kyc){
        String pepStatus = "";
        String fatcaStatus = "";
        if (kyc.isPep()){
            pepStatus = "Yes";
        } else {
            pepStatus = "No";
        }
        if (kyc.isFatca()){
            fatcaStatus = "Yes";
        } else {
            fatcaStatus = "No";
        }
        String result = "Personalnumber: " + kyc.getPersonalNumber() + System.lineSeparator() +
                "Occupation: " + kyc.getOccupation() + System.lineSeparator() +
                "Salary: " + kyc.getSalary() + System.lineSeparator() +
                "Politically exposed customer: " + pepStatus + System.lineSeparator() +
                "Affected by FATCA: " + fatcaStatus + System.lineSeparator();
        return result;
    }

    public String registerKYC (Customer customer, String occupation, double salary, String pepQuestion, String fatcaQuestion) {
        boolean pep = false;
        boolean fatca = false;
        if (pendingKYC(customer)){
            return "KYC has already been filled in.";
        }

        if (salary < 0) {
            return "Salary cannot be lower than zero. Please try again.";
        }
        if (occupation.isBlank()) {
            return "You need to fill in your occupation. Please try again.";
        }
        if (pepQuestion.trim().toLowerCase(Locale.ROOT).equals("yes")) {
            pep = true;
        } else if (pepQuestion.trim().toLowerCase(Locale.ROOT).equals("no")) {
            pep = false;
        } else {
            return "Please write either Yes or No";
        }
        if (fatcaQuestion.trim().toLowerCase(Locale.ROOT).equals("yes")){
            fatca = true;
        } else if (fatcaQuestion.trim().toLowerCase(Locale.ROOT).equals("no")){
            fatca = false;
        } else {
            return "Please write either Yes or No";
        }
        KYC kyc = new KYC(customer.getPersonalNumber(), occupation, salary, pep, fatca, false);
        reviewKYCList.add(kyc);
        return System.lineSeparator() + "KYC awaiting review." + System.lineSeparator();
    }

    public boolean approvedKYC(Customer customer) {
        for (KYC approvedKYC : approvedKYCList) {
            if (customer.getPersonalNumber().equals(approvedKYC.getPersonalNumber())) {
                return true;
            }
        }
        return false;
    }

    public boolean onlyDigits(String personalNumber) {
        for (int i = 0; i < personalNumber.length(); i++) {
            if (!Character.isDigit(personalNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    public String reviewUnapprovedKYC(String review) {
        KYC unapprovedKYC = findUnapprovedKYC();
        String result = "";
        if (review.equals("1")) {
            approvedKYCList.add(unapprovedKYC);
            OpenAccounts(unapprovedKYC.getPersonalNumber());
            reviewKYCList.remove(unapprovedKYC);
            result = "Customers KYC has been approved.";
        } else if (review.equals("2")) {
            reviewKYCList.remove(unapprovedKYC);
            result = "Customers KYC has been declined.";
        } else {
            result = "Please input either 1 or 2";
        }
        return result + System.lineSeparator();
    }

    public void OpenAccounts(String customerPersonalNumber){
        Customer currentUser = findCustomer(customerPersonalNumber);
        CheckingAccount cH = new CheckingAccount(customerPersonalNumber);
        SavingsAccount sA = new SavingsAccount(customerPersonalNumber);
        currentUser.addBankAccount(cH);
        currentUser.addBankAccount(sA);
        accountsList.add(cH);
        accountsList.add(sA);
    }



    public String showUnapprovedKYC() {
        KYC unapprovedKYC = findUnapprovedKYC();
        if (unapprovedKYC != null) {
            return employeeDisplayKYC(unapprovedKYC);
        } else {
            return "No KYC registered for this customer." + System.lineSeparator();
        }
    }

    public String numberOfApprovedKYCs() {
        String result = "";
        if (approvedKYCList.isEmpty()) {
            result = "There are currently no approved KYCs." + System.lineSeparator();
        } else {
            result = "There are " + approvedKYCList.size() + " approved reviews." + System.lineSeparator();
        }
        return result + System.lineSeparator();
    }

    public String numberOfUnapprovedKYCs() {
        String result = "";
        if (reviewKYCList.isEmpty()) {
            result = ""; // Returns an empty string as option one displays information if there are no KYCs to review
        } else {
            result = "The number of unapproved KYC's is: " + reviewKYCList.size() + System.lineSeparator();
        }
        return result;
    }


    public String printAllApprovedKYCs() {
        String allApprovedKYCs = "All approved KYCs:";
        for (KYC approvedKYC : approvedKYCList) {
            allApprovedKYCs = allApprovedKYCs + System.lineSeparator() + approvedKYC.toString();
        }
        return allApprovedKYCs;
    }

    public KYC findUnapprovedKYC (){
        for (KYC kyc : reviewKYCList){
            return kyc;
        } return null;
    }

    public boolean isCustomerExist(String personalNumber) {
        return getCustomerIndex(personalNumber) != -1;
    }

    public boolean containsCustomer(String personalNumber) {
        for (Customer customer : customerList) {
            if (customer.getPersonalNumber().equals(personalNumber)) {
                return true;
            }
        }
        return false;
    }


    public boolean verifyCustomer(String personalNumber, String password) {
        int index = getCustomerIndex(personalNumber);
        return !this.customerList.get(index).verifyCustomer(password);
    }

    //todo Adrian
    /*
    public String deleteCustomer(String personalNumber) {

      if(customerList.isEmpty()){ Adrians version of verifyCustomer
            return "No customers created yet.";
        }
        for (Customer customer : customerList) {
            if(!customer.getPersonalNumber().equals(personalNumber) && !customer.getPassword().equals(password)){
                return "Wrong personal number or password.";
            }
        }

        return "Verified customer.";
    }
  */
/*
    public String editCustomerDetail(String firstName, String lastName, String email,
                                     String telephone, String password, String pinCode) {
        // Done but 6 methods in total. see below
        return "";
    }

    */

    public String printAllCustomers() {
        return "All registered customers:";
    }

    /*
        public String printAllCustomers() {
            String allCustomers = "All registered customers:";

            for (Customer customer : customerList) {
                allCustomers = allCustomers + System.lineSeparator() + customer.toString();
            }
            return allCustomers + System.lineSeparator();
        }
    */
    public String editCustomerFirstName(String personalNumber, String newFirstName) {

        Customer nameToChange = null;
        for (Customer currentName : customerList) {
            if (currentName.getPersonalNumber().equals(personalNumber)) {
                if (newFirstName.isEmpty()) {
                    return "Invalid entry.";
                }
                nameToChange = currentName;
                currentName.setFirstName(newFirstName);
            }
        }
        if (nameToChange == null) {
            return personalNumber + " was not registered yet.";
        }
        return personalNumber + "'s " + " first name was edited successfully.";
    }

    public String editCustomerLastName(String personalNumber, String newLastName) {

        Customer lastNameToChange = null;

        for (Customer currentLastName : customerList) {
            if (currentLastName.getPersonalNumber().equals(personalNumber)) {
                if (newLastName.isEmpty() || newLastName.isBlank()) {
                    return "Invalid entry";
                }
                lastNameToChange = currentLastName;
                currentLastName.setLastName(newLastName);
            }
        }
        if (lastNameToChange == null) {
            return personalNumber + " was not registered yet.";
        }
        return personalNumber + "'s last name was edited successfully.";
    }

    public String editCustomerEmail(String personalNumber, String newEmail) {

        Customer emailToChange = null;
        for (Customer currentEmail : customerList) {
            if (currentEmail.getPersonalNumber().equals(personalNumber)) {
                if (newEmail.isEmpty() || newEmail.isBlank()) {
                    return "Invalid entry.";
                }
                emailToChange = currentEmail;
                currentEmail.setEmail(newEmail);
            }
            if (emailToChange == null) {

                return personalNumber + " was not registered yet.";
            }
        }
        return personalNumber + "'s" + " email was edited successfully.";
    }

    public String editCustomerTelephone(String personalNumber, String newTelephone) {

        Customer telephoneToChange = null;
        for (Customer currentPhone : customerList) {
            if (currentPhone.getPersonalNumber().equals(personalNumber)) {
                if (newTelephone.isEmpty() || newTelephone.isBlank()) {
                    return "Invalid entry.";
                }
                telephoneToChange = currentPhone;
                currentPhone.setTelephone(newTelephone);
            }
            if (telephoneToChange == null) {

                return personalNumber + " was not registered yet.";
            }
        }
        return personalNumber + "'s" + " telephone number was edited successfully.";
    }

    public boolean employeeLoginCheck(String username, String password) {

        return username.equals("admin") && password.equals("admin");
    }

    public String editCustomerPassword(String personalNumber, String newPassword) {
        Customer passwordToChange = null;
        for (Customer currentPW : customerList) {
            if (currentPW.getPersonalNumber().equals(personalNumber)) {
                if (newPassword.isEmpty() || newPassword.isBlank()) {
                    return "Invalid entry.";
                }
                passwordToChange = currentPW;
                currentPW.setPassword(newPassword);
            }
            if (passwordToChange == null) {

                return personalNumber + " was not registered yet.";
            }
        }
        return personalNumber + "'s" + " password was edited successfully.";
    }

    public String editCustomerPincode(String personalNumber, String newPincode) {
        Customer pinCodeToChange = null;
        for (Customer currentPinCode : customerList) {
            if (currentPinCode.getPersonalNumber().equals(personalNumber)) {
                if (newPincode.isEmpty() || newPincode.isBlank()) {
                    return "Invalid entry.";
                }
                pinCodeToChange = currentPinCode;
                currentPinCode.setPinCode(newPincode);
            }
            if (pinCodeToChange == null) {

                return personalNumber + " was not registered yet.";
            }
        }
        return personalNumber + "'s" + " pin code was edited successfully.";
    }


    public String deleteCustomer(String personalNumber) {
        Customer customerToBeDeleted = findCustomer(personalNumber);
        if (customerToBeDeleted != null) {
            customerList.remove(customerToBeDeleted);
            return "Customer " + personalNumber + " successfully removed." + EOL;
        }
        return "Cannot find customer:" + personalNumber + EOL;
    }

    //todo Christopher
    public String updateKYC(String occupation, double salary, String PEP, String FATCA) {
        return "";
    }

    // method for finding account object by Account Number
    public BankAccount getAccountByAccountNumber(String accountNumber) {
        for (BankAccount account : accountsList) {
            if (accountNumber.equals(account.getAccountNumber())) {
                return account;
            }
        }
        return null;
    }

    // ? discuss if this is needed. returns Account index in the list
    public int getAccountNumberIndex(String accountNumber) {
        for (int i = 0; i < this.accountsList.size(); i++) {
            if (this.accountsList.get(i).verifyAccountNumber(accountNumber)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isAccountNumberExist(String accountNumber) {
        return getAccountNumberIndex(accountNumber) != -1;
    }

    // todo add exceptions
    // new method for deposit using getAccountByAccountNumber
    public String deposit(String toAccount, double amount) {
        BankAccount account = getAccountByAccountNumber(toAccount);
        if (account == null) {
            return "Account doesn't exist.";
        }
        if (amount < 0) {
            return "Amount should be greater than 0.";
        } else {
            account.addToUpdateBalance(amount);
            Deposit deposit = new Deposit(amount, toAccount);
            transactions.add(deposit);
            account.addTransaction(deposit);
            return account.getType() + " balance was updated successfully!" + EOL +
                    "Current balance is: " + account.getBalance() + " SEK.";
        }
    }

    // todo add exceptions
    public String payTransfer(String fromAccountNumber, String toAccountNumber, double amount, String note) {
        BankAccount account = getAccountByAccountNumber(fromAccountNumber);
        if (account == null) {
            return "Account doesn't exist.";
        }
        if (amount <= 0) {
            return "Amount should be greater than 0.";
        }
        if (amount > account.getBalance()) {
            return "Not enough funds on account #" + account.getAccountNumber();
        }
        if (toAccountNumber.length() != 6) {
            return "Account number should be 6 digits.";
        } else {
            account.subtractToUpdateBalance(amount);
            Withdrawal withdrawal = new Withdrawal(amount, fromAccountNumber, toAccountNumber, note);
            transactions.add(withdrawal);
            account.addTransaction(withdrawal);
            return "Transaction successful!" + EOL +
                    account.getType() + " #" + fromAccountNumber + " Current Balance: " + account.getBalance() + " SEK." + EOL;
        }
    }

    public String saveRecipient(Customer currentUser, String fromAccount, String toAccountNumber, String note, String name) {

        Withdrawal withdrawal = new Withdrawal(0.0, fromAccount, toAccountNumber, note, name);
        currentUser.addRecipient(withdrawal);
        return "Saved!";
    }


    // todo add exceptions
    public String withdraw(String fromAccount, double amount) {
        BankAccount account = getAccountByAccountNumber(fromAccount);
        if (account == null) {
            return "Account doesn't exist.";
        }
        if (amount <= 0) {
            return "Amount should be greater than 0.";
        }
        if (amount > account.getBalance()) {
            return "Not enough funds to withdraw from account " + account.getAccountNumber();
        } else {
            Withdrawal withdrawal = new Withdrawal(amount, fromAccount);
            transactions.add(withdrawal);
            account.addTransaction(withdrawal);
            account.subtractToUpdateBalance(amount);
            return account.getType() + " balance was updated successfully.";
        }
    }

    // todo change to exceptions
    // new method for transferring Funds using getAccountByAccountNumber
    public String transferFundsBetweenAccounts(double amount, String fromAccountNumber, String toAccountNumber) {
        BankAccount fromAccount = getAccountByAccountNumber(fromAccountNumber);
        BankAccount toAccount = getAccountByAccountNumber(toAccountNumber);
        if (toAccount == null || fromAccount == null) {
            return "Can't find account. Please check if the accounts' numbers are correct";
        } else if (checkBalance(fromAccountNumber) < amount) {
            return "Not enough funds.";
        } else {
            withdraw(fromAccountNumber, amount);
            deposit(toAccountNumber, amount);
            return "Transfer successful!" + EOL +
                    fromAccount.getType() + " #" + fromAccount.getAccountNumber() + " Current Balance: " + fromAccount.getBalance() + " SEK." + EOL +
                    toAccount.getType() + " #" + toAccount.getAccountNumber() + " Current Balance: " + toAccount.getBalance() + " SEK." + EOL;
        }
    }

    public String printAccountsAndBalance(Customer currentUser) {
        List<BankAccount> accounts = currentUser.getBankAccounts();
        String checkingAccountOutput = "";
        String savingsAccountOutput = "";
        if (accounts.isEmpty()) {
            return "No accounts open yet. Please return to the menu and register KYC first.";
        } else
            for (BankAccount account : accounts) {
                if (account instanceof CheckingAccount) {
                    checkingAccountOutput = account.toString();
                }
                if (account instanceof SavingsAccount) {
                    savingsAccountOutput = account.toString();
                }
            }
        return checkingAccountOutput + EOL +
                "------------------------------------- " + EOL +
                savingsAccountOutput + EOL +
                "------------------------------------- " + EOL;
    }

    public String printAccounts(Customer currentUser) {
        String operationResult = "0. Return to the previous menu" + EOL;
        List<BankAccount> accounts = currentUser.getBankAccounts();
        String checkingAccountOutput = "";
        String savingsAccountOutput = "";
        if (accounts.isEmpty()) {
            operationResult += "No accounts open yet. Please return to the menu and register KYC first.";
        } else {
            for (BankAccount account : accounts) {
                if (account instanceof CheckingAccount) {
                    checkingAccountOutput = "1. Checking Account: #" + account.getAccountNumber() + EOL;
                }
                if (account instanceof SavingsAccount) {
                    savingsAccountOutput = "2. Savings Account: #" + account.getAccountNumber() + EOL;
                }
            }
        }
        operationResult += checkingAccountOutput + savingsAccountOutput;
        return operationResult;
    }

    public String chooseSecondAccount(Customer currentUser, String fromAccount) {
        List<BankAccount> accounts = currentUser.getBankAccounts();
        for (BankAccount account : accounts) {
            if (!account.getAccountNumber().equals(fromAccount)) {
                return account.getAccountNumber();
            }
        }
        return "";
    }

    public String printAllTransactions(List<Transaction> transactions) {
        int index = 0;
        String operationResult = "All transactions:" + EOL;
        for (Transaction tx : transactions) {
            index++;
            operationResult += index + ". " + tx.toString();
        }
        if (index == 0) {
            operationResult = "No transactions so far.";
        }
        return operationResult;
    }

    public String printAllRecipients(List<Transaction> transactions) {
        int index = 0;
        String operationResult = "";
        for (Transaction tx : transactions) {
            index++;
            operationResult += index + ". " + tx.toString();
        }
        if (index == 0) {
            operationResult = "No recipients has been saved so far.";
        }
        return operationResult;
    }

    public String printAllWithdrawals(List<Transaction> transactions) {
        int index = 0;
        String operationResult = "All withdrawals:" + EOL;
        for (Transaction tx : transactions) {
            if (tx instanceof Withdrawal) {
                index++;
                operationResult += index + ". " + tx;
            }
        }
        if (index == 0) {
            operationResult = "No withdrawals so far.";
        }
        return operationResult;
    }

    public String printAllDeposits(List<Transaction> transactions) {
        int index = 0;
        String operationResult = "All deposits:" + EOL;
        for (Transaction tx : transactions) {
            if (tx instanceof Deposit) {
                index++;
                operationResult += index + ". " + tx;
            }

        }
        if (index == 0) {
            operationResult = "No deposits so far.";
        }
        return operationResult;
    }


    public double checkBalance(String accountNumber) {
        return getAccountByAccountNumber(accountNumber).getBalance();
    }

    //todo Anna LOAN

    /**
     * WHERE LOAN BEGIN:
     * <p>
     * ╭━┳━╭━╭━╮╮
     * ┃┈┈┈┣▅╋▅┫┃
     * ┃┈┃┈╰━╰━━━━━━╮
     * ╰┳╯┈┈┈┈┈┈┈┈┈◢▉◣
     * ╲┃┈┈┈┈┈┈┈┈┈┈▉▉▉
     * ╲┃┈┈┈┈┈┈┈┈┈┈◥▉◤
     * ╲┃┈┈┈┈╭━┳━━━━╯
     * ╲┣━━━━━━┫
     */

    public int searchForLoanIndex(String personalNumber) {
        for (int i = 0; i < this.loanList.size(); i++) {
            if (this.loanList.get(i).getPersonalNumber().equals(personalNumber)) {
                return i;
            }
        }
        return -1;
    }

    public boolean containsLoanID(String personalNumber) {
        for (Loan loan : loanList) {
            if (loan.getPersonalNumber().equals(personalNumber)) {
                return true;
            }
        }
        return false;
    }

    public String viewLoan(String personalNumber) {
        int index = searchForLoanIndex(personalNumber);
        if (index == -1) {
            return (" No loan. Would you like to apply for a loan?");
        } else {
            return loanList.get(index).toString();
        }
    }

    public String applyLoan(String personalNumber, double monthlyIncome, double currentLoanDebt, double currentCreditDebt, int appliedLoanAmount, int appliedLoanDuration) {
        LoanApplication loanApplication = new LoanApplication(personalNumber, monthlyIncome, currentLoanDebt, currentCreditDebt, appliedLoanAmount, appliedLoanDuration);
        loanApplicationList.add(loanApplication);
        return "Your loan application has been received; we will get back to you within 24 hours.";
    }


    public String increaseLoan(String personalNumber, double monthlyIncome, double currentLoanDebt, double currentCreditDebt, int appliedLoanAmount, int appliedLoanDuration, double loanDebt) {
        IncreaseLoan increaseLoan = new IncreaseLoan(personalNumber, monthlyIncome, currentLoanDebt, currentCreditDebt, appliedLoanAmount, appliedLoanDuration, loanDebt);
        loanApplicationList.add(increaseLoan);
        return "Your loan application has been received; we will get back to you within 24 hours.";
    }
    //todo For Employee - to collect and approve loans, add loan list and then I can collect loan debt (-Anna)

    public String viewAllLoanApplications(String personalNumber) {
        if (loanApplicationList.isEmpty()) {
            return "Currently no loan applications waiting for review.";
        }
        String message = "All loan applications:";
        for (LoanApplication loanApplication : loanApplicationList) {
            message += (loanApplication.getPersonalNumber());
        }
        return message;
    }

    public String viewMessage(Customer currentUser) {
        return currentUser.viewMessage();
    }

    // Meddelanden behöver tas bort, både employee och customer
    //
    public void removeMessage(Customer currentUser) {
        currentUser.removeMessage();
    }

    public void removeMessage() {
        employee.removeMessage();
    }

    public String viewMessage() {
        return employee.viewMessage();
    }

    public String messageToCustomer(String personalNumber, String newMessage) {
        Customer foundCustomer = findCustomer(personalNumber);
        foundCustomer.addMessage(newMessage);
        return "Message sent";
    }

    public int numberOfMessages() {
        return employee.numberOfMessages();
    }

    public int numberOfMessages(Customer customer) {
        return customer.numberOfMessages();
    }


    public String messageToEmployee(Customer currentUser, String newMessage) {
        employee.addMessage("Message from: " + currentUser.getPersonalNumber() + System.lineSeparator() + newMessage);
        return "Message sent.";
    }

    public String fetchPersonalNumber() {
        String message = viewMessage();
        String personalNumber = message.substring(14, 24);
        return personalNumber;
    }

    public boolean verifyEmployee(String userName, String pinCode) {
        if (employee.getEmployeeID().equals(userName.trim().toLowerCase(Locale.ROOT)) && employee.getPinCode().equals(pinCode.trim().toLowerCase(Locale.ROOT))) {
            return true;
        }
        return false;
    }


    //todo Faiza
    public String openNewAccount() {
        return "";
    }

    //todo Faiza
    public String closeAccount(String accountNumber) {
        return "";
    }

    //todo Faiza
    public void chooseAccount() {

    }


    public Customer findCustomer(String personalNumber) {
        try {
            if (customerList.size() > 0) {
                for (Customer customer : customerList) {
                    if (customer.getPersonalNumber().equals(personalNumber)) {
                        return customer;
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public Customer getCustomerByPN(String pn) {
        for (Customer c : customerList) {
            if (c.getPersonalNumber().equals(pn)) {
                return c;
            }
        }
        return null;
    }

    //TODO DELETE
    public void addAccount(BankAccount acc) {
        accountsList.add(acc);
    }
}

