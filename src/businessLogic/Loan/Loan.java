package businessLogic.Loan;

import Utility.Utilities;

import java.util.Date;

public class Loan {
    private String personalNumber;
    private double loanAmount;
    private double yearlyInterestRate;
    private int numOfYears;
    private java.util.Date date;

    public static final String EOL = System.lineSeparator();


    public Loan(String personalNumber, double yearlyInterestRate, int numOfYears, double loanAmount, Date date) {
        this.personalNumber =personalNumber;
        this.yearlyInterestRate = yearlyInterestRate;
        this.numOfYears = numOfYears;
        this.loanAmount = loanAmount;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Loan:" + EOL +
                "--------------------------" + EOL +
                "Loan amount: " + Utilities.truncate(loanAmount) + " SEK" + EOL +
                "Yearly interest rate: " + yearlyInterestRate + " %" + EOL +
                "Duration: " + numOfYears + " years" + EOL +
                "Start date: " + Utilities.simpleDateFormat(date);
    }

    public double getMonthlyPayment() {
        double monthlyInterestRate = yearlyInterestRate / 1200;
        return loanAmount * monthlyInterestRate / (1 -
                (Math.pow(1 / (1 + monthlyInterestRate), numOfYears * 12)));
    }




    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String customerID) {
        this.personalNumber = customerID;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getYearlyInterestRate() {
        return yearlyInterestRate;
    }

    public void setYearlyInterestRate(double yearlyInterestRate) {
        this.yearlyInterestRate = yearlyInterestRate;
    }

    public int getNumOfYears() {
        return numOfYears;
    }

    public void setNumOfYears(int numOfYears) {
        this.numOfYears = numOfYears;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
