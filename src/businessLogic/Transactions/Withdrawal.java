package businessLogic.Transactions;

public class Withdrawal extends Transaction {

    public static final String EOL = System.lineSeparator();

    private String name = "";

    public Withdrawal(double amount, String fromAccount, String toAccount) {
        super(amount, fromAccount, toAccount);
    }

    public Withdrawal(double amount, String fromAccount, String toAccount, String note) {
        super(amount, fromAccount, toAccount, note);
    }

    public Withdrawal(double amount, String fromAccount) {
        super(amount, fromAccount, "");
    }

    public Withdrawal(double amount, String fromAccount, String toAccount, String note, String name) {
        super(amount, fromAccount, toAccount, note);
        this.name = name;
    }


    @Override
    public String toString() {
        if (!getName().isEmpty()) {
            return "Transaction (Recipient) name: " + getName() + EOL +
                    "   Recipient Account #" + getToAccount() + EOL +
                    "   Note: " + getNote() + EOL;
        } else if (!getNote().isEmpty() && !getToAccount().isEmpty()) {
            return getDate() + " Withdrawal from #" + getFromAccount() + " to #" + getToAccount() + " : SEK " + getAmount() + " - " + getNote() + EOL;
        } else if (getToAccount().isEmpty()) {
            return getDate() + " Withdrawal from #" + getFromAccount() + " : SEK " + getAmount() + EOL;
        } else if (!getNote().isEmpty() && getToAccount().isEmpty()) {
            return getDate() + " Withdrawal from #" + getFromAccount() + " : SEK " + getAmount() + " - " + getNote() + EOL;
        } else {
            return getDate() + " Withdrawal from #" + getFromAccount() + " to #" + getToAccount() + " : SEK " + getAmount() + EOL;
        }
    }

    public String getName() {
        return this.name;
    }
}
