 import java.sql.*;

public class CustomerDAO {

    
    public static boolean saveIndividualCustomer(IndividualCustomer c, String username, String password) {

        String sql = """
            INSERT INTO customers 
            (type, first_name, last_name, address, phone, username, password, national_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "Individual");
            ps.setString(2, c.getFirstName());
            ps.setString(3, c.getLastName());
            ps.setString(4, c.getAddress());
            ps.setString(5, c.getPhone());
            ps.setString(6, username);
            ps.setString(7, password);
            ps.setString(8, c.getNationalId());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    
    public static boolean saveBusinessCustomer(BusinessCustomer c, String username, String password) {

        String sql = """
            INSERT INTO customers
            (type, first_name, last_name, address, phone, username, password, company_name, tax_number)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "Business");
            ps.setString(2, c.getFirstName());
            ps.setString(3, c.getLastName());
            ps.setString(4, c.getAddress());
            ps.setString(5, c.getPhone());
            ps.setString(6, username);
            ps.setString(7, password);
            ps.setString(8, c.getCompanyName());
            ps.setString(9, c.getTaxNumber());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



 
    public static Customer loginCustomer(String username, String password) {

        String sql = "SELECT * FROM customers WHERE username=? AND password=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            String type = rs.getString("type");

            Customer c;

            if (type.equals("Individual")) {
                c = new IndividualCustomer(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("national_id")
                );
            } else {
                c = new BusinessCustomer(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("company_name"),
                    rs.getString("tax_number")
                );
            }

            int customerId = rs.getInt("customer_id");
            AccountDAO.loadAccountsForCustomer(c, customerId);

            return c;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

} 


