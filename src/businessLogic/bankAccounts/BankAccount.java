package businessLogic.bankAccounts;

import java.util.Date;
import java.util.List;

public class BankAccount {

    private String accountNumber;
    private double balance;
    private List<String> transactions;

    public BankAccount(String accountNumber, double balance){
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber(){
        return this.accountNumber;
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
