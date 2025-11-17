import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public static boolean saveTransaction(Account account, String type, double amount) {

        String sql = """
            INSERT INTO transactions (account_number, type, amount)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, account.getAccountNumber());
            ps.setString(2, type);
            ps.setDouble(3, amount);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }
    }


    public static List<String> getTransactionHistory(Account account) {

        List<String> list = new ArrayList<>();

        String sql = """
            SELECT * FROM transactions 
            WHERE account_number=?
            ORDER BY timestamp DESC
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, account.getAccountNumber());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(
                        rs.getString("timestamp") + " | " +
                        rs.getString("type") + " | BWP " +
                        rs.getDouble("amount")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
