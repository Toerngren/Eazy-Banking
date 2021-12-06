package businessLogic.Transactions;

public class Deposit extends Transaction {

    public Deposit(double amount, String fromAccount, String toAccount) {

        super(amount, fromAccount, toAccount);
    }

    public Deposit(double amount, String fromAccount, String toAccount, String note) {
        super(amount, fromAccount, toAccount, note);
    }

    public Deposit (double amount, String toAccount) {
        super(amount, "", toAccount);
    }

    @Override
    public String toString() {
        if (!getNote().isEmpty()) {
            return getDate() + " Deposit from " + getFromAccount() + " : SEK " + getAmount() + " - " + getNote();
        } else {
            return getDate() + " Deposit from " + getFromAccount() + " : SEK " + getAmount();
        }
    }
}
