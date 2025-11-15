package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InvestmentAccountView {
    public void show(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("InvestmentAccountView.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Investment Account");
        stage.show();
    }
}
