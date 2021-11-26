package businessLogic;

public class SavingsAccount extends BankAccount{

    private final double interestRate = 0.0;

    public SavingsAccount(String accountNumber, double balance){
        super(accountNumber, balance);
    }

    public void calculateInterest(){

    }
}
