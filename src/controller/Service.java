package controller;

import businessLogic.Transactions.Deposit;
import businessLogic.Transactions.Transaction;
import businessLogic.Transactions.Withdrawal;
import businessLogic.User.KYC;
import businessLogic.User.Customer;
import businessLogic.bankAccounts.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class Service { // This is like our facade. Where we place all our business logic

    private List<Customer> customerList;
    private List<BankAccount> accountsList;
    private List<KYC> KycList;
    private List<Transaction> transactions;
    private List<Transaction> savedRecipients;
    private List<KYC> kycApprovedList;
    // private Account loggedInAccount;


    public Service() {
        customerList = new ArrayList<>();
        accountsList = new ArrayList<>();
        KycList = new ArrayList<>();
        transactions = new ArrayList<>();
        savedRecipients = new ArrayList<>();
        kycApprovedList = new ArrayList<>();
    }

    public String createCustomer(String personalNumber, String firstName, String lastName, String email,
                                 String telephone, String password, String pinCode) {
        Customer customer = new Customer(personalNumber, firstName, lastName, email, telephone, password, pinCode);
        customerList.add(customer);
        return System.lineSeparator() + "You have now been registered!" + System.lineSeparator();
    }

    public void createKYC(String personalNumber, String occupation, double salary, boolean pep, boolean fatca, boolean approved){
        KYC kyc = new KYC(personalNumber, occupation, salary, pep,fatca,approved);
        KycList.add(kyc);
    }



    //todo Adrian
    public String verifyCustomerID(String personalNumber, String password) {
        return "";
    }

    public int getCustomerIndex(String personalNumber){
        for (int i = 0; i < this.accountsList.size(); i++){
            if (this.accountsList.get(i).verifyAccountNumber(personalNumber)){
                return i;
            }
        }
        return -1;
    }

    public KYC findKYC(Customer customer){
        try{
            if (KycList.size() > 0){
                for (KYC kyc : KycList){
                    if (customer.getPersonalNumber().equals(kyc.getPersonalNumber())){
                        return kyc;
                    }
                }
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public String viewKYC (Customer customer) {
        if (findKYC(customer) == null) {
            return "No KYC registered yet.";
        } else {
            KYC customersKYC = findKYC(customer);
            return displayKYC(customersKYC);
        }
    }

    public String registerKYC (Customer customer, String occupation, double salary, boolean pep, boolean fatca){
        KYC kyc = new KYC(customer.getPersonalNumber(), occupation, salary, pep, fatca, false);
        KycList.add(kyc);
        return System.lineSeparator() + "KYC awaiting review." + System.lineSeparator();
    }

    public boolean approvedKYC (Customer customer){
        for (KYC approvedKYC : kycApprovedList){
            if (customer.getPersonalNumber().equals(approvedKYC.getPersonalNumber())){
                return true;
            }
        } return false;
    }


    public String reviewUnapprovedKYC (String review){
        KYC unapprovedKYC = findUnapprovedKYC();
        String result = "";
        if (review.equals("1")){
            unapprovedKYC.setApproved(true);
            kycApprovedList.add(unapprovedKYC);
            result = "Customers KYC has been approved.";
        } else if(review.equals("2")){
            KycList.remove(unapprovedKYC);
            result = "Customers KYC has been declined.";
        } else {
            result = "Please input either 1 or 2";
        }
        return result + System.lineSeparator();
    }

    public String displayKYC(KYC kyc){
        String pepStatus = "";
        String fatcaStatus = "";
        String approvedStatus = "";
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
        if (kyc.isApproved()){
            approvedStatus = "Yes";
        } else {
            approvedStatus = "Awaiting review";
        }
        String result = "Personalnumber: " + kyc.getPersonalNumber() + System.lineSeparator() +
                "Occupation: " + kyc.getOccupation() + System.lineSeparator() +
                "Salary: " + kyc.getSalary() + System.lineSeparator() +
                "Politically exposed customer: " + pepStatus + System.lineSeparator() +
                "Affected by FATCA: " + fatcaStatus + System.lineSeparator() +
                "Approved: " + approvedStatus + System.lineSeparator();
        return result;
    }

    public String showUnapprovedKYC (){
        KYC unapprovedKYC = findUnapprovedKYC();
        if (unapprovedKYC != null){
            return displayKYC(unapprovedKYC);
        }
        else {
            return "No KYC registered for this customer." + System.lineSeparator();
        }
    }

    public String numberOfApprovedKYCs(){
        String result = "";
        if (kycApprovedList.isEmpty()){
            result = "There are currently no approved KYCs." + System.lineSeparator();
        } else {
            result = "There are " + kycApprovedList.size() + " approved reviews." + System.lineSeparator();
        } return result + System.lineSeparator();
    }

    public String numberOfUnapprovedKYCs() {
        String result = "";
        int counter = 0;
        if (KycList.isEmpty()) {
            result = "There are currently no unapproved KYCs.";
        } else {
            for (KYC unapprovedKYC : KycList) {
                if (!unapprovedKYC.isApproved()) {
                    counter++;
                }
                result = "There are currently " + counter +" unapproved KYCs." + System.lineSeparator();
            }
        } return result;
    }


    public String printAllApprovedKYCs(){
            String allApprovedKYCs = "All approved KYCs:";
            for (KYC approvedKYC : kycApprovedList) {
                allApprovedKYCs = allApprovedKYCs + System.lineSeparator() + approvedKYC.toString();
            }
            return allApprovedKYCs;
        }

    public KYC findUnapprovedKYC (){
        for (KYC kyc : KycList){
            if (!kyc.isApproved()){
                return kyc;
            }
        } return null;
    }

    public boolean isCustomerExist(String personalNumber){
        return getCustomerIndex(personalNumber) != -1;
    }

    public boolean containsCustomer(String personalNumber){
        for (Customer customer : customerList) {
            if (customer.getPersonalNumber().equals(personalNumber)) {
                return true;
            }
        }
        return false;
    }


    public boolean verifyCustomer(String personalNumber, String password){
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
        String allCustomers = "All registered customers:";

        for (Customer customer : customerList) {
            allCustomers = allCustomers + System.lineSeparator() + customer.toString();
        }
        return allCustomers;
    }


    public boolean onlyDigits(String personalNumber){
        for (int i = 0; i < personalNumber.length(); i++) {
            if (!Character.isDigit(personalNumber.charAt(i))){
                return false;
            }
        } return true;
    }



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

    public boolean employeeLoginCheck(String username, String password){
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
        return personalNumber + "'s" + " pincode was edited successfully.";
    }


    public String deleteCustomer(String personalNumber) {
        Customer customerToBeDeleted = findCustomer(personalNumber);
        if (customerToBeDeleted != null) {
            customerList.remove(customerToBeDeleted);
            return "Customer " + personalNumber + "successfully removed.";
        }
        return "Cannot find customer:" + personalNumber;
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
            return "Account balance was update successfully.";
        }
    }

    // new method for withdraw using getAccountByAccountNumber
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
            account.subtractToUpdateBalance(amount);
            return "Account balance was update successfully.";
        }
    }

    // todo transferToYourAccount

    // new method for transferring Funds using getAccountByAccountNumber
    public String transferFunds(double amount, String fromAccountNumber, String toAccountNumber) {
        BankAccount fromAccount = getAccountByAccountNumber(fromAccountNumber);
        BankAccount toAccount = getAccountByAccountNumber(toAccountNumber);
        if (toAccount == null || fromAccount == null) {
            return "Can't find account. Please check if the accounts' numbers are correct";
        }
        if (amount < fromAccount.getBalance()) {
            return "Amount should be greater than 0.";
        } else {
            return withdraw(fromAccountNumber, amount) + " " + deposit(toAccountNumber, amount);
        }
    }

    /*

    public String depositMoney(String accountNumber, double amount) {
        if (!isAccountNumberExist(accountNumber)) {
            return accountNumber + " Account number does not exist.";
        }
        if (amount <= 0) {
            return "Amount must be greater than 0";
        } else {
            int index = getAccountNumberIndex(accountNumber);
            this.accountsList.get(index).addToUpdateBalance(amount);
        }
        return amount + " has been deposit to the account " + accountNumber;
    }
/*
    public String withdrawMoney(String accountNumber, double amount) {
        BankAccount account = getAccountByAccountNumber(accountNumber);

        if (!isAccountNumberExist(accountNumber)) {
            return accountNumber + " Account number does not exist.";
        }
        int index = getAccountNumberIndex(accountNumber);
        if (this.accountsList.get(index).getBalance() < amount) {
            return "Not enough money to withdraw from account no. " + accountNumber;
        } else {
            // Margaret added the transaction creation
            Withdrawal wTransaction = new Withdrawal(amount, accountNumber);
            transactions.add(wTransaction);
            account.addTransaction(wTransaction);
            this.accountsList.get(index).subtractToUpdateBalance(wTransaction.getAmount());
        }
        return amount + " SEK has been withdrawn from account no. " + accountNumber;
    }
 */
    /*
    public String transferMoney(String accountNumberFrom, String accountNumberTo, double amount) {
        if (!isAccountNumberExist(accountNumberFrom)) {
            return accountNumberFrom + " Account number does not exist.";
        }
        if (!isAccountNumberExist(accountNumberTo)) {
            return accountNumberTo + " Account number does not exist.";
        }
        int index1 = getAccountNumberIndex(accountNumberFrom);
        int index2 = getAccountNumberIndex(accountNumberFrom);
        if (this.accountsList.get(index1).getBalance() < amount) {
            return "Not enough money to transfer from account no. " + accountNumberFrom;
        } else {
            this.accountsList.get(index1).subtractToUpdateBalance(amount);
            this.accountsList.get(index2).addToUpdateBalance(amount);
        }
        return amount + " SEK has been transferred to account no. " + accountNumberTo;
    }

 */

    // Margaret added a new method using getAccountByNumber without indexes
    public String printBalance(String accountNumber) {
        BankAccount account = getAccountByAccountNumber(accountNumber);
        if (account == null) {
            return accountNumber + " account doesn't exist.";
        } else {
            Double balance = account.getBalance();
            return accountNumber + " account balance is " + balance;
        }
    }


    public String checkBalance(String accountNumber) {
        String balance;
        if (!isAccountNumberExist(accountNumber)) {
            return accountNumber + " Account number does not exist.";
        } else {
            int index = getAccountNumberIndex(accountNumber);
            balance = Double.toString(this.accountsList.get(index).getBalance());
        }
        return balance;
    }

    //todo Anna
    public String applyLoan() {
        return "";
    }
    //todo Faiza
    public String openNewAccount() {
        return "";
    }

    //todo Faiza
    public String closeAccount(String accountNumber){
        return "";
    }

    //todo Faiza
    public void chooseAccount() {

    }
    //todo Pontus
    public void checkInbox() {

    }


    public String sendMessage(){
        return "";
    }

    public void receiveMessage(){

    }

    public Customer findCustomer(String personalNumber){
    try {
        if (customerList.size() > 0) {
            for (Customer customer : customerList) {
                if (customer.getPersonalNumber().equals(personalNumber)) {
                    return customer;
                }
            }
        }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    //Inbox methods
    public String addNewMessage(String personalNumber, String newMessage){
        int index = getCustomerIndex(personalNumber);
        return this.customerList.get(index).addNewMessage(newMessage);
    }

    public String addReadMessage(){
        return "";
    }

    public String printUnreadMessages(){
        return "";
    }

    public String printReadMessages(){
        return "";
    }

    public String printAllMessages(){
        return "";
    }


}