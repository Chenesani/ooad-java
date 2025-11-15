package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CustomerProflieView {
    public void show(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CustomerProfileView.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Profile");
        stage.show();
    }
}
