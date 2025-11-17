import javax.swing.*;
import java.awt.*;

public class CustomerDashboard extends JFrame {

    private Customer customer;

    public CustomerDashboard(Customer customer) {
        this.customer = customer;

        setTitle("Welcome, " + customer.getFirstName());
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("WIZZ BANK DASHBOARD", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setOpaque(true);
        header.setBackground(new Color(0,102,204));
        header.setForeground(Color.white);
        header.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(header, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(6,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JButton btnOpen = new JButton("Open New Account");
        JButton btnView = new JButton("View My Accounts");
        JButton btnDeposit = new JButton("Deposit Money");
        JButton btnWithdraw = new JButton("Withdraw Money");
        JButton btnHistory = new JButton("Transaction History");
        JButton btnLogout = new JButton("Logout");

        panel.add(btnOpen);
        panel.add(btnView);
        panel.add(btnDeposit);
        panel.add(btnWithdraw);
        panel.add(btnHistory);
        panel.add(btnLogout);

        add(panel, BorderLayout.CENTER);

        btnOpen.addActionListener(e -> new OpenAccountForm(this, customer).setVisible(true));
        btnView.addActionListener(e -> new ViewAccountsForm(this, customer).setVisible(true));
        btnDeposit.addActionListener(e -> new DepositForm(this, customer).setVisible(true));
        btnWithdraw.addActionListener(e -> new WithdrawForm(this, customer).setVisible(true));
        btnHistory.addActionListener(e -> new TransactionHistoryForm(this, customer).setVisible(true));

        btnLogout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }
}
