package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.BO.BoFactory;
import lk.ijse.BO.custom.UserBO;

import java.io.IOException;

public class GetStartFormController {
    UserBO userBO = (UserBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.USER);

    @FXML
    private AnchorPane anpGetStarted;

    @FXML
    private JFXButton btnGetStarted;

    @FXML
    void btnGetStartedOnAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) anpGetStarted.getScene().getWindow();
        currentStage.close();

        boolean isUsersExsistorNot = userBO.isUsersExsistorNotDB();
        if (isUsersExsistorNot){
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
            Scene scene = new Scene(rootNode);
            Stage loginStage = new Stage();
            loginStage.setScene(scene);
            loginStage.setTitle("Login Form");
            loginStage.show();
        }else {
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/SignUpForm.fxml"));
            Scene scene = new Scene(rootNode);
            Stage loginStage = new Stage();
            loginStage.setScene(scene);
            loginStage.setTitle("Sign Up Form");
            loginStage.show();

        }


    }

}
