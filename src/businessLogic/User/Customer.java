package businessLogic.User;

import businessLogic.Transactions.Transaction;
import businessLogic.bankAccounts.*;

import java.util.ArrayList;
import java.util.List;

public class Customer{
    static final String EOL = System.lineSeparator();

    private String personalNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private String password;
    private String pinCode;

    // easier to work with 1 list instead of 2.
    private List<Transaction> savedRecipients;
    private ArrayList<String> customerMessageList;
    private List<CheckingAccount> checkingList = new ArrayList<>();
    private List<SavingsAccount> savingsList = new ArrayList<>();

    public Customer(String personalNumber, String firstName, String lastName, String email, String password, String telephone, String pinCode) {
        this.personalNumber = personalNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
        this.password = password;
        this.pinCode = pinCode;
        this.customerMessageList = new ArrayList<>();
        this.checkingList = new ArrayList<>();
        this.savingsList = new ArrayList<>();
        this.savedRecipients = new ArrayList<>();
    }

    public String addMessage(String message){
        this.customerMessageList.add(message);
        return "Message sent.";
    }

    public String viewMessage(){
        if(customerMessageList.isEmpty()){
            return EOL + "There are currently no new messages.";
        } else {
            return "Message from: " + customerMessageList.get(0);
        }
    }

    public int numberOfMessages(){
        return customerMessageList.size();
    }

    public void removeMessage(){
        customerMessageList.remove(0);
    }



    public String getPersonalNumber() {
        return personalNumber;
    }

    public boolean verifyCustomer(String password) {
        return this.password.equals(password);
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
    public String toString(){
        return "Full name: " +firstName +" "+ lastName + EOL + "Personal number: " + personalNumber + EOL + "E-mail: " + email + EOL + "Telephone number: " + telephone + EOL + "PIN-code: " + pinCode + EOL + "Password: " + password + EOL;
    }

    public boolean verifyPassword(String password) {
        if (password.equals(this.password)) {
            return true;
        } else {
            return false;
        }
    }

    public List<Transaction> getSavedRecipients() {
        return this.savedRecipients;
    }

    public void addRecipient(Transaction transaction){
        this.savedRecipients.add(transaction);
    }

    public List<CheckingAccount> getCheckingList() {
        return checkingList;
    }

    public void addCheckingList(CheckingAccount checkingAccount) throws Exception {
        if(checkingAccount.getAccountNumber().length() != 6){
            throw new Exception("Account number must be 6 characters.");
        } else {
            checkingList.add(checkingAccount);
        }
    }

    public List<SavingsAccount> getSavingsList() {
        return savingsList;
    }

    public void addSavingsList(SavingsAccount savingsAccount) throws Exception {
        if(savingsAccount.getAccountNumber().length() != 6){
            throw new Exception("Account number must be 6 characters.");
        } else {
            savingsList.add(savingsAccount);
        }
    }
}









