package businessLogic.Loan;

import java.util.Date;

public class Loan {
    private String personalNumber;
    private double loanAmount;
    private double yearlyInterestRate;
    private int years;
    private String loanID;
    private boolean approvedLoan;
    private java.util.Date loanDate;
    private double loanDebt;

    public Loan(double yearlyInterestRate, int years, double loanAmount, String loanID, Date loanDate, boolean approvedLoan, double loanDebt) {
        this.yearlyInterestRate = yearlyInterestRate;
        this.years = years;
        this.loanAmount = loanAmount;
        this.loanID = loanID;
        this.loanDate = loanDate;
        this.approvedLoan = approvedLoan;
        this.loanDebt = 0;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "customerID='" + personalNumber + '\'' +
                ", loanAmount=" + loanAmount +
                ", yearlyInterestRate=" + yearlyInterestRate +
                ", years=" + years +
                ", loanID='" + loanID + '\'' +
                ", approvedLoan=" + approvedLoan +
                ", loanDate=" + loanDate +
                '}';
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

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getLoanID() {
        return loanID;
    }

    public void setLoanID(String loanID) {
        this.loanID = loanID;
    }

    public boolean isApprovedLoan() {
        return approvedLoan;
    }

    public void setApprovedLoan(boolean approvedLoan) {
        this.approvedLoan = approvedLoan;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public double getLoanDebt() {
        return loanDebt;
    }

    public void setLoanDebt(double loanDebt) {
        this.loanDebt = loanDebt;
    }
}
