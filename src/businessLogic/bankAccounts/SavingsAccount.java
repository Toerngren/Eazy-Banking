package businessLogic.bankAccounts;

public class SavingsAccount extends BankAccount {

    private final double interestRate = 0.0;

    public SavingsAccount(String customerPersonalNumber){
        super(customerPersonalNumber);
    }

    public void calculateInterest(){

    }
}
