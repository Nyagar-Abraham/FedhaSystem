import java.util.ArrayList;
import java.util.List;

public class FedhaSystem {
    private List<Member> members;
    private double fixedDeposits;
    private double totalRevenue;

    //constructor
    public FedhaSystem() {
        this.members = new ArrayList<>();
        this.fixedDeposits = 0.0;
        this.totalRevenue = 0.0;
    }

    //register a user to the system
    public void registerMember(Member member) {
        //todo
        members.add(member);
    }

    //compute the total registration fee
    public double calculateTotalRegistrationFees() {
        double totalFees = 0.0;
        for (Member member : members) {
            totalFees += 1000; // Registration fee is constant at Kshs 1000
        }
        return totalFees;
    }

    //total share contributed by all members
    public double calculateTotalShares() {
        double totalShares = 0.0;
        for (Member member : members) {
            totalShares += member.getTotalShares();
        }
        return totalShares;
    }

    //add to fixed deposit when savings aren`t borrowed
    public void addToFixedDeposits(double amount) {
        this.fixedDeposits += amount;
    }

    //calculate interest earned on fixed deposit
    public double calculateFixedDepositInterest() {
        return this.fixedDeposits * 0.006;
    }

    public List<Member> getMembers() {
        return members;
    }


    //get dividends payable to each member based on their shares
    public void computeDividends() {
        double totalShares = calculateTotalShares();
        double dividendPool = totalRevenue * 0.90; // 10% retained for office expenses
        for (Member member : members) {
            double dividend = (member.getTotalShares() / totalShares) * dividendPool;
            System.out.println("Member " + member.getName() + " receives dividend: " + dividend);
        }
    }
}
