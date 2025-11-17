public class InvestmentAccount extends Account 
        implements InterestBearing, Withdrawable {

    private static final double INTEREST_RATE = 0.05; 

    public InvestmentAccount(double openingBalance, String branch, Customer customer) {
        super(openingBalance, branch, customer);

        if (openingBalance < 500) {
            throw new IllegalArgumentException("Investment accounts require a minimum deposit of BWP 500.");
        }
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public double calculateMonthlyInterest() {
        return balance * INTEREST_RATE;
    }
}
