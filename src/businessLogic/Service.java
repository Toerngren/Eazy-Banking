package businessLogic;

import businessLogic.User.Customer;

import java.util.ArrayList;
import java.util.List;

public class Service { // This is like our facade. Where we place all our business logic

    private List<Customer> customerList;

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

    public String depositMoney(String accountNumber, double amount){
        return "";
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

}