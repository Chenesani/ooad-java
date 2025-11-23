import java.util.List;

public class TransactionController {

    public static boolean deposit(Customer customer, String accountNumber, String amountStr) {

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (Exception e) {
            return false;
        }

        if (amount <= 0) return false;

        Account acc = findAccount(customer, accountNumber);
        if (acc == null) return false;

        acc.deposit(amount);

        return TransactionDAO.saveTransaction(acc, "DEPOSIT", amount);
    }


    public static boolean withdraw(Customer customer, String accountNumber, String amountStr) {

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (Exception e) {
            return false;
        }

        Account acc = findAccount(customer, accountNumber);
        if (acc == null) return false;

        
        if (acc instanceof Withdrawable withdrawableAcc) {
            if (withdrawableAcc.withdraw(amount)) {
                return TransactionDAO.saveTransaction(acc, "WITHDRAW", amount);
            }
        }

        return false;
    }


    public static List<String> getHistory(Account account) {
        return TransactionDAO.getTransactionHistory(account);
    }


    private static Account findAccount(Customer customer, String accountNumber) {
        return customer.getAccounts()
                .stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }
}
