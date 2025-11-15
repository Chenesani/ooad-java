package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginView {

    public void show(Stage stage) {
        System.out.println(">>> LoginView.show() is running!");

        try {
            System.out.println("Current Class Path: " + System.getProperty("java.class.path"));
            System.out.println("Trying to load LoginView.fxml ...");

            var url = getClass().getResource("/view/LoginView.fxml");
            System.out.println("Resolved URL = " + url);

            if (url == null) {
                System.out.println("❌ ERROR: FXML NOT FOUND");
                return;
            }

            Parent root = FXMLLoader.load(url);
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




