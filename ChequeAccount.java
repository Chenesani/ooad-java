public class ChequeAccount extends Account implements Withdrawable {

    private String employerName;
    private String employerAddress;

    public ChequeAccount(double openingBalance, String branch, Customer customer,
                         String employerName, String employerAddress) {

        super(openingBalance, branch, customer);

        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public String getEmployerName() { return employerName; }
    public String getEmployerAddress() { return employerAddress; }
}
