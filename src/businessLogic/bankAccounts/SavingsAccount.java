package businessLogic.bankAccounts;

public class SavingsAccount extends BankAccount {

    public static final String EOL = System.lineSeparator();
    private static final String TYPE = "Savings Account";

    private final double interestRate = 0.0;

    public SavingsAccount(String customerPersonalNumber){
        super(customerPersonalNumber);
    }

    // method should update balance by adding interest every month
    public void addInterest(){
    }

    public String toString(){
        return "Savings Account Number: " + getAccountNumber() + EOL +
                "Balance: " + getBalance() + " SEK.";
    }

    public String getType() {
        return this.TYPE;
    }

}
