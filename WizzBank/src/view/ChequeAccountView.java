package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChequeAccountView {
    public void show(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ChequeAccountView.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Cheque Account");
        stage.show();
    }
}
