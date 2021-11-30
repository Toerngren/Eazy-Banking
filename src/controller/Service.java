package controller;

import businessLogic.KYC;
import businessLogic.User.Customer;
import businessLogic.bankAccounts.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class Service { // This is like our facade. Where we place all our business logic

    private List<Customer> customerList;
    private List<BankAccount> accountsList;
    private List<KYC> Kyc;


    public Service(){
        customerList = new ArrayList<>();
    }

    public String createCustomer(String personalNumber, String firstName,String lastName, String email,
                                 String telephone, String password, String pinCode) {
        Customer customer = new Customer(personalNumber, firstName, lastName, email, telephone, password, pinCode);
        customerList.add(customer);
        return "Customer is registered successfully.";
    }

    public String verifyCustomerID(String personalNumber, String password){
        return "";
    }

    public String editCustomerDetail(String firstName,String lastName, String email,
                                     String telephone, String password, String pinCode){

        return "";
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
            return "Account number does not exist.";
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
        return "";
    }

    public String transferMoney(String accountNumber1, String accountNumber2, double amount){
        return "";
    }

    public String checkBalance(){
        return "";
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