package businessLogic.bankAccounts;

public class SavingsAccount extends BankAccount {

    private final double interestRate = 0.0;

    public SavingsAccount(String customerPersonalNumber){
        super(customerPersonalNumber);
    }

    // method should update balance by adding interest every month
    public void addInterest(){
    }

}
