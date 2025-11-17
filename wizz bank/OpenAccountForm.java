import javax.swing.*;
import java.awt.*;

public class OpenAccountForm extends JDialog {

    public OpenAccountForm(JFrame parent, Customer customer) {
        super(parent, "Open New Account", true);
        setSize(450, 420);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("OPEN NEW ACCOUNT", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.setOpaque(true);
        header.setBackground(new Color(0,102,204));
        header.setForeground(Color.white);
        add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(6,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel lblType = new JLabel("Account Type:");
        JComboBox<String> cmbType = new JComboBox<>(new String[]{"Savings","Investment","Cheque"});

        JLabel lblBranch = new JLabel("Branch:");
        JTextField txtBranch = new JTextField();

        JLabel lblOpening = new JLabel("Opening Deposit:");
        JTextField txtOpening = new JTextField();

        JLabel lblEmpName = new JLabel("Employer Name:");
        JTextField txtEmpName = new JTextField();
        JLabel lblEmpAdd = new JLabel("Employer Address:");
        JTextField txtEmpAdd = new JTextField();

        
        lblEmpName.setVisible(false); txtEmpName.setVisible(false);
        lblEmpAdd.setVisible(false);  txtEmpAdd.setVisible(false);

        cmbType.addActionListener(e -> {
            boolean isCheque = cmbType.getSelectedItem().equals("Cheque");
            lblEmpName.setVisible(isCheque);
            txtEmpName.setVisible(isCheque);
            lblEmpAdd.setVisible(isCheque);
            txtEmpAdd.setVisible(isCheque);
        });

        form.add(lblType); form.add(cmbType);
        form.add(lblBranch); form.add(txtBranch);
        form.add(lblOpening); form.add(txtOpening);
        form.add(lblEmpName); form.add(txtEmpName);
        form.add(lblEmpAdd); form.add(txtEmpAdd);

        add(form, BorderLayout.CENTER);

        JButton btnCreate = new JButton("Create Account");
        add(btnCreate, BorderLayout.SOUTH);

        btnCreate.addActionListener(e -> {

            boolean ok = AccountController.openAccount(
                    customer,
                    (String)cmbType.getSelectedItem(),
                    txtBranch.getText(),
                    txtOpening.getText(),
                    txtEmpName.getText(),
                    txtEmpAdd.getText()
            );

            if (ok) {
                JOptionPane.showMessageDialog(this, "Account Created Successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create account. Check inputs.");
            }
        });
    }
}
