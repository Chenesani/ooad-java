import java.util.ArrayList;
import java.util.List;

public abstract class Customer {
    protected String firstName;
    protected String lastName;
    protected String address;
    protected String phone;
    protected List<Account> accounts;

    
    protected String username;

    public Customer(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.accounts = new ArrayList<>();
    }

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
}

