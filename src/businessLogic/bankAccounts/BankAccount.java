package businessLogic.bankAccounts;

import Utility.Calculator;

import java.util.Date;
import java.util.List;

public abstract class BankAccount {

    private final String customerPersonalNumber;
    private final String accountNumber;
    private double balance;
    private List<String> transactions;

    public BankAccount(String customerPersonalNumber){
        this.customerPersonalNumber = customerPersonalNumber;
        this.accountNumber = generateAccountNumber();
        this.balance = 0.0;
    }

    private String generateAccountNumber(){
        return Integer.toString(Calculator.randomNumberGenerator(999999,555555)) ;
    }

    public String getCustomerPersonalNumber(){
        return this.customerPersonalNumber;
    }

    public String getAccountNumber(){
        return this.accountNumber;
    }

    public boolean verifyAccountNumber(String accountNumber){
        return this.accountNumber.equals(accountNumber);
    }

    public double getBalance(){
        return this.balance;
    }

    public void addToUpdateBalance(double newBalance){
        this.balance = this.balance + newBalance;
    }

    public void subtractToUpdateBalance(double newBalance){
        this.balance = this.balance - newBalance;
    }

    public void addTransaction(String transactionID, double amount, Date date, double fromAccount,
                               double toAccount, String note){

    }

    public void printLatestTransaction(){

    }

    public void printAllTransaction(){

    }

    public String toString(){
        return "Account Number: " + accountNumber + " Balance: " + this.balance;
    }
}
