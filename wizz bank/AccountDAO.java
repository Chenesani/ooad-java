import java.sql.*;

public class AccountDAO {

    public static boolean saveAccount(Account acc, Customer c) {

        int customerId = getCustomerId(c);

        if (customerId == -1) return false;

        String sql = """
            INSERT INTO accounts 
            (account_number, customer_id, type, branch, balance, employer_name, employer_address)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, acc.getAccountNumber());
            ps.setInt(2, customerId);
            ps.setString(3, acc.getClass().getSimpleName());
            ps.setString(4, acc.getBranch());
            ps.setDouble(5, acc.getBalance());

            if (acc instanceof ChequeAccount ch) {
                ps.setString(6, ch.getEmployerName());
                ps.setString(7, ch.getEmployerAddress());
            } else {
                ps.setString(6, null);
                ps.setString(7, null);
            }

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void loadAccountsForCustomer(Customer c, int customerId) {

        String sql = "SELECT * FROM accounts WHERE customer_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String type = rs.getString("type");
                double balance = rs.getDouble("balance");
                String branch = rs.getString("branch");
                String accNum = rs.getString("account_number");

                Account acc;

                switch (type) {

                    case "SavingsAccount":
                        acc = new SavingsAccount(balance, branch, c);
                        break;

                    case "InvestmentAccount":
                        acc = new InvestmentAccount(balance, branch, c);
                        break;

                    case "ChequeAccount":
                        acc = new ChequeAccount(
                                balance, branch, c,
                                rs.getString("employer_name"),
                                rs.getString("employer_address")
                        );
                        break;

                    default:
                        continue;
                }

                
                acc.accountNumber = accNum;
                c.addAccount(acc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private static int getCustomerId(Customer c) {
        String sql = "SELECT customer_id FROM customers WHERE username=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getUsername());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("customer_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}

