import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

/**
 * Banking System – OOP Demo (single source file)
 * ------------------------------------------------
 * Features:
 * - Entities: Customer, Account (abstract), SavingsAccount, InvestmentAccount, ChequeAccount
 * - Relationships: A Customer can hold multiple Accounts; an Account cannot exist without an owner Customer
 * - Interfaces: InterestBearing (with default interest application behavior)
 * - Inheritance & Polymorphism: Account subclasses; Bank stores Accounts polymorphically
 * - Overriding: withdraw(), applyMonthlyInterest(), toString()
 * - Overloading: deposit() methods; Bank.openAccount() variants
 * - Business rules enforced as per scenario (see each class)
 *
 * Note: Uses BigDecimal for money; BWP currency assumed.
 */
public class BankingApp {
    public static void main(String[] args) {
        Bank bank = new Bank("Gaborone Main");

        // Create customers
        Customer alice = bank.registerCustomer("C001", "Alice", "Mokgalo", "Plot 10, Gaborone");
        Customer bob   = bank.registerCustomer("C002", "Bob", "Phiri", "Block 8, Gaborone");

        // Open accounts honoring the rules
        String aliceSavings = bank.openSavingsAccount(alice, BigDecimal.valueOf(1000));
        String aliceInvest  = bank.openInvestmentAccount(alice, BigDecimal.valueOf(2000)); // ≥ BWP 500 required

        String bobCheque    = bank.openChequeAccount(
                bob,
                BigDecimal.valueOf(50), // salary accounts can be opened with any initial amount
                "Kalahari Tech Pty Ltd",
                "CBD Tower, Gaborone"
        );

        // Make some transactions
        bank.deposit(aliceSavings, BigDecimal.valueOf(500), "Top-up before month-end");
        bank.deposit(aliceInvest, BigDecimal.valueOf(1500));
        bank.deposit(bobCheque, BigDecimal.valueOf(5000), "Salary");

        // Attempting to withdraw from Savings should fail
        try {
            bank.withdraw(aliceSavings, BigDecimal.valueOf(100));
        } catch (UnsupportedOperationException ex) {
            System.out.println("Expected rule: " + ex.getMessage());
        }

        // Valid withdrawals
        bank.withdraw(aliceInvest, BigDecimal.valueOf(300));
        bank.withdraw(bobCheque, BigDecimal.valueOf(700));

        // Apply monthly interest (5% for Investment; 0.05% for Savings)
        bank.applyMonthlyInterestToAll();

        // Print simple statement
        bank.printCustomerSummary(alice);
        bank.printCustomerSummary(bob);
    }
}

// ===================== DOMAIN ===================== //

/** Customer entity */
class Customer {
    private final String id; // e.g., national ID or bank customer ID
    private String firstName;
    private String surname;
    private String address;

    // Accounts held by this customer (read-only view exposed)
    private final List<Account> accounts = new ArrayList<>();

    public Customer(String id, String firstName, String surname, String address) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Customer id required");
        this.id = id;
        this.firstName = Objects.requireNonNull(firstName);
        this.surname = Objects.requireNonNull(surname);
        this.address = Objects.requireNonNull(address);
    }

    void addAccount(Account account) { // package-private: only Bank should wire this
        accounts.add(Objects.requireNonNull(account));
    }

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getSurname() { return surname; }
    public String getAddress() { return address; }

    public List<Account> getAccounts() { return Collections.unmodifiableList(accounts); }

    @Override public String toString() {
        return firstName + " " + surname + " (" + id + ")";
    }
}

/** Interest-bearing behavior */
interface InterestBearing {
    /** Monthly interest rate as a decimal (e.g., 0.05 for 5%) */
    BigDecimal monthlyRate();

    /** Default interest application for principal balance (no compounding within month) */
    default void applyMonthlyInterest(Account account) {
        BigDecimal interest = account.getBalance().multiply(monthlyRate());
        account.internalCredit(interest.setScale(2, RoundingMode.HALF_UP),
                "Monthly interest @ " + monthlyRate().multiply(BigDecimal.valueOf(100)) + "%");
    }
}

/** Abstract base Account */
abstract class Account {
    private static long NEXT = 100_000_000L; // simple generator for demo

    private final String accountNumber;
    private final String branch;
    private final Customer owner; // cannot be null (composition-like rule)

    private BigDecimal balance; // money

    // Transaction log for audit
    private final List<String> ledger = new ArrayList<>();

    protected Account(Customer owner, String branch, BigDecimal openingDeposit) {
        this.accountNumber = String.valueOf(NEXT++);
        this.branch = Objects.requireNonNull(branch);
        this.owner = Objects.requireNonNull(owner);
        this.balance = requireNonNegative(openingDeposit);
        log("Account opened with BWP " + fmt(balance));
    }

    public String getAccountNumber() { return accountNumber; }
    public String getBranch() { return branch; }
    public Customer getOwner() { return owner; }
    public BigDecimal getBalance() { return balance.setScale(2, RoundingMode.HALF_UP); }

    protected static BigDecimal requireNonNegative(BigDecimal amount) {
        Objects.requireNonNull(amount);
        if (amount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Amount cannot be negative");
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    protected void log(String line) { ledger.add(LocalDate.now() + " | " + line); }

    String fmt(BigDecimal amt) { return amt.setScale(2, RoundingMode.HALF_UP).toPlainString(); }

    // --- Core operations (polymorphic & overridable) ---

    /** Deposit with narration (overload 1) */
    public void deposit(BigDecimal amount, String note) {
        amount = requireNonNegative(amount);
        internalCredit(amount, note == null ? "Deposit" : ("Deposit - " + note));
    }

    /** Deposit without narration (overload 2) */
    public void deposit(BigDecimal amount) { deposit(amount, null); }

    /** Withdraw (may be overridden to restrict behavior) */
    public void withdraw(BigDecimal amount) {
        amount = requireNonNegative(amount);
        ensureSufficientFunds(amount);
        balance = balance.subtract(amount);
        log("Withdrawal BWP " + fmt(amount));
    }

    protected void ensureSufficientFunds(BigDecimal amount) {
        if (balance.compareTo(amount) < 0)
            throw new IllegalStateException("Insufficient funds");
    }

    /** Internal credit used by interest and deposits */
    void internalCredit(BigDecimal amount, String note) {
        balance = balance.add(amount);
        log(note + " +BWP " + fmt(amount));
    }

    public List<String> getLedger() { return Collections.unmodifiableList(ledger); }

    @Override public String toString() {
        return getClass().getSimpleName() + "{" +
                "acct=" + accountNumber +
                ", owner=" + owner +
                ", branch='" + branch + '\'' +
                ", balance=BWP " + fmt(balance) +
                '}';
    }
}

/** SavingsAccount: deposits only; pays 0.05% monthly interest */
class SavingsAccount extends Account implements InterestBearing {
    private static final BigDecimal RATE = new BigDecimal("0.0005"); // 0.05% per month

    public SavingsAccount(Customer owner, String branch, BigDecimal openingDeposit) {
        super(owner, branch, openingDeposit);
    }

    @Override public void withdraw(BigDecimal amount) {
        throw new UnsupportedOperationException("Withdrawals are not allowed from SavingsAccount");
    }

    @Override public BigDecimal monthlyRate() { return RATE; }
}

/** InvestmentAccount: deposits & withdrawals allowed; requires ≥ BWP 500 to open; pays 5% monthly interest */
class InvestmentAccount extends Account implements InterestBearing {
    private static final BigDecimal RATE = new BigDecimal("0.05"); // 5% per month

    public InvestmentAccount(Customer owner, String branch, BigDecimal openingDeposit) {
        super(owner, branch, openingDeposit);
        if (openingDeposit.compareTo(new BigDecimal("500")) < 0)
            throw new IllegalArgumentException("InvestmentAccount requires a minimum opening deposit of BWP 500.00");
    }

    @Override public BigDecimal monthlyRate() { return RATE; }
}

/** ChequeAccount: typical salary account; deposits & withdrawals allowed; only for working persons */
class ChequeAccount extends Account {
    private final String employerName;
    private final String employerAddress;

    public ChequeAccount(Customer owner, String branch, BigDecimal openingDeposit,
                         String employerName, String employerAddress) {
        super(owner, branch, openingDeposit);
        if (employerName == null || employerName.isBlank())
            throw new IllegalArgumentException("Employer name required for ChequeAccount");
        if (employerAddress == null || employerAddress.isBlank())
            throw new IllegalArgumentException("Employer address required for ChequeAccount");
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    public String getEmployerName() { return employerName; }
    public String getEmployerAddress() { return employerAddress; }
}

// ===================== SERVICE (AGGREGATE ROOT) ===================== //

class Bank {
    private final String branchName;
    private final Map<String, Customer> customers = new HashMap<>();
    private final Map<String, Account> accounts = new HashMap<>();

    public Bank(String branchName) { this.branchName = Objects.requireNonNull(branchName); }

    // --- Customer management ---
    public Customer registerCustomer(String id, String firstName, String surname, String address) {
        if (customers.containsKey(id)) throw new IllegalArgumentException("Customer already exists: " + id);
        Customer c = new Customer(id, firstName, surname, address);
        customers.put(id, c);
        return c;
    }

    public Optional<Customer> findCustomer(String id) { return Optional.ofNullable(customers.get(id)); }

    // --- Account opening (overloaded convenience methods) ---
    public String openSavingsAccount(Customer owner, BigDecimal openingDeposit) {
        SavingsAccount a = new SavingsAccount(owner, branchName, openingDeposit);
        return attachAccount(owner, a);
    }

    public String openInvestmentAccount(Customer owner, BigDecimal openingDeposit) {
        InvestmentAccount a = new InvestmentAccount(owner, branchName, openingDeposit);
        return attachAccount(owner, a);
    }

    public String openChequeAccount(Customer owner, BigDecimal openingDeposit,
                                    String employerName, String employerAddress) {
        ChequeAccount a = new ChequeAccount(owner, branchName, openingDeposit, employerName, employerAddress);
        return attachAccount(owner, a);
    }

    // Generic (polymorphic) open method
    public String openAccount(Account account) { return attachAccount(account.getOwner(), account); }

    private String attachAccount(Customer owner, Account account) {
        owner.addAccount(account); // enforce: account cannot exist without customer
        accounts.put(account.getAccountNumber(), account);
        return account.getAccountNumber();
    }

    // --- Transactions ---
    public void deposit(String accountNumber, BigDecimal amount) { get(accountNumber).deposit(amount); }
    public void deposit(String accountNumber, BigDecimal amount, String note) { get(accountNumber).deposit(amount, note); }

    public void withdraw(String accountNumber, BigDecimal amount) { get(accountNumber).withdraw(amount); }

    // --- Interest processing ---
    public void applyMonthlyInterestToAll() {
        for (Account a : accounts.values()) {
            if (a instanceof InterestBearing ib) {
                ib.applyMonthlyInterest(a);
            }
        }
    }

    // --- Queries & reporting ---
    public Account get(String accountNumber) {
        Account a = accounts.get(accountNumber);
        if (a == null) throw new NoSuchElementException("No account: " + accountNumber);
        return a;
    }

    public void printCustomerSummary(Customer c) {
        System.out.println("\n=== Statement for " + c + " ===");
        for (Account a : c.getAccounts()) {
            System.out.println(a);
            a.getLedger().forEach(line -> System.out.println("  " + line));
        }
    }
}
