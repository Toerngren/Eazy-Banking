package businessLogic.Loan;

public class IncreaseLoan extends LoanApplication{
    public double eazyBankDebt;

    public IncreaseLoan(String personalNumber, double monthlyIncome, double currentLoanDebt, double currentCreditDebt, int appliedLoanAmount, int appliedLoanDuration, double eazyBankDebt) {
        super(personalNumber, monthlyIncome, currentLoanDebt, currentCreditDebt, appliedLoanAmount, appliedLoanDuration);
        this.eazyBankDebt = eazyBankDebt;
    }

    public double getEazyBankDebt() {

        return eazyBankDebt;
    }

    public void setEazyBankDebt(double eazyBankDebt) {
        this.eazyBankDebt = eazyBankDebt;
    }
}

