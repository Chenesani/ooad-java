package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DepositView {
    public void show(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("DepositView.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Deposit");
        stage.show();
    }
}
