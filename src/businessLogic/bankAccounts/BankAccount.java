package businessLogic.bankAccounts;

import Utility.Calculator;
import Utility.Utilities;

import businessLogic.Transactions.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BankAccount {

    private final String customerPersonalNumber;
    private final String accountNumber;
    private double balance;
    private Calendar date;
    private List<Transaction> transactionList;

    public BankAccount(String customerPersonalNumber){
        this.customerPersonalNumber = customerPersonalNumber;
        this.accountNumber = generateAccountNumber();
        this.balance = 0.0;
        this.date = Calendar.getInstance();
        this.transactionList = new ArrayList<>();
    }

    private String generateAccountNumber(){

        return Calculator.randomNumberGenerator() ;
    }

    public String getCustomerPersonalNumber(){

        return this.customerPersonalNumber;
    }

    public String getAccountNumber(){

        return this.accountNumber;
    }

    public Calendar getDate(){
        return this.date;
    }

    public boolean verifyAccountNumber(String accountNumber){

        return this.accountNumber.equals(accountNumber);
    }

    public double getBalance(){

        return Utilities.truncateDouble(this.balance, 2);
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public void addToUpdateBalance(double amount){

        this.balance = this.balance + amount;
    }

    public void subtractToUpdateBalance(double amount){

        this.balance = this.balance - amount;
    }

    public void addTransaction(Transaction tx){

        this.transactionList.add(tx);
    }

    public List<Transaction> getTransactionList() {
        return this.transactionList;
    }

    // todo Margaret to Update if needed
    public String toString() {
        return null;
    }

    public String getType() {
        return null;
    }

}
