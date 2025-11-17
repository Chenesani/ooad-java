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

                // Preserve account number from DB
                acc.accountNumber = accNum;
                c.addAccount(acc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static int getCustomerId(Customer c) {
        String sql = "SELECT customer_id FROM customers WHERE first_name=? AND last_name=? AND username=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());

            // Must match username uniquely
            if (c instanceof IndividualCustomer ic) {
                ps.setString(3, ic.getNationalId());
            } else if (c instanceof BusinessCustomer bc) {
                ps.setString(3, bc.getTaxNumber());
            } else {
                ps.setString(3, "");
            }

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("customer_id");
            }

        } catch (Exception e) {
            return -1;
        }
        return -1;
    }
}
