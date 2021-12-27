package businessLogic.bankAccounts;

public class CheckingAccount extends BankAccount {

    public static final String EOL = System.lineSeparator();
    private static final String TYPE = "Checking Account";

    public CheckingAccount(String customerPersonalNumber){
        super(customerPersonalNumber);
    }

    public String toString(){
        return "Checking Account Number: " + getAccountNumber() + EOL +
                "Balance: " + getBalance() + " SEK.";
    }

    public String getType() {
        return this.TYPE;
    }

}
