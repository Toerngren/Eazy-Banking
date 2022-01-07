package businessLogic.Transactions;

import java.util.Date;
import java.util.UUID;

public class Transaction {

    // withdrawal and deposit can be merged into this class by specifying toStrings with if and
    // adding transactionType with enum. My suggestion is to keep three classes for now.

    private String transactionID;
    private Date date;
    private double amount;
    private String fromAccount = "";
    private String toAccount = "";
    private String note = "";


    public Transaction(double amount, String fromAccount, String toAccount) {
        this.transactionID = UUID.randomUUID().toString();
        this.date = new Date();
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public Transaction(double amount, String fromAccount, String toAccount, String note) {
        this(amount, fromAccount,toAccount);
        this.note = note;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public String getNote() {
        return note;
    }

    public String getName() {
        return "";
    }

}
