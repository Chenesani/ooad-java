import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    
    private static final String URL =
            "jdbc:sqlite:C:/wizz bank/bank.db";

    public static Connection getConnection() {
        try {
            System.out.println("DB USING: " + URL);   
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.out.println("DB Error: " + e.getMessage());
            return null;
        }
    }
}



