package controller;

import businessLogic.KYC;
import businessLogic.Transactions.Deposit;
import businessLogic.Transactions.Transaction;
import businessLogic.Transactions.Withdrawal;
import businessLogic.User.Customer;
import businessLogic.bankAccounts.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class Service { // This is like our facade. Where we place all our business logic

    private List<Customer> customerList;
    private List<BankAccount> accountsList;
    private List<KYC> Kyc;
    private List<Transaction> transactions;
    private List<Transaction> savedRecipients;
    // private Account loggedInAccount;


    public Service() {
        customerList = new ArrayList<>();
        accountsList = new ArrayList<>();
        Kyc = new ArrayList<>();
        transactions = new ArrayList<>();
        savedRecipients = new ArrayList<>();
    }

    public String createCustomer(String personalNumber, String firstName, String lastName, String email,
                                 String telephone, String password, String pinCode) {
        Customer customer = new Customer(personalNumber, firstName, lastName, email, telephone, password, pinCode);
        customerList.add(customer);
        return "Customer is registered successfully.";
    }
    //todo Adrian
    public String verifyCustomerID(String personalNumber, String password) {
        return "";
    }
    //todo Adrian
    public String editCustomerDetail(String firstName, String lastName, String email,
                                     String telephone, String password, String pinCode) {

        return "";
    }

    //todo Adrian
    public String deleteCustomer(String personalNumber) {
        return "";
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
    public String closeAccount() {
        return "";
    }

    //todo Faiza
    public void chooseAccount() {

    }
    //todo Pontus
    public void checkInbox() {

    }

    public Customer findCustomer(String personalNumber) {
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