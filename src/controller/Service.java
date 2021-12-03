package controller;

import businessLogic.KYC;
import businessLogic.User.Customer;
import businessLogic.bankAccounts.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class Service { // This is like our facade. Where we place all our business logic

    private List<Customer> customerList;
    private List<BankAccount> accountsList;
    private List<KYC> KycList;


    public Service(){
        this.customerList = new ArrayList<>();
        this.accountsList = new ArrayList<>();
        this.KycList = new ArrayList<>();
    }

    public String createCustomer(String personalNumber, String firstName,String lastName, String email,
                                 String telephone, String password, String pinCode) {
        Customer customer = new Customer(personalNumber, firstName, lastName, email, telephone, password, pinCode);
        customerList.add(customer);
        return "Customer is registered successfully.";
    }

    public int getCustomerIndex(String personalNumber){
        for (int i = 0; i < this.accountsList.size(); i++){
            if (this.accountsList.get(i).verifyAccountNumber(personalNumber)){
                return i;
            }
        }
        return -1;
    }

    public boolean isCustomerExist(String personalNumber){
        return getCustomerIndex(personalNumber) != -1;
    }

    public boolean verifyCustomer(String personalNumber, String password){
        int index = getCustomerIndex(personalNumber);
        return !this.customerList.get(index).verifyCustomer(password);
    }

    public String editCustomerDetail(String firstName,String lastName, String email,
                                     String telephone, String password, String pinCode){

        return "";
    }
    public String printAllCustomers(){
        String allCustomers = "All registered customers:";

        for(Customer customer : customerList){
            allCustomers = allCustomers + System.lineSeparator() + customer.toString();
        }
        return allCustomers + System.lineSeparator();
    }

    public String editCustomerFirstName(String personalNumber, String newFirstName) {

        String operationResult = "";
        Customer nameToChange = null;
        for (Customer currentName : customerList) {
            if (currentName.getPersonalNumber().equals(personalNumber)) {
                if (newFirstName.isEmpty()) {
                    return "Invalid entry.";
                }
                nameToChange = currentName;
                currentName.setFirstName(newFirstName);
                operationResult = personalNumber + "'s "+ " was edited successfully.";
            }
        }

        if (nameToChange == null) {
            operationResult = personalNumber + " was not registered yet.";
            return operationResult;
        }
        return operationResult;

    }

    public String deleteCustomer(String personalNumber){
        return "";
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