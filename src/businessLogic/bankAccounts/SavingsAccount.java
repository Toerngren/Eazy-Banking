package businessLogic.bankAccounts;

public class SavingsAccount extends BankAccount {

    public static final String EOL = System.lineSeparator();
    private final String TYPE = "Savings Account";
    private static final double annualInterestRate = 0.10;

    public SavingsAccount(String customerPersonalNumber){
        super(customerPersonalNumber);
    }

    public double getAnnualInterestRate(){
        return annualInterestRate;
    }

    // method should update balance by adding interest every month
    public void addMonthlyInterest(){
        double profit = getBalance() * annualInterestRate/12;
        setBalance(profit);
    }

    public String toString(){
        return "Savings Account Number: " + getAccountNumber() + EOL +
                "Annual interest rate: "+ getAnnualInterestRate() + EOL +
                "Balance: " + getBalance() + " SEK.";
    }

    public String getType() {
        return this.TYPE;
    }
}
