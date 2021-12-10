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
        System.out.println("You are now logged in!" +EOL +"Please choose among the options below." + EOL +
                "0. Log out" + EOL +
                "1. Payments & Transfers" + EOL +
                "2. Bank accounts" + EOL +
                "3. Loans" + EOL +
                "4. Mortgages" + EOL +
                "5. KYC" + EOL +
                "6. Customer profile" + EOL +
                "7. Customer support" + EOL);

    }

    public static void customerProfileMenu(){
        System.out.println("Please choose among the options below." + EOL +
                "0. Go back to My page." + EOL +
                "1. See all of your details." + EOL +
                "2. Change telephone number." + EOL +
                "3. Change e-mail." + EOL +
                "4. Change password." + EOL +
                "5. Change PIN-code." + EOL);

    }
    public static void employeeMenu(){
        System.out.println("You are now logged in!" + EOL +
                "Please choose one of the following options:" + EOL +
                "0. Log out." + EOL +
                "1. Review loans." + EOL +
                "2. Review mortgages." + EOL +
                "3. Review KYCs" + EOL +
                "4. Customer Support" + EOL +
                "5. View all customers."+ EOL +
                "6. Remove customer." + EOL);
    }
    public static void accountMenu(){
        System.out.println("Please choose one of the following options:" + EOL +
                "0. Return to menu." + EOL +
                "1. Open account." + EOL +
                "2. Close account." + EOL +
                "3. Deposit funds." + EOL +
                "4. Withdraw funds." + EOL +
                "5. Transfer." + EOL +
                "6. Check balance." + EOL);
    }
    public static void KYCMenu() {
        System.out.println("Please choose one of the following options:" + EOL +
                "0. Return to menu." + EOL +
                "1. Fill in KYC." + EOL +
                "2. View KYC." + EOL);
    }
    public static void loanMenu() {
        System.out.println("Please choose one of the following options:" + EOL +
                "0. Return to menu." + EOL +
                "1. View loan." + EOL +
                "2. Apply for a new loan." + EOL +
                "3. Increase current loan." + EOL);
    }

    public static void PayTransferMenu(){
        System.out.println("Please choose one of the following options:" + EOL +
                "0. Return to menu." + EOL +
                "1. Transfers between accounts." + EOL +
                "2. Transfer funds or make payment." + EOL +
                "3. View saved transactions." + EOL +
                "4. View all transactions." + EOL +
                "5. View deposits." + EOL +
                "6. View withdrawals." + EOL +
                "7. View total deposits for a period." + EOL +
                "8. View total withdrawals for a period." + EOL);
    }

    public static void employeeKYCMenu() {
        System.out.println("Please choose one of the following options:" + EOL +
                "0. Return to Employee Menu." + EOL +
                "1. Review unapproved KYC." + EOL +
                "2. View number of KYCs pending review." + EOL +
                "3. View number of approved KYC's." + EOL);
    }


    public static void invalidEntry() {
        System.out.println("Invalid menu option. Please type another option." + EOL);
    }
}
