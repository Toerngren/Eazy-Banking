package View;

import Utility.Printing;
import Utility.UserInput;
import controller.Service;

public class MyAccount {

    private String personalNumber;
    private static final Service service = new Service();

    public MyAccount(String personalNumber){
        this.personalNumber = personalNumber;
    }

    public static void myAccount() {
        String option;

        do {
            Printing.myAccount();
            option = UserInput.readLine("");
            switch (option) {
                case "0" -> StartPage.startPage();
                case "1" -> deposit();
                case "2" -> withdraw();
                case "3" -> transfer();
                case "4" -> makePayment();
                case "5" -> printTransactionHistory();
                case "6" -> checkBalance();
                default -> Printing.invalidEntry();
            }
        } while (!(option.equals("0")));
        UserInput.exitScanner();
    }

    public static void deposit(){
        // Account no. should be available before coming to this step
        String accountNumber = UserInput.readLine("Enter account number: ");
        double amount = UserInput.readDouble("Enter amount to deposit: ");
        String message = service.depositMoney(accountNumber, amount);
        System.out.println(message);
    }

    public static void withdraw(){
        String accountNumber = UserInput.readLine("Enter account number: ");
        double amount = UserInput.readDouble("Enter amount to withdraw: ");
        String message = service.withdrawMoney(accountNumber, amount);
        System.out.println(message);
    }

    public static void transfer(){
        String accountNumber1 = UserInput.readLine("Enter account number to transfer from: ");
        String accountNumber2 = UserInput.readLine("Enter account number to transfer from: ");
        double amount = UserInput.readDouble("Enter amount to transfer: ");
        String message = service.transferMoney(accountNumber1, accountNumber2, amount);
        System.out.println(message);
    }

    public static void makePayment(){

    }

    public static void printTransactionHistory(){

    }

    public static void checkBalance(){

    }

}