import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginForm() {
        setTitle("Online Banking - Login");
        setSize(380, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

       
        JLabel lblHeader = new JLabel("Wizz Bank Login", SwingConstants.CENTER);
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblHeader.setOpaque(true);
        lblHeader.setBackground(new Color(0, 102, 204));
        lblHeader.setForeground(Color.white);
        lblHeader.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblHeader, BorderLayout.NORTH);

        
        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblUsername = new JLabel("Username:");
        txtUsername = new JTextField();

        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField();

        form.add(lblUsername);
        form.add(txtUsername);
        form.add(lblPassword);
        form.add(txtPassword);

        add(form, BorderLayout.CENTER);

        
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Create Account");

        btnPanel.add(btnLogin);
        btnPanel.add(btnRegister);

        add(btnPanel, BorderLayout.SOUTH);

        
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            Customer customer = AuthController.login(username, password);

            if (customer != null) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new CustomerDashboard(customer).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        });

        
        btnRegister.addActionListener(e -> {
            new RegisterCustomerForm(this).setVisible(true);
        });
    }

public static void main(String[] args) {

    
    DatabaseSetup.initialize();

    
    SwingUtilities.invokeLater(() -> {
        new LoginForm().setVisible(true);
    });
}

}
