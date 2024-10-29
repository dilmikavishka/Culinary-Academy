package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.Config.FactoryConfiguration;

public class Launcher extends Application {
    public static void main(String[] args) {
        FactoryConfiguration instance = FactoryConfiguration.getInstance();
        instance.getSession();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/GetStartForm.fxml"))));
        stage.setTitle("Get Start");
        stage.centerOnScreen();
        stage.show();
    }

}