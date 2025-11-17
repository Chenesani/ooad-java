import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    public static void initialize() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // CUSTOMERS TABLE
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS customers (
                    customer_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    type TEXT NOT NULL,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    address TEXT NOT NULL,
                    phone TEXT NOT NULL,
                    username TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    national_id TEXT,
                    company_name TEXT,
                    tax_number TEXT
                );
            """);

            // ACCOUNTS TABLE
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS accounts (
                    account_number TEXT PRIMARY KEY,
                    customer_id INTEGER NOT NULL,
                    type TEXT NOT NULL,
                    branch TEXT NOT NULL,
                    balance REAL NOT NULL,
                    employer_name TEXT,
                    employer_address TEXT,
                    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
                );
            """);

            // TRANSACTIONS TABLE
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transactions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    account_number TEXT NOT NULL,
                    type TEXT NOT NULL,
                    amount REAL NOT NULL,
                    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
                );
            """);

            System.out.println("Database initialized successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
