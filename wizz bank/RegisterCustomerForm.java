import javax.swing.*;
import java.awt.*;

public class RegisterCustomerForm extends JDialog {

    public RegisterCustomerForm(JFrame parent) {
        super(parent, "Register New Customer", true);
        setSize(420, 520);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("CREATE YOUR ACCOUNT", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.setOpaque(true);
        header.setBackground(new Color(0,102,204));
        header.setForeground(Color.white);
        add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(10, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblType = new JLabel("Customer Type:");
        JComboBox<String> cmbType = new JComboBox<>(new String[]{"Individual", "Business"});

        JLabel lblFirst = new JLabel("First Name:");
        JTextField txtFirst = new JTextField();

        JLabel lblLast = new JLabel("Last Name:");
        JTextField txtLast = new JTextField();

        JLabel lblAddress = new JLabel("Address:");
        JTextField txtAddress = new JTextField();

        JLabel lblPhone = new JLabel("Phone:");
        JTextField txtPhone = new JTextField();

        JLabel lblUser = new JLabel("Username:");
        JTextField txtUser = new JTextField();

        JLabel lblPass = new JLabel("Password:");
        JPasswordField txtPass = new JPasswordField();

        JLabel lblID = new JLabel("National ID:");
        JTextField txtID = new JTextField();

        JLabel lblComp = new JLabel("Company Name:");
        JTextField txtComp = new JTextField();

        JLabel lblTax = new JLabel("Tax Number:");
        JTextField txtTax = new JTextField();

        
        lblComp.setVisible(false);
        txtComp.setVisible(false);
        lblTax.setVisible(false);
        txtTax.setVisible(false);

        
        cmbType.addActionListener(e -> {
            boolean isBusiness = cmbType.getSelectedItem().equals("Business");

            lblID.setVisible(!isBusiness);
            txtID.setVisible(!isBusiness);

            lblComp.setVisible(isBusiness);
            txtComp.setVisible(isBusiness);
            lblTax.setVisible(isBusiness);
            txtTax.setVisible(isBusiness);
        });

        form.add(lblType); form.add(cmbType);
        form.add(lblFirst); form.add(txtFirst);
        form.add(lblLast); form.add(txtLast);
        form.add(lblAddress); form.add(txtAddress);
        form.add(lblPhone); form.add(txtPhone);
        form.add(lblUser); form.add(txtUser);
        form.add(lblPass); form.add(txtPass);
        form.add(lblID); form.add(txtID);
        form.add(lblComp); form.add(txtComp);
        form.add(lblTax); form.add(txtTax);

        add(form, BorderLayout.CENTER);

        JButton btnRegister = new JButton("Create Account");
        add(btnRegister, BorderLayout.SOUTH);

        btnRegister.addActionListener(e -> {
            String type = (String) cmbType.getSelectedItem();

            boolean ok;

            if (type.equals("Individual")) {
                ok = CustomerController.registerIndividual(
                        txtFirst.getText(),
                        txtLast.getText(),
                        txtAddress.getText(),
                        txtPhone.getText(),
                        txtUser.getText(),
                        new String(txtPass.getPassword()),
                        txtID.getText()
                );
            } else {
                ok = CustomerController.registerBusiness(
                        txtFirst.getText(),
                        txtLast.getText(),
                        txtAddress.getText(),
                        txtPhone.getText(),
                        txtUser.getText(),
                        new String(txtPass.getPassword()),
                        txtComp.getText(),
                        txtTax.getText()
                );
            }

            if (ok) {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Check your inputs.");
            }
        });
    }  
}  


