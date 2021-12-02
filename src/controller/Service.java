package controller;

import businessLogic.User.KYC;
import businessLogic.User.Customer;
import businessLogic.bankAccounts.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class Service { // This is like our facade. Where we place all our business logic

    private static final List<Customer> customerList = new ArrayList<>(); // So we use the same ArrayList everytime..
    private List<BankAccount> accountsList; // Keep in mind above^
    private List<KYC> Kyc;


    public Service() {
    }

    public String createCustomer(String personalNumber, String firstName, String lastName, String email,
                                 String telephone, String password, String pinCode) {
        Customer customer = new Customer(personalNumber, firstName, lastName, email, telephone, password, pinCode);

        customerList.add(customer);

        return "Customer is registered successfully.";
    }

    public String verifyCustomerID(String personalNumber, String password) { // Am I thinking this right?

        if(customerList.isEmpty()){
            return "No customers created yet.";
        }
        for (Customer customer : customerList) {
            if(!customer.getPersonalNumber().equals(personalNumber) && !customer.getPassword().equals(password)){
                return "Wrong personal number or password.";
            }
        }
        return "Verified customer.";
    }

    public String editCustomerDetail(String firstName, String lastName, String email,
                                     String telephone, String password, String pinCode) {
        // Done but 6 methods in total. see below
        return "";
    }

    public String printAllCustomers() {
        String allCustomers = "All registered customers:";

        for (Customer customer : customerList) {
            allCustomers = allCustomers + System.lineSeparator() + customer.toString();
        }
        return allCustomers + System.lineSeparator();
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

    public String updateKYC(String occupation, double salary, String PEP, String FATCA){
        return "";
    }

    public int getAccountNumberIndex(String accountNumber){
        for (int i = 0; i < this.accountsList.size(); i++){
            if (this.accountsList.get(i).verifyAccountNumber(accountNumber)){
                return i;
            }
        }
        return -1;
    }

    public boolean isAccountNumberExist(String accountNumber){
        return getAccountNumberIndex(accountNumber) != -1;
    }

    public String depositMoney(String accountNumber, double amount){
        if (!isAccountNumberExist(accountNumber)){
            return accountNumber + " Account number does not exist.";
        }
        if (amount <= 0){
            return "Amount must be greater than 0";
        }else {
            int index = getAccountNumberIndex(accountNumber);
            this.accountsList.get(index).addToUpdateBalance(amount);
        }
        return amount + " has been deposit to the account " + accountNumber;
    }

    public String withdrawMoney(String accountNumber, double amount){
        if (!isAccountNumberExist(accountNumber)){
            return accountNumber + " Account number does not exist.";
        }
        int index = getAccountNumberIndex(accountNumber);
        if (this.accountsList.get(index).getBalance() < amount){
            return "Not enough money to withdraw from account no. " + accountNumber;
        }else {
            this.accountsList.get(index).subtractToUpdateBalance(amount);
        }
        return amount + " SEK has been withdrawn from account no. " + accountNumber;
    }

    public String transferMoney(String accountNumberFrom, String accountNumberTo, double amount){
        if (!isAccountNumberExist(accountNumberFrom)){
            return accountNumberFrom + " Account number does not exist.";
        }
        if (!isAccountNumberExist(accountNumberTo)){
            return accountNumberTo + " Account number does not exist.";
        }
        int index1 = getAccountNumberIndex(accountNumberFrom);
        int index2 = getAccountNumberIndex(accountNumberFrom);
        if (this.accountsList.get(index1).getBalance() < amount){
            return "Not enough money to transfer from account no. " + accountNumberFrom;
        }else {
            this.accountsList.get(index1).subtractToUpdateBalance(amount);
            this.accountsList.get(index2).addToUpdateBalance(amount);
        }
        return amount + " SEK has been transferred to account no. " + accountNumberTo;
    }

    public String checkBalance(String accountNumber){
        String balance;
        if (!isAccountNumberExist(accountNumber)){
            return accountNumber + " Account number does not exist.";
        }
        else {
            int index = getAccountNumberIndex(accountNumber);
            balance = Double.toString(this.accountsList.get(index).getBalance()) ;
        }
        return balance;
    }

    public String applyLoan(){
        return "";
    }

    public String openNewAccount(){
        return "";
    }

    public String closeAccount(){
        return "";
    }

    public void chooseAccount(){

    }

    public void checkInbox(){

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
    } catch (Exception exception) {
        exception.printStackTrace();
    }
    return null;
}

}