package businessLogic.Transactions;

public class Deposit extends Transaction {
    public static final String EOL = System.lineSeparator();

    public Deposit(double amount, String fromAccount, String toAccount) {

        super(amount, fromAccount, toAccount);
    }

    public Deposit(double amount, String fromAccount, String toAccount, String note) {
        super(amount, fromAccount, toAccount, note);
    }

    @Override
    public String getName() {
        return "";
    }

    public Deposit (double amount, String toAccount) {
        super(amount, "", toAccount);
    }

    @Override
    public String toString() {
        if (getFromAccount().isEmpty()) {
            return getDate() + " Deposit to #" + getToAccount() + " : SEK " + getAmount() + EOL;
        } else if (!getNote().isEmpty()) {
            return getDate() + " Deposit from #" + getFromAccount() + " : SEK " + getAmount() + " - " + getNote() + EOL;
        } else {
            return getDate() + " Deposit from #" + getFromAccount() + " : SEK " + getAmount() + EOL;
        }
    }
}
