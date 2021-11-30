package View;

import Utility.Printing;
import Utility.UserInput;
import controller.Service;

public class MyAccount {

      private static final Service service = new Service();

       public static void myAccount() {
        String option;

        do {
            Printing.myAccount();
            option = UserInput.readLine("");
            switch (option) {

                case "0":
                    StartPage.startPage();
                    break;
                case "1":
                    deposit();
                    break;
                case "2":
                    withdraw();
                    break;
                case "3":
                    transfer();
                    break;
                case "4":
                    makePayment();
                    break;
                case "5":
                    printTransactionHistory();
                    break;
                case "6":
                    checkBalance();
                    break;
                default:
                    Printing.invalidEntry();
                    break;
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