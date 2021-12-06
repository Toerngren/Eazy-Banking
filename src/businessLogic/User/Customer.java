package businessLogic.User;

import businessLogic.Inbox_Customer;

public class Customer{

    private String personalNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private String password;
    private String pinCode;
    private Inbox_Customer inbox;

    public Customer(String personalNumber, String firstName,String lastName, String email, String password, String telephone, String pinCode) {
        this.personalNumber = personalNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
        this.password = password;
        this.pinCode = pinCode;
        this.inbox = new Inbox_Customer();
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public boolean verifyCustomer(String password){
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
        return "First name and sur name: " +firstName + lastName +  " Personal number: " + personalNumber; // feel free to edit toString
    }
    // Inbox methods
    public String addNewMessage(String newMessage){
        return  inbox.addNewMessage(newMessage);
    }

    public boolean verifyPassword(String password) {
        if (password.equals(this.password)) {
            return true;
        } else {
            return false;
        }
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









