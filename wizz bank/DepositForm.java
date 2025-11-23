import javax.swing.*;
import java.awt.*;

public class DepositForm extends JDialog {

    public DepositForm(JFrame parent, Customer customer) {
        super(parent, "Deposit Money", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("DEPOSIT MONEY", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.setOpaque(true);
        header.setBackground(new Color(0,102,204));
        header.setForeground(Color.white);
        add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel lblAcc = new JLabel("Select Account:");
        JComboBox<String> cmbAcc = new JComboBox<>();

        for (Account acc : customer.getAccounts()) {
            cmbAcc.addItem(acc.getAccountNumber());
        }

        JLabel lblAmount = new JLabel("Amount:");
        JTextField txtAmt = new JTextField();

        form.add(lblAcc); form.add(cmbAcc);
        form.add(lblAmount); form.add(txtAmt);

        add(form, BorderLayout.CENTER);

        JButton btnDep = new JButton("Deposit");
        add(btnDep, BorderLayout.SOUTH);

        btnDep.addActionListener(e -> {

            boolean ok = TransactionController.deposit(
                    customer,
                    (String)cmbAcc.getSelectedItem(),
                    txtAmt.getText()
            );

            if (ok) {
                JOptionPane.showMessageDialog(this, "Deposit Successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Deposit failed.");
            }
        });
    }
}
