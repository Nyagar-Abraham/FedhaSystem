import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FedhaSystemGUI extends JFrame {
    private FedhaSystem fedhaSystem;

    private JTextField nameField, ageField, sharesField, loanAmountField;
    private JTextArea displayArea;

    private JComboBox<String> loanTypeComboBox;


    //constructor
    public FedhaSystemGUI() {
        // Initialize the Fedha system
        fedhaSystem = new FedhaSystem();

        // Set up the main frame
        setTitle("Fedha Youth Group System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);

        // Create UI components
        createComponents();

        setVisible(true);
    }

    //create the ui for the gui
    private void createComponents() {
        // Layouts
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);

        // Name input
        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Name: "));
        nameField = new JTextField(20);
        namePanel.add(nameField);
        mainPanel.add(namePanel);

        // Age input
        JPanel agePanel = new JPanel();
        agePanel.add(new JLabel("Age: "));
        ageField = new JTextField(5);
        agePanel.add(ageField);
        mainPanel.add(agePanel);

        // Register Member Button
        JButton registerButton = new JButton("Register Member");
        registerButton.addActionListener(new RegisterMemberAction());
        mainPanel.add(registerButton);

        // Shares contribution
        JPanel sharesPanel = new JPanel();
        sharesPanel.add(new JLabel("Contribute Shares: "));
        sharesField = new JTextField(10);
        sharesPanel.add(sharesField);
        mainPanel.add(sharesPanel);

        // Contribute Shares Button
        JButton contributeButton = new JButton("Contribute Shares");
        contributeButton.addActionListener(new ContributeSharesAction());
        mainPanel.add(contributeButton);



        // Loan Type selection
        String[] loanTypes = { "Emergency Loan", "Short-Term Loan", "Normal Loan", "Development Loan" };
        loanTypeComboBox = new JComboBox<>(loanTypes);
        JPanel loanTypePanel = new JPanel();
        loanTypePanel.add(new JLabel("Select Loan Type: "));
        loanTypePanel.add(loanTypeComboBox);
        mainPanel.add(loanTypePanel);

        // Loan Amount input
        JPanel loanPanel = new JPanel();
        loanPanel.add(new JLabel("Loan Amount: "));
        loanAmountField = new JTextField(10);
        loanPanel.add(loanAmountField);
        mainPanel.add(loanPanel);


        // Apply for Loan Button
        JButton loanButton = new JButton("Apply for Loan");
        loanButton.addActionListener(new ApplyForLoanAction());
        mainPanel.add(loanButton);

        // Display area for output
        displayArea = new JTextArea(15, 40);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        mainPanel.add(scrollPane);
    }


    /**
     * Action for registering a member.
     */
    private class RegisterMemberAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());

            // Validate age
            if (age < 18 || age > 35) {
                displayArea.append("Error: Age must be between 18 and 35.\n");
                return;
            }

            Member newMember = new Member(name, age, 1000); // Registration fee is 1000
            fedhaSystem.registerMember(newMember);
            displayArea.append("Member registered: " + name + "\n");
        }
    }

    /**
     * Action for contributing shares.
     */
    private class ContributeSharesAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            double shares = Double.parseDouble(sharesField.getText());

            Member member = findMemberByName(name);
            if (member != null) {
                member.contributeShares(shares);
                displayArea.append("Shares contributed: " + shares + " for member " + name + "\n");
            } else {
                displayArea.append("Error: Member not found.\n");
            }
        }
    }

    private class ApplyForLoanAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();

            String loanType = (String) loanTypeComboBox.getSelectedItem();

            Member member = findMemberByName(name);
            double membersShares =  member.getTotalShares();

            double EnteredLoanAmount = Double.parseDouble(loanAmountField.getText());
            double loanAmount = getMaximumLoanAmount(loanType,EnteredLoanAmount,membersShares);
            if(loanAmount == 0.0){
                 displayArea.append("you have requested loan above you qualifications");
            }
            if (member != null) {
                // Determine interest rate based on loan type
                double interestRate = getInterestRateForLoanType(loanType);
                int repaymentPeriod = getRepaymentPeriodForLoanType(loanType);

                Loan loan = new Loan(loanType, loanAmount, interestRate, repaymentPeriod, member);
                member.applyForLoan(loan);
                displayArea.append("Loan applied: " + loanAmount + " for member " + name + " (Type: " + loanType + ")\n");
            } else {
                displayArea.append("Error: Member not found.\n");
            }
        }
    }


    private Member findMemberByName(String name) {
        for (Member member : fedhaSystem.getMembers()) {
            if (member.getName().equals(name)) {
                return member;
            }
        }
        return null;
    }

    /**
     * Returns the interest rate based on the loan type.
     * @param loanType The type of loan.
     * @return The interest rate for the loan.
     */
    private double getInterestRateForLoanType(String loanType) {
        switch (loanType) {
            case "Emergency Loan":
                return 0.3;
            case "Short-Term Loan":
                return 0.6;
            case "Normal Loan":
                return 1.0;
            case "Development Loan":
                return 1.4;
            default:
                return 0.10;
        }
    }


    private int getRepaymentPeriodForLoanType(String loanType) {
        switch (loanType) {
            case "Emergency Loan":
                return 12;
            case "Short-Term Loan":
                return 24;
            case "Normal Loan":
                return 36;
            case "Development Loan":
                return 48;
            default:
                return 12;
        }
    }

    private double getMaximumLoanAmount(String loanType,double requestedAmout ,double memberShares ) {
        switch (loanType) {
            case "Emergency Loan":
                if(requestedAmout >memberShares){
                    return 0.0;
                }
                return requestedAmout;
            case "Short-Term Loan":
                if(requestedAmout >memberShares*2){
                    return 0.0;
                }
                return requestedAmout;
            case "Normal Loan":
                if(requestedAmout >memberShares*3){
                    return 0.0;
                }
                return requestedAmout;
            case "Development Loan":
                if(requestedAmout >memberShares*4){
                    return 0.0;
                }
                return requestedAmout;
            default:
                return requestedAmout;
        }
    }


}
