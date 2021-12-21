package businessLogic.Loan;


public class LoanApplication {
    private String personalNumber;
    private double monthlyIncome;
    private double currentLoanDebt;
    private double currentCreditDebt;
    private int appliedLoanAmount;
    private int appliedLoanDuration;

    public LoanApplication(String personalNumber, double monthlyIncome, double currentLoanDebt, double currentCreditDebt, int appliedLoanAmount, int appliedLoanDuration) {
        this.personalNumber = personalNumber;
        this.monthlyIncome = monthlyIncome;
        this.currentLoanDebt = currentLoanDebt;
        this.currentCreditDebt = currentCreditDebt;
        this.appliedLoanAmount = appliedLoanAmount;
        this.appliedLoanDuration = appliedLoanDuration;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public double getCurrentLoanDebt() {
        return currentLoanDebt;
    }

    public double getCurrentCreditDebt() {
        return currentCreditDebt;
    }

    public double getAppliedLoanAmount() {
        return appliedLoanAmount;
    }

    public double getAppliedLoanDuration() {
        return appliedLoanDuration;
    }

}
