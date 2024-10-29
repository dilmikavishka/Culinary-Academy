package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotPasswordFormController {

    @FXML
    private AnchorPane anp2;

    @FXML
    private AnchorPane anpForgotPassword;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSendOtp;

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtNewPassword;

    @FXML
    private TextField txtOtp;

    @FXML
    void btnResetOnAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) anpForgotPassword.getScene().getWindow();
        currentStage.close();

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(rootNode);
        Stage loginStage = new Stage();
        loginStage.setScene(scene);
        loginStage.setTitle("Login Form");
        loginStage.show();
    }

    @FXML
    void btnSendOtpOnAction(ActionEvent event) {

    }

}
