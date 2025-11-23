import javax.swing.*;
import java.awt.*;

public class TransactionHistoryForm extends JDialog {

    public TransactionHistoryForm(JFrame parent, Customer customer) {
        super(parent, "Transaction History", true);
        setSize(500, 350);
        setLocationRelativeTo(parent);

        DefaultListModel<String> model = new DefaultListModel<>();

        for (Account acc : customer.getAccounts()) {
            for (String t : TransactionController.getHistory(acc)) {
                model.addElement(t);
            }
        }

        JList<String> list = new JList<>(model);
        add(new JScrollPane(list));
    }
}
