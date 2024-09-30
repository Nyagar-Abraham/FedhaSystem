import java.util.ArrayList;
import java.util.List;

public class Member {
    private String name;
    private int age;
    private double registrationFee;
    private double totalShares;
    private List<Loan> loans;
    private List<Loan> guaranteedLoan;

    //constructor for member
    public Member(String name, double registrationFee,int age){
        this.name = name;
        this.age = age;
        this.registrationFee = registrationFee;
        this.totalShares = 0.0;
        this.loans = new  ArrayList<>();
        this.guaranteedLoan = new ArrayList<>();
    }

    public String getName() {
        return name;
    }


    //method for contributing shares
    public void contributeShares(double share){
        this.totalShares += share;
    }

    //get totalShares
    public double getTotalShares() {
        return this.totalShares;
    }

    //add a member a loan
    public void applyForLoan(Loan loan) {
        this.loans.add(loan);
    }

    //add a guaranteed Loan to a members list
    public void guaranteeLoan(Loan loan) {
        this.guaranteedLoan.add(loan);
    }

    //get the list of loans from member
    public List<Loan> getLoans() {
        return this.loans;
    }


}
