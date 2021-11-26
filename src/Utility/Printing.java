package Utility;

public class Printing {

    static final String EOL = System.lineSeparator();

    public static void startPage() {
        System.out.println("Start page: Choose among the options below." + EOL +
                "0. Close System." + EOL +
                "1. Display My page." + EOL +
                "2. Display Admin page." + EOL +
                "3. Add customer." + EOL +
                "4. Add employee." + EOL +
                "5. No feature" + EOL);
    }
    public static void myPage(){
        System.out.println("My page: Choose among the options below." + EOL +
                "0. Return to start page." + EOL +
                "1. No feature yet" + EOL +
                "2. No feature yet" + EOL +
                "3. No feature yet" + EOL);
    }
    public static void adminPage(){
        System.out.println("Admin page: Choose among the options below." + EOL +
                "0. Return to start page." + EOL +
                "1. No feature yet." + EOL +
                "2. No feature yet." + EOL +
                "3. No feature yet." + EOL);
    }
    public static void myAccount(){
        System.out.println("My account: Choose among the options below." + EOL +
                "0. Return to start page." + EOL +
                "1. Deposit." + EOL +
                "2. Withdraw" + EOL +
                "3. Transfer" + EOL +
                "4. Make Payment" + EOL +
                "5. Transaction history" + EOL +
                "6. Check balance" + EOL);
    }

    public static void invalidEntry() {
        System.out.println("Invalid menu option. Please type another option." + EOL);
    }
}
