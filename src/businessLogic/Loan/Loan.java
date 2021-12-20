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

    public String getPersonalNumber() {
        return personalNumber;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public int getNumOfYears() {
        return numOfYears;
    }



}
