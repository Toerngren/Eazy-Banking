package businessLogic.bankAccounts;

public class CheckingAccount extends BankAccount {

    private static final String TYPE = "Checking Account";
    public static final String EOL = System.lineSeparator();

    public CheckingAccount(String customerPersonalNumber){
        super(customerPersonalNumber);
    }

    public String toString(){
        return "Checking Account Number: #" + getAccountNumber() + " | Balance: " + getBalance() + " SEK.";
    }

    public String getType() {
        return this.TYPE;
    }

}
