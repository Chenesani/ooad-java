import javax.swing.*;
import java.awt.*;

public class ViewAccountsForm extends JDialog {

    public ViewAccountsForm(JFrame parent, Customer customer) {
        super(parent, "My Accounts", true);
        setSize(450, 300);
        setLocationRelativeTo(parent);

        DefaultListModel<String> model = new DefaultListModel<>();

        for (Account acc : customer.getAccounts()) {
            model.addElement(acc.getAccountNumber() + " | " +
                    acc.getClass().getSimpleName() + " | Balance: BWP " + acc.getBalance());
        }

        JList<String> list = new JList<>(model);
        JScrollPane scroll = new JScrollPane(list);

        add(scroll);
    }
}
