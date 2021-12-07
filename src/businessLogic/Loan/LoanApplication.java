package businessLogic.Loan;

import java.util.Date;

public class LoanApplication {
    private String personalNumber;
    private double monthlyIncome;
    private double currentLoanDebt;
    private double currentCreditDebt;
    private double appliedLoanAmount;
    private double appliedLoanDuration;
    private java.util.Date loanApplicationDate;

    public LoanApplication(String personalNumber, double monthlyIncome, double currentLoanDebt, double currentCreditDebt, double appliedLoanAmount, double appliedLoanDuration, Date loanApplicationDate) {
        this.personalNumber = personalNumber;
        this.monthlyIncome = monthlyIncome;
        this.currentLoanDebt = currentLoanDebt;
        this.currentCreditDebt = currentCreditDebt;
        this.appliedLoanAmount = appliedLoanAmount;
        this.appliedLoanDuration = appliedLoanDuration;
        this.loanApplicationDate = loanApplicationDate;
    }

    public boolean isLoanAppValid(String personalNumber, double monthlyIncome, double currentLoanDebt, double currentCreditDebt, double appliedLoanAmount, double appliedLoanDuration, Date loanApplicationDate) {
        if((personalNumber.isEmpty())) {
            System.out.println("Please enter your personal number");
            return false;
        }
        if(monthlyIncome.isEmpty()) {
            System.out.println("Please enter your monthly income");
            return false;
        }
        if(unitPrice <= 0){
            System.out.println("Invalid data for item." + " (Wrong price)");
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "LoanApplication{" +
                "personalNumber='" + personalNumber + '\'' +
                ", monthlyIncome=" + monthlyIncome +
                ", currentLoanDebt=" + currentLoanDebt +
                ", currentCreditDebt=" + currentCreditDebt +
                ", appliedLoanAmount=" + appliedLoanAmount +
                ", appliedLoanDuration=" + appliedLoanDuration +
                ", loanApplicationDate=" + loanApplicationDate +
                '}';
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public double getCurrentLoanDebt() {
        return currentLoanDebt;
    }

    public void setCurrentLoanDebt(double currentLoanDebt) {
        this.currentLoanDebt = currentLoanDebt;
    }

    public double getCurrentCreditDebt() {
        return currentCreditDebt;
    }

    public void setCurrentCreditDebt(double currentCreditDebt) {
        this.currentCreditDebt = currentCreditDebt;
    }

    public double getAppliedLoanAmount() {
        return appliedLoanAmount;
    }

    public void setAppliedLoanAmount(double appliedLoanAmount) {
        this.appliedLoanAmount = appliedLoanAmount;
    }

    public double getAppliedLoanDuration() {
        return appliedLoanDuration;
    }

    public void setAppliedLoanDuration(double appliedLoanDuration) {
        this.appliedLoanDuration = appliedLoanDuration;
    }

    public Date getLoanApplicationDate() {
        return loanApplicationDate;
    }

    public void setLoanApplicationDate(Date loanApplicationDate) {
        this.loanApplicationDate = loanApplicationDate;
    }
}
