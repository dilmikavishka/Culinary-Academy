package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private AnchorPane anpLogin;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private Hyperlink linkForgotPassword;

    @FXML
    private Hyperlink linkSign;

    @FXML
    private TextField txtLogUserName;

    @FXML
    private TextField txtLogUserPassword;

    @FXML
    void btnLoginOnAction(ActionEvent event) {

    }

    @FXML
    void linkForgotPasswordOnAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) anpLogin.getScene().getWindow();
        currentStage.close();

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/ForgotPasswordForm.fxml"));
        Scene scene = new Scene(rootNode);
        Stage loginStage = new Stage();
        loginStage.setScene(scene);
        loginStage.setTitle("ForgotPassword Form");
        loginStage.show();
    }

    @FXML
    void linkSignOnAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) anpLogin.getScene().getWindow();
        currentStage.close();

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/SignUpForm.fxml"));
        Scene scene = new Scene(rootNode);
        Stage loginStage = new Stage();
        loginStage.setScene(scene);
        loginStage.setTitle("Sign Up Form");
        loginStage.show();
    }

}
