import java.util.ArrayList;
import java.util.List;

public class Loan {
    private String loanType;
    private double amount;
    private double interestRate;
    private int repaymentPeriod;
    private Member borrower;
    private List<Member> guarantors;

   //constructor
    public Loan(String loanType, double amount, double interestRate, int repaymentPeriod, Member borrower) {
        this.loanType = loanType;
        this.amount = amount;
        this.interestRate = interestRate;
        this.repaymentPeriod = repaymentPeriod;
        this.borrower = borrower;
        this.guarantors = new ArrayList<>();
    }

   // add guarantor to the loan
    public void addGuarantor(Member guarantor) {
        this.guarantors.add(guarantor);
    }

    //get the monthly repayment for the loan based on interest rate and repayment period
    public double calculateMonthlyRepayment() {
        double monthlyInterestRate = interestRate / 12;
        return (amount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -repaymentPeriod));
    }

    //get loan amount
    public double getAmount() {
        return this.amount;
    }
}
