package businessLogic.Loan;

public class IncreaseLoan extends LoanApplication{
    public double loanDebt;

    public IncreaseLoan(String personalNumber, double monthlyIncome, double currentLoanDebt, double currentCreditDebt, int appliedLoanAmount, int appliedLoanDuration, double loanDebt) {
        super(personalNumber, monthlyIncome, currentLoanDebt, currentCreditDebt, appliedLoanAmount, appliedLoanDuration);
        this.loanDebt = getLoanDebt();
    }

    public double getLoanDebt() {
        return loanDebt;
    }

    public void setLoanDebt(double loanDebt) {
        this.loanDebt = loanDebt;
    }
}

