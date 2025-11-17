import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String branch;
    protected Customer customer;

    public Account(double openingBalance, String branch, Customer customer) {
        this.accountNumber = generateAccountNumber();
        this.balance = openingBalance;
        this.branch = branch;
        this.customer = customer;
    }

    private String generateAccountNumber() {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int rand = new Random().nextInt(9000) + 1000;
        return "ACC" + date + "-" + rand;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance += amount;
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getBranch() { return branch; }
    public Customer getCustomer() { return customer; }

    public void setBranch(String branch) { this.branch = branch; }
}
