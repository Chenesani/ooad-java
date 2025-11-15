package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InterestPaymentView {
    public void show(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("InterestPaymentView.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Interest Payment");
        stage.show();
    }
}
