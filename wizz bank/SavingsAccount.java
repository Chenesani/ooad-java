public class SavingsAccount extends Account implements InterestBearing {

    private static final double INTEREST_RATE = 0.0005; 

    public SavingsAccount(double openingBalance, String branch, Customer customer) {
        super(openingBalance, branch, customer);
    }

    @Override
    public double calculateMonthlyInterest() {
        return balance * INTEREST_RATE;
    }

    // Withdrawals are NOT allowed
    public boolean withdraw(double amount) {
        return false; 
    }
}
