package Utility;

public class Printing {

    static final String EOL = System.lineSeparator();

    public static void startPage() {
        System.out.println("Welcome to Eazy Banking!" + EOL +
                "0. Close system." + EOL +
                "1. Customer registration." + EOL +
                "2. Customer login." + EOL +
                "3. Employee login." + EOL);
    }

    public static void customerMenu(){
        System.out.println(EOL + "0. Log out" + EOL +
                "1. Payments & Transfers" + EOL +
                "2. Bank accounts" + EOL +
                "3. Loans" + EOL +
                "4. KYC" + EOL +
                "5. Customer profile" + EOL +
                "6. Customer support" + EOL);
    }

    public static void customerProfileMenu(){
        System.out.println(EOL + "0. Go back to My page." + EOL +
                "1. See all of your details." + EOL +
                "2. Change telephone number." + EOL +
                "3. Change e-mail." + EOL +
                "4. Change password." + EOL +
                "5. Change PIN-code." + EOL);

    }

    public static void employeeMenu(){
        System.out.println( EOL + "0. Log out." + EOL +
                "1. Review KYCs" + EOL +
                "2. Customer Support" + EOL +
                "3. View all customers."+ EOL);

    }

    public static void accountsMenu(){
        System.out.println( EOL + "0. Return to menu." + EOL +
                "1. Pay, Deposit or Transfer." + EOL+
                "2. View Accounts History." + EOL);
    }

    public static void KYCMenu() {
        System.out.println( EOL + "0. Return to menu." + EOL +
                "1. Fill in KYC." + EOL +
                "2. View KYC." + EOL);
    }

    public static void loanMenu() {
        System.out.println( EOL + "Please choose one of the following options:" + EOL +
                "0. Return to menu." + EOL +
                "1. My loan." + EOL +
                "2. Apply for a new loan." + EOL);
    }

    public static void myLoanMenu() {
        System.out.println( EOL + "Please choose one of the following options:" + EOL +
                "0. Return to menu. " + EOL +
                "1. View loan. " + EOL +
                "2. Pay loan. " + EOL);
    }

    public static void payTransferMenu(){
        System.out.println( EOL + "0. Return to the previous menu." + EOL +
                "1. Deposit funds." + EOL +
                "2. Transfers between accounts." + EOL +
                "3. Transfer funds or make payment." + EOL +
                "4. View saved recipients." + EOL +
                "5. View Transactions History." + EOL);
    }

    public static void  transactionHistoryMenu(){
        System.out.println(EOL + "0. Return to the previous menu." + EOL +
                "1. View all transactions." + EOL +
                "2. View deposits." + EOL +
                "3. View withdrawals." + EOL +
                "4. View total deposits for a period. (coming in V2)" + EOL +
                "5. View total withdrawals for a period. (coming in V2)" + EOL);
    }

    public static void employeeKYCMenu() {
        System.out.println( EOL +
                "0. Return to Employee Menu." + EOL +
                "1. Review KYC." + EOL +
                "2. View number of approved KYC's." + EOL);
    }

    public static void customerSupportMenu() {
        System.out.println("0. Return to menu." + EOL +
                "1. Write a message to Customer Support." + EOL +
                "2. Read latest message." + EOL);
    }

    public static void employeeSupportMenu() {
        System.out.println("0. Return to menu." + EOL +
                "1. Write message to a customer." + EOL +
                "2. Read latest message." + EOL );
    }

    public static void invalidEntry() {
        System.out.println("\u001B[31m" + "Invalid menu option. Please type another option." + "\u001B[0m" + EOL);
    }
}
