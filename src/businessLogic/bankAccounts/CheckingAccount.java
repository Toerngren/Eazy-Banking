package businessLogic.bankAccounts;

public class CheckingAccount extends BankAccount {

    public static final String EOL = System.lineSeparator();
    private static final String TYPE = "Checking Account";

    public CheckingAccount(String customerPersonalNumber){
        super(customerPersonalNumber);
    }

 //todo unclear what this method is for
    public void payBillS(){

    }

    public String toString(){
        return "Checking Account Number: " + getAccountNumber() + EOL +
                "Balance: " + getBalance() + " SEK.";
    }

    public String getType() {
        return this.TYPE;
    }

}
