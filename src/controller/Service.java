package controller;

import businessLogic.Loan.Loan;
import businessLogic.Loan.LoanApplication;
import businessLogic.Transactions.Deposit;
import businessLogic.Transactions.Transaction;
import businessLogic.Transactions.Withdrawal;
import businessLogic.User.Employee;
import businessLogic.User.KYC;
import businessLogic.User.Customer;
import businessLogic.bankAccounts.CheckingAccount;
import businessLogic.bankAccounts.SavingsAccount;

import java.util.*;


public class Service {

    public static final String EOL = System.lineSeparator();

    private List<Customer> customerList;
    private List<KYC> reviewKYCList;
    private List<Transaction> transactions;
    private List<Transaction> savedRecipients;
    private List<KYC> approvedKYCList;
    private List<Loan> loanList;
    private List<LoanApplication> loanApplicationList;
    private Employee employee;
    private List<SavingsAccount> savingsAccounts;
    private List<CheckingAccount> checkingAccounts;
    // private Account loggedInAccount;

    public Service() {
        customerList = new ArrayList<>();
        reviewKYCList = new ArrayList<>();
        transactions = new ArrayList<>();
        savedRecipients = new ArrayList<>();
        approvedKYCList = new ArrayList<>();
        loanList = new ArrayList<>();
        loanApplicationList = new ArrayList<>();
        this.employee = new Employee("admin", "admin");
        this.savingsAccounts =  new ArrayList<>();
        this.checkingAccounts= new ArrayList<>();
    }

    public String createCustomer(String personalNumber, String firstName, String lastName, String email,
                                 String telephone, String password, String pinCode) {

        Customer customer = new Customer(personalNumber, firstName, lastName, email, telephone, password, pinCode);
        customerList.add(customer);

        return System.lineSeparator() + "You have now been registered!" + System.lineSeparator();
    }

    public void createKYC(String personalNumber, String occupation, double salary, boolean pep, boolean fatca, boolean approved) {
        KYC kyc = new KYC(personalNumber, occupation, salary, pep, fatca);
        reviewKYCList.add(kyc);
    }

    //todo Adrian
    public String verifyCustomerID(String personalNumber, String password) {
        return "";
    }
/*
    public int getCustomerIndex(String personalNumber) {
        for (int i = 0; i < this.accountsList.size(); i++) {
            if (this.accountsList.get(i).verifyAccountNumber(personalNumber)) {
                return i;
            }
        }
        return -1;
    }

 */

    public KYC findKYC(Customer customer) {
        if (reviewKYCList.size() > 0) {
            for (KYC kyc : reviewKYCList) {
                if (customer.getPersonalNumber().equals(kyc.getPersonalNumber())) {
                    return kyc;
                }
            }
        }
        if (approvedKYCList.size() > 0) {
            for (KYC kyc : approvedKYCList) {
                if (customer.getPersonalNumber().equals(kyc.getPersonalNumber())) {
                    return kyc;
                }
            }
        }
        return null;
    }

    public boolean pendingKYC(Customer customer) {
        for (KYC kyc : reviewKYCList) {
            if (customer.getPersonalNumber().equals(kyc.getPersonalNumber())) {
                return true;
            }
        }
        return false;
    }

    public String customerDisplayKYC(KYC kyc) {
        String pepStatus = "";
        String fatcaStatus = "";
        if (kyc.isPep()) {
            pepStatus = "Yes";
        } else {
            pepStatus = "No";
        }
        if (kyc.isFatca()) {
            fatcaStatus = "Yes";
        } else {
            fatcaStatus = "No";
        }

        String result =
                "Occupation: " + kyc.getOccupation() + System.lineSeparator() +
                        "Salary: " + kyc.getSalary() + System.lineSeparator() +
                        "Politically exposed customer: " + pepStatus + System.lineSeparator() +
                        "Affected by FATCA: " + fatcaStatus + System.lineSeparator();
        return result;
    }

    public String viewKYC(Customer customer) {
        if (findKYC(customer) == null) {
            return "No KYC registered yet.";
        } else if (approvedKYC(customer)) {
            KYC customersKYC = findKYC(customer);
            return "Status: Approved." + EOL + customerDisplayKYC(customersKYC);
        } else if (pendingKYC(customer)) {
            KYC customersKYC = findKYC(customer);
            return "Status: Under review. " + EOL + customerDisplayKYC(customersKYC);
        }
        return "";
    }

    public boolean emptyReviewList() {
        return reviewKYCList.isEmpty();
    }

    public String KYCToBeReviewed() {
        if (reviewKYCList.isEmpty()) {
            return "There are currently no KYC's to review.";
        }
        KYC unapprovedKYC = findUnapprovedKYC();
        if (unapprovedKYC == null) {
            return "No KYCs to review";
        }
        return employeeDisplayKYC(unapprovedKYC);
    }

    public String employeeDisplayKYC(KYC kyc) {
        String pepStatus = "";
        String fatcaStatus = "";
        if (kyc.isPep()) {
            pepStatus = "Yes";
        } else {
            pepStatus = "No";
        }
        if (kyc.isFatca()) {
            fatcaStatus = "Yes";
        } else {
            fatcaStatus = "No";
        }
        String result = "Personalnumber: " + kyc.getPersonalNumber() + System.lineSeparator() +
                "Occupation: " + kyc.getOccupation() + System.lineSeparator() +
                "Salary: " + kyc.getSalary() + System.lineSeparator() +
                "Politically exposed customer: " + pepStatus + System.lineSeparator() +
                "Affected by FATCA: " + fatcaStatus + System.lineSeparator();
        return result;
    }

    public String registerKYC(Customer customer, String occupation, double salary, String pepQuestion, String fatcaQuestion) {
        boolean pep = false;
        boolean fatca = false;
        if (pendingKYC(customer)) {
            return "KYC has already been filled in.";
        }

        if (salary < 0) {
            return "Salary cannot be lower than zero. Please try again.";
        }
        if (occupation.isBlank()) {
            return "You need to fill in your occupation. Please try again.";
        }
        if (pepQuestion.trim().toLowerCase(Locale.ROOT).equals("yes")) {
            pep = true;
        } else if (pepQuestion.trim().toLowerCase(Locale.ROOT).equals("no")) {
            pep = false;
        } else {
            return "Please input either Yes or No.";
        }
        if (fatcaQuestion.trim().toLowerCase(Locale.ROOT).equals("yes")) {
            fatca = true;
        } else if (fatcaQuestion.trim().toLowerCase(Locale.ROOT).equals("no")) {
            fatca = false;
        } else {
            return "Please write either Yes or No";
        }
        KYC kyc = new KYC(customer.getPersonalNumber(), occupation, salary, pep, fatca);
        reviewKYCList.add(kyc);
        return System.lineSeparator() + "KYC awaiting review." + System.lineSeparator();
    }

    public boolean approvedKYC(Customer customer) {
        for (KYC approvedKYC : approvedKYCList) {
            if (customer.getPersonalNumber().equals(approvedKYC.getPersonalNumber())) {
                return true;
            }
        }
        return false;
    }

    public boolean onlyDigits(String personalNumber) {
        for (int i = 0; i < personalNumber.length(); i++) {
            if (!Character.isDigit(personalNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean onlyDigitsName(String firstName) {
        for (int i = 0; i < firstName.length(); i++) {
            if (!Character.isDigit(firstName.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean onlyDigitsLastName(String lastName) {
        for (int i = 0; i < lastName.length(); i++) {
            if (!Character.isDigit(lastName.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean onlyDigitsT(String telephoneNumber) {
        for (int i = 0; i < telephoneNumber.length(); i++) {
            if (!Character.isDigit(telephoneNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean onlyDigitsP(String pinCode) {
        for (int i = 0; i < pinCode.length(); i++) {
            if (!Character.isDigit(pinCode.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean onlyDigitsPass(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isDigit(password.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public String reviewUnapprovedKYC(String review) {
        KYC unapprovedKYC = findUnapprovedKYC();
        String result = "";
        if (review.trim().toLowerCase(Locale.ROOT).equals("yes")) {
            approvedKYCList.add(unapprovedKYC);
            OpenAccounts(unapprovedKYC.getPersonalNumber());
            reviewKYCList.remove(unapprovedKYC);
            result = "Customers KYC has been approved.";
        } else if (review.trim().toLowerCase(Locale.ROOT).equals("no")) {
            reviewKYCList.remove(unapprovedKYC);
            result = "Customers KYC has been declined.";
        } else {
            result = "Please input either Yes or No.";
        }
        return result + System.lineSeparator();
    }

    public void OpenAccounts(String customerPersonalNumber) {
        Customer currentUser = findCustomer(customerPersonalNumber);
        CheckingAccount cH = new CheckingAccount(customerPersonalNumber);
        SavingsAccount sA = new SavingsAccount(customerPersonalNumber);
        try {
            currentUser.addCheckingList(cH);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            currentUser.addSavingsList(sA);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        checkingAccounts.add(cH);
        savingsAccounts.add(sA);
    }

    public String showUnapprovedKYC() {
        KYC unapprovedKYC = findUnapprovedKYC();
        if (unapprovedKYC != null) {
            return employeeDisplayKYC(unapprovedKYC);
        } else {
            return "No KYC registered for this customer." + System.lineSeparator();
        }
    }

    public String numberOfApprovedKYCs() {
        String result = "";
        if (approvedKYCList.isEmpty()) {
            result = "There are currently no approved KYCs." + System.lineSeparator();
        } else {
            result = "The number of approved KYC's is: " + approvedKYCList.size();
        }
        return result + System.lineSeparator();
    }

    public String numberOfUnapprovedKYCs() {
        String result = "";
        if (reviewKYCList.isEmpty()) {
            result = ""; // Returns an empty string as option one displays information if there are no KYCs to review
        } else {
            result = "-----------------------------------" + EOL +
                    "The number of unapproved KYCs is: " + reviewKYCList.size();
        }
        return result;
    }

    public String printAllApprovedKYCs() {
        String allApprovedKYCs = "All approved KYCs:";
        for (KYC approvedKYC : approvedKYCList) {
            allApprovedKYCs = allApprovedKYCs + System.lineSeparator() + approvedKYC.toString();
        }
        return allApprovedKYCs;
    }

    public KYC findUnapprovedKYC() {
        for (KYC kyc : reviewKYCList) {
            return kyc;
        }
        return null;
    }

    public boolean containsCustomer(String personalNumber) {
        for (Customer customer : customerList) {
            if (customer.getPersonalNumber().equals(personalNumber)) {
                return true;
            }
        }
        return false;
    }

    /*
    public String deleteCustomer(String personalNumber) {

      if(customerList.isEmpty()){ Adrians version of verifyCustomer
            return "No customers created yet.";
        }
        for (Customer customer : customerList) {
            if(!customer.getPersonalNumber().equals(personalNumber) && !customer.getPassword().equals(password)){
                return "Wrong personal number or password.";
            }
        }

        return "Verified customer.";
    }
  */

    public String printAllCustomers() {
        String allCustomers = "All registered customers:";

        for (Customer customer : customerList) {
            allCustomers = allCustomers + System.lineSeparator() + customer.toString();
        }
        return allCustomers + System.lineSeparator();
    }

    public String editCustomerFirstName(String personalNumber, String newFirstName) {

        Customer nameToChange = null;
        for (Customer currentName : customerList) {
            if (currentName.getPersonalNumber().equals(personalNumber)) {
                if (newFirstName.isEmpty()) {
                    return "Invalid entry.";
                }
                nameToChange = currentName;
                currentName.setFirstName(newFirstName);
            }
        }
        if (nameToChange == null) {
            return personalNumber + " was not registered yet.";
        }
        return personalNumber + "'s " + " first name was edited successfully.";
    }

    public String editCustomerLastName(String personalNumber, String newLastName) {

        Customer lastNameToChange = null;

        for (Customer currentLastName : customerList) {
            if (currentLastName.getPersonalNumber().equals(personalNumber)) {
                if (newLastName.isEmpty() || newLastName.isBlank()) {
                    return "Invalid entry";
                }
                lastNameToChange = currentLastName;
                currentLastName.setLastName(newLastName);
            }
        }
        if (lastNameToChange == null) {
            return personalNumber + " was not registered yet.";
        }
        return personalNumber + "'s last name was edited successfully.";
    }

    public String editCustomerEmail(String personalNumber, String newEmail) {

        Customer emailToChange = null;
        for (Customer currentEmail : customerList) {
            if (currentEmail.getPersonalNumber().equals(personalNumber)) {
                if (newEmail.isEmpty() || newEmail.isBlank()) {
                    return "Invalid entry.";
                }
                emailToChange = currentEmail;
                currentEmail.setEmail(newEmail);
            }
            if (emailToChange == null) {

                return personalNumber + " was not registered yet.";
            }
        }
        return personalNumber + "'s" + " email was edited successfully.";
    }

    public String editCustomerTelephone(String personalNumber, String newTelephone) {
        String changedTelephone = "";
        Customer telephoneToChange = null;
        for (Customer currentPhone : customerList) {
            if (currentPhone.getPersonalNumber().equals(personalNumber)) {
                if (newTelephone.isEmpty() || newTelephone.isBlank() || !onlyDigitsT(newTelephone) || newTelephone.length() < 9 || newTelephone.length() > 13) {
                    return "Invalid entry.";
                }
                telephoneToChange = currentPhone;
                currentPhone.setTelephone(newTelephone);
                changedTelephone = personalNumber + "'s" + " telephone number was edited successfully.";
            }
            if (telephoneToChange == null) {

                return personalNumber + " was not registered yet.";
            }
        }
        return changedTelephone;
    }

    public boolean employeeLoginCheck(String username, String password) {

        return username.equals("admin") && password.equals("admin");
    }

    public String editCustomerPassword(String personalNumber, String newPassword) {
        Customer passwordToChange = null;
        String dosomething = "";
        for (Customer customer : customerList) {
            if (customer.getPersonalNumber().equals(personalNumber)) {
                if (newPassword.isEmpty() || newPassword.isBlank() || !onlyDigitsPass(newPassword)) {
                    return "Invalid entry.";
                }
                passwordToChange = customer;
                customer.setPassword(newPassword);
                dosomething = personalNumber + "'s" + " password was edited successfully.";

            }
            if (passwordToChange == null) {

                return personalNumber + " was not registered yet.";
            }
        }
        return dosomething;
    }

    public String editCustomerPincode(String personalNumber, String newPincode) {
        Customer pinCodeToChange = null;
        for (Customer currentPinCode : customerList) {
            if (currentPinCode.getPersonalNumber().equals(personalNumber)) {
                if (newPincode.isEmpty() || newPincode.isBlank() || !onlyDigitsP(newPincode) || newPincode.length() != 4) {
                    return "Invalid entry.";
                }
                pinCodeToChange = currentPinCode;
                currentPinCode.setPinCode(newPincode);
            }
            if (pinCodeToChange == null) {

                return personalNumber + " was not registered yet.";
            }
        }
        return personalNumber + "'s" + " pin code was edited successfully.";
    }
    
    public String updateKYC(String occupation, double salary, String PEP, String FATCA) {
        return "";
    }

    // method for finding account object by Account Number

    public SavingsAccount getSavingsAccountByAccountNumber(String accountNumber) {
        for (SavingsAccount account : savingsAccounts) {
            if (accountNumber.equals(account.getAccountNumber())) {
                return account;
            }
        }
        return null;
    }

    public CheckingAccount getCheckingAccountByAccountNumber(String accountNumber) {
        for (CheckingAccount account : checkingAccounts) {
            if (accountNumber.equals(account.getAccountNumber())) {
                return account;
            }
        }
        return null;
    }


    public String deposit(String toAccount, double amount) throws Exception {
        SavingsAccount sa = getSavingsAccountByAccountNumber(toAccount);
        CheckingAccount cha = getCheckingAccountByAccountNumber(toAccount);
        if (sa == null && cha == null) {
            throw new Exception("Account doesn't exist.");
        }
        if (amount < 0) {
            throw new Exception("Amount should be greater than 0.");
        } else if(sa != null) {
            sa.addToUpdateBalance(amount);
            Deposit deposit = new Deposit(amount, toAccount);
            transactions.add(deposit);
            sa.addTransaction(deposit);
            return "\u001B[32m" + sa.getType() + " balance was updated successfully!" + EOL +
                    "Current balance is: " + sa.getBalance() + " SEK." + " \u001B[0m";
        } else if(cha != null ) {
            cha.addToUpdateBalance(amount);
            Deposit deposit = new Deposit(amount, toAccount);
            transactions.add(deposit);
            cha.addTransaction(deposit);
            return "\u001B[32m" + cha.getType() + " balance was updated successfully!" + EOL +
                    "Current balance is: " + cha.getBalance() + " SEK." + " \u001B[0m";
        }
        return  "";
    }

    public String payTransfer(String fromAccountNumber, String toAccountNumber, double amount, String note) throws Exception {
        SavingsAccount sa = getSavingsAccountByAccountNumber(fromAccountNumber);
        CheckingAccount cha = getCheckingAccountByAccountNumber(fromAccountNumber);
        if (sa == null && cha == null) {
            throw new Exception("Account doesn't exist.");
        }
        if (toAccountNumber.length() != 6) {
            throw new Exception("Account number should be 6 characters.");
        }
        if (amount <= 0) {
            throw new Exception("Amount should be greater than 0.");
        }
        if(sa != null) {
            if (amount > sa.getBalance()) {
                throw new Exception("Not enough funds on account #" + sa.getAccountNumber());
            } else {
                sa.subtractToUpdateBalance(amount);
                Withdrawal withdrawal = new Withdrawal(amount, fromAccountNumber, toAccountNumber, note);
                transactions.add(withdrawal);
                sa.addTransaction(withdrawal);
                if (getCheckingAccountByAccountNumber(toAccountNumber) != null) {
                    deposit(toAccountNumber, amount);
                }

                return "\u001B[32m" + "Transfer successful!" + EOL +
                        sa.getType() + " #" + fromAccountNumber + " Current Balance: " + sa.getBalance() + " SEK." + " \u001B[0m" + EOL;
            }
        }

        if(cha != null) {
            if (amount > cha.getBalance()) {
                throw new Exception("Not enough funds on account #" + sa.getAccountNumber());
            } else {
                cha.subtractToUpdateBalance(amount);
                Withdrawal withdrawal = new Withdrawal(amount, fromAccountNumber, toAccountNumber, note);
                transactions.add(withdrawal);
                cha.addTransaction(withdrawal);
                if (getSavingsAccountByAccountNumber(toAccountNumber) != null) {
                    deposit(toAccountNumber, amount);
                }

                return "\u001B[32m" + "Transfer successful!" + EOL +
                        cha.getType() + " #" + fromAccountNumber + " Current Balance: " + cha.getBalance() + " SEK." + " \u001B[0m" + EOL;

            }
        }

        return  "";
    }

    public String saveRecipient(Customer currentUser, String fromAccount, String toAccountNumber, String note, String name) {

        Withdrawal withdrawal = new Withdrawal(0.0, fromAccount, toAccountNumber, note, name);
        currentUser.addRecipient(withdrawal);
        return "\u001B[32m" + "Saved!" + " \u001B[0m";
    }

    public String withdraw(String fromAccount, double amount) throws Exception {
        SavingsAccount sa = getSavingsAccountByAccountNumber(fromAccount);
        CheckingAccount cha = getCheckingAccountByAccountNumber(fromAccount);
        if (sa == null && cha == null) {
            throw new Exception("Account doesn't exist.");
        }
        if (amount <= 0) {
            throw new Exception("Amount should be greater than 0.");
        }
        if(sa != null) {
            if (amount > sa.getBalance()) {
                throw new Exception("Not enough funds to withdraw from account " + sa.getAccountNumber());
            } else {
                Withdrawal withdrawal = new Withdrawal(amount, fromAccount);
                transactions.add(withdrawal);
                sa.addTransaction(withdrawal);
                sa.subtractToUpdateBalance(amount);
                return "\u001B[32m" + sa.getType() + " balance was updated successfully." + EOL +
                        "Current balance is: " + sa.getBalance() + " SEK." + " \u001B[0m";
            }
        }
        if(cha != null) {
            if (amount > cha.getBalance()) {
                throw new Exception("Not enough funds to withdraw from account " + sa.getAccountNumber());
            } else {
                Withdrawal withdrawal = new Withdrawal(amount, fromAccount);
                transactions.add(withdrawal);
                cha.addTransaction(withdrawal);
                cha.subtractToUpdateBalance(amount);
                return "\u001B[32m" + cha.getType() + " balance was updated successfully." + EOL +
                        "Current balance is: " + cha.getBalance() + " SEK." + " \u001B[0m";
            }
        }
        return "";
    }

    public String transferFundsBetweenAccounts(double amount, String fromAccountNumber, String toAccountNumber) throws Exception {
        SavingsAccount sa = null;
        CheckingAccount cha = null;
        if (getCheckingAccountByAccountNumber(fromAccountNumber) != null) {
            cha = getCheckingAccountByAccountNumber(fromAccountNumber);
            sa = getSavingsAccountByAccountNumber(toAccountNumber);
        }

        if (getSavingsAccountByAccountNumber(fromAccountNumber) != null) {
            cha = getCheckingAccountByAccountNumber(toAccountNumber);
            sa = getSavingsAccountByAccountNumber(fromAccountNumber);
        }

        if (sa == null || cha == null) {
            return "Can't find account. Please check if the accounts' numbers are correct";
        } else if (checkBalance(fromAccountNumber) < amount) {
            return "Not enough funds.";
        } else {
            withdraw(fromAccountNumber, amount);
            deposit(toAccountNumber, amount);
            return "\u001B[32m" + "Transfer successful!" + EOL +
                    cha.getType() + " #" + cha.getAccountNumber() + " Current Balance: " + cha.getBalance() + " SEK." + EOL +
                    sa.getType() + " #" + sa.getAccountNumber() + " Current Balance: " + sa.getBalance() + " SEK." + " \u001B[0m";
        }
    }

    public String printAccountsAndBalance(Customer currentUser) {
        List<CheckingAccount> checkingAccounts = currentUser.getCheckingList();
        List<SavingsAccount> savingsAccounts = currentUser.getSavingsList();

        String checkingAccountOutput = "";
        String savingsAccountOutput = "";
        if (savingsAccounts == null && checkingAccounts == null) {
            return "No accounts open yet.";
        } else {
            for (CheckingAccount chAccount : checkingAccounts) {
                checkingAccountOutput = chAccount.toString();
            }
            for (SavingsAccount sAccount : savingsAccounts) {
                savingsAccountOutput = sAccount.toString();
            }
        }
        return checkingAccountOutput + EOL +
                "------------------------------------- " + EOL +
                savingsAccountOutput + EOL +
                "------------------------------------- ";
    }

    //todo Adrian changed
    public String printAccounts(Customer currentUser) {
        List<CheckingAccount> checkingAccounts = currentUser.getCheckingList();
        List<SavingsAccount> savingsAccounts = currentUser.getSavingsList();
        String operationResult = "---------------------------------------" + EOL +
                "0. Return to the previous menu." + EOL;
        String checkingAccountOutput = "";
        String savingsAccountOutput = "";

        if (currentUser.getCheckingList() == null && currentUser.getSavingsList() == null) {
            return "No accounts open yet.";
        } else {
            for (CheckingAccount chAccount : checkingAccounts) {
                checkingAccountOutput = chAccount.toString();
            }
            for (SavingsAccount sAccount : savingsAccounts) {
                savingsAccountOutput = sAccount.toString();
            }
        }
        operationResult += "1. " + checkingAccountOutput + EOL +
                "2. " + savingsAccountOutput + EOL;
        return operationResult;
    }

    public String chooseSecondAccount(Customer currentUser, String fromAccount) {
        List<CheckingAccount> checkingAccounts = currentUser.getCheckingList();
        List<SavingsAccount> savingsAccounts = currentUser.getSavingsList();
        for (CheckingAccount chAccount : checkingAccounts) {
            if (!chAccount.getAccountNumber().equals(fromAccount)) {
                return chAccount.getAccountNumber();
            }
        }
        for (SavingsAccount sAccount : savingsAccounts) {
            if (!sAccount.getAccountNumber().equals(fromAccount)) {
                return sAccount.getAccountNumber();
            }
        }

        return "";
    }

    public String printAllTransactions(List<Transaction> transactions) {
        int index = 0;
        String operationResult = "----------------------------------" + EOL +
                "All transactions:" + EOL;
        for (Transaction tx : transactions) {
            index++;
            operationResult += index + ". " + tx.toString() ;
        }
        if (index == 0) {
            operationResult = "No transactions so far.";
        }
        return operationResult + "----------------------------------";
    }

    public String printAllRecipients(List<Transaction> transactions) {
        int index = 0;
        String operationResult = "";
        for (Transaction tx : transactions) {
            index++;
            operationResult += index + ". " + tx.toString();
        }
        if (index == 0) {
            operationResult = "No recipients has been saved so far.";
        }
        return operationResult;
    }

    public String printAllWithdrawals(List<Transaction> transactions) {
        int index = 0;
        String operationResult = "All withdrawals:" + EOL;
        for (Transaction tx : transactions) {
            if (tx instanceof Withdrawal) {
                index++;
                operationResult += index + ". " + tx;
            }
        }
        if (index == 0) {
            operationResult = "No withdrawals so far.";
        }
        return operationResult;
    }

    public String printAllDeposits(List<Transaction> transactions) {
        int index = 0;
        String operationResult = "All deposits:" + EOL;
        for (Transaction tx : transactions) {
            if (tx instanceof Deposit) {
                index++;
                operationResult += index + ". " + tx;
            }

        }
        if (index == 0) {
            operationResult = "No deposits so far.";
        }
        return operationResult;
    }

    public String getCheckingAccountNumber(Customer currentUser) {
        List<CheckingAccount> accounts = currentUser.getCheckingList();
        for (CheckingAccount account : accounts) {
                return account.getAccountNumber();
        }
        return "";
    }

    public String getSavingsAccountNumber(Customer currentUser) {
        List<SavingsAccount> accounts = currentUser.getSavingsList();
        for (SavingsAccount account : accounts) {
                return account.getAccountNumber();
        }
        return "";
    }
    // Adding monthly profit to all saving account's list
    public void addProfitToSavings(){
        for (SavingsAccount account : savingsAccounts){
            account.addMonthlyInterest();
        }
    }

    public double checkBalance(String accountNumber) {
        if(getSavingsAccountByAccountNumber(accountNumber) != null) {
            return getSavingsAccountByAccountNumber(accountNumber).getBalance();
        } else {
           return getCheckingAccountByAccountNumber(accountNumber).getBalance();
        }
    }

    public boolean checkPinCode(String typedPinCode, Customer currentUser) throws Exception {
        if(!currentUser.getPinCode().equals(typedPinCode)) {
            throw new Exception("\u001B[31m" + "Incorrect PIN-code => Operation rejected." + "\u001B[0m");
        }
        return currentUser.getPinCode().equals(typedPinCode);
    }



    /**
     * WHERE LOAN BEGIN:
     * <p>
     * ╭━┳━╭━╭━╮╮
     * ┃┈┈┈┣▅╋▅┫┃
     * ┃┈┃┈╰━╰━━━━━━╮
     * ╰┳╯┈┈┈┈┈┈┈┈┈◢▉◣
     * ╲┃┈┈┈┈┈┈┈┈┈┈▉▉▉
     * ╲┃┈┈┈┈┈┈┈┈┈┈◥▉◤
     * ╲┃┈┈┈┈╭━┳━━━━╯
     * ╲┣━━━━━━┫
     */

    public int searchForLoanIndex(String personalNumber) {
        for (int i = 0; i < this.loanList.size(); i++) {
            if (this.loanList.get(i).getPersonalNumber().equals(personalNumber)) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkLoan(String personalNumber) {
        for (Loan loan : loanList) {
            if (loan.getPersonalNumber().equals(personalNumber)) {
                return true;
            }
        }
        return false;
    }

    //Customer is not allowed to have more than one current loan at the time.
    public String viewLoan(String personalNumber) {
        int index = searchForLoanIndex(personalNumber);
        if (index == -1) {
            return ("You currently do not have a loan with Eazy Banking." + EOL +
                    "If you want to apply for a loan:" + EOL +
                    "Go to: Loan menu - Apply for a new loan.");
        } else {
            return loanList.get(index).toString();
        }
    }
// Collects data from user input in Menu Class, to add to loan list for autoApproval:
    public String applyLoan(String personalNumber, double monthlyIncome, double currentLoanDebt, double currentCreditDebt, int appliedLoanAmount, int appliedLoanDuration) {
        LoanApplication loanApplication = new LoanApplication(personalNumber, monthlyIncome, currentLoanDebt, currentCreditDebt, appliedLoanAmount, appliedLoanDuration);
        loanApplicationList.add(loanApplication);
        return null;
    }

    // Collects from the loanApplication list,
// Depending on input value from customer, the loan will be auto approved depending on criteria listed below:
    public String autoApproval(Customer currentUser) {
        LoanApplication unapprovedLoan = findLoanApplication(currentUser);
        String personalNumber = unapprovedLoan.getPersonalNumber();
        double monthlyIncome = unapprovedLoan.getMonthlyIncome();
        double currentLoanDebt = unapprovedLoan.getCurrentLoanDebt();
        double currentCreditDebt = unapprovedLoan.getCurrentCreditDebt();
        double appliedLoanDuration = unapprovedLoan.getAppliedLoanDuration();
        if (monthlyIncome <= 10000 || currentLoanDebt >= 500000 || currentCreditDebt >= 500000 || appliedLoanDuration > 5) {
            return ("Loan application was declined, contact 24|7 Service for more information.");
        } else {
// Eazy Bank have a fixed yearly interest rate, set to 2,3%
            double yearlyInterestRate = 2.3;
//Could be set to fixed duration, if Employee wants.
            // int numOfYears = 5;
            int numOfYears = (int) unapprovedLoan.getAppliedLoanDuration();
            double loanAmount = unapprovedLoan.getAppliedLoanAmount();
            Date date = new Date();
            Loan loan = new Loan(personalNumber, yearlyInterestRate, numOfYears, loanAmount, date);
//Remove loan application from application list.
            loanApplicationList.remove(unapprovedLoan);
// "transforms" to a loan
            loanList.add(loan);
        }
        return "\u001B[32m" + "Your loan has been approved." + "\u001B[0m" + EOL
                + payOutLoan(currentUser) + EOL;
    }

    // Use deposit method for Transaction menu, to deposit approved loan amount to customers Savings account.
    public String payOutLoan(Customer currentUser) {
        String message = "";
        try {
            message = deposit(getSavingsAccountNumber(currentUser), getLoanAmount(currentUser));
        } catch (Exception e) {
            System.out.println(e);
        }
        return message;
    }

    // Monthly payment = loan amount x mr(1 + mr)^b / (1 + mr)^b – 1
    public double getMonthlyPayment(Customer currentUser) {
        Loan approvedLoan = findLoan(currentUser);
        double monthlyInterestRate = 2.3 / 1200;
        double monthlyPayment = approvedLoan.getLoanAmount() * monthlyInterestRate
                / (1 - (1 / Math.pow(1 + monthlyInterestRate, approvedLoan.getNumOfYears() * 12)));
        return monthlyPayment;
    }

    public double getLoanAmount(Customer currentUser) {
        Loan approvedLoan = findLoan(currentUser);
        return approvedLoan.getLoanAmount();
    }

    public LoanApplication findLoanApplication(Customer currentUser) {
        for (LoanApplication loanApplication : loanApplicationList) {
            if (loanApplication.getPersonalNumber().equals(currentUser.getPersonalNumber())) {
                return loanApplication;
            }
        }
        return null;
    }

    public Loan findLoan(Customer currentUser) {
        for (Loan loan : loanList) {
            if (loan.getPersonalNumber().equals(currentUser.getPersonalNumber())) {
                return loan;
            }
        }
        return null;
    }

    public String viewMessage(Customer currentUser) {
        return currentUser.viewMessage();
    }

    // Meddelanden behöver tas bort, både employee och customer
    //
    public void removeMessage(Customer currentUser) {
        currentUser.removeMessage();
    }

    public void removeMessage() {
        employee.removeMessage();
    }

    public String viewMessage() {
        return employee.viewMessage();
    }

    public String messageToCustomer(String personalNumber, String newMessage) {
        Customer foundCustomer = findCustomer(personalNumber);
        foundCustomer.addMessage(newMessage);
        return "\u001B[32m" + "Message sent." + "\u001B[0m";
    }

    public int numberOfMessages() {
        return employee.numberOfMessages();
    }

    public int numberOfMessages(Customer customer) {
        return customer.numberOfMessages();
    }

    public String messageToEmployee(Customer currentUser, String newMessage) {
        employee.addMessage(currentUser.getPersonalNumber() + System.lineSeparator() + newMessage);
        return "\u001B[32m" + "Message sent." + "\u001B[0m";
    }

    public String fetchPersonalNumber(String message) {
        return message.substring(0, 2000);
    }

    public boolean verifyEmployee(String userName, String pinCode) {
        if (employee.getEmployeeID().equals(userName.trim().toLowerCase(Locale.ROOT)) && employee.getPinCode().equals(pinCode.trim().toLowerCase(Locale.ROOT))) {
            return true;
        }
        return false;
    }

    //todo Faiza
    public String openNewAccount() {
        return "";
    }

    public String closeAccount(String accountNumber) {
        return "";
    }

    public Customer findCustomer(String personalNumber) {
        try {
            if (customerList.size() > 0) {
                for (Customer customer : customerList) {
                    if (customer.getPersonalNumber().equals(personalNumber)) {
                        return customer;
                    }
                }
            }
        } catch (Exception exception) {
            System.out.println("No customer found.");
        }
        return null;
    }

    public Customer getCustomerByPN(String pn) {
        for (Customer c : customerList) {
            if (c.getPersonalNumber().equals(pn)) {
                return c;
            }
        }
        return null;
    }

    public void addCustomerToList(Customer customer) {
        this.customerList.add(customer);
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }


    public List<KYC> getReviewKYCList() {
        return reviewKYCList;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Transaction> getSavedRecipients() {
        return savedRecipients;
    }

    public List<KYC> getApprovedKYCList() {
        return approvedKYCList;
    }

    public List<Loan> getLoanList() {
        return loanList;
    }

    public List<LoanApplication> getLoanApplicationList() {
        return loanApplicationList;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public void setReviewKYCList(List<KYC> reviewKYCList) {
        this.reviewKYCList = reviewKYCList;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setSavedRecipients(List<Transaction> savedRecipients) {
        this.savedRecipients = savedRecipients;
    }

    public void setApprovedKYCList(List<KYC> approvedKYCList) {
        this.approvedKYCList = approvedKYCList;
    }

    public void setLoanList(List<Loan> loanList) {
        this.loanList = loanList;
    }

    public void setLoanApplicationList(List<LoanApplication> loanApplicationList) {
        this.loanApplicationList = loanApplicationList;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void addSavingsAccounts(List<SavingsAccount> savingsList) {
        this.savingsAccounts.addAll(savingsList);
    }

    public void addCheckingAccounts(List<CheckingAccount> checkingList) {
        this.checkingAccounts.addAll(checkingList);
    }
}

