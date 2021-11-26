package businessLogic.Transactions;

public class Withdrawal extends Transaction {

    public Withdrawal(double amount, String fromAccount, String toAccount) {
        super(amount, fromAccount, toAccount);
    }

    public Withdrawal(double amount, String fromAccount, String toAccount, String note) {
        super(amount, fromAccount, toAccount, note);
    }

    @Override
    public String toString() {
        if (!getNote().isEmpty()) {
           return getDate() + " Withdrawal to " + getToAccount() + " : SEK " + getAmount() + " - " + getNote();
        } else {
         return getDate() + " Withdrawal to " + getToAccount() + " : SEK " + getAmount();
        }
    }
}
