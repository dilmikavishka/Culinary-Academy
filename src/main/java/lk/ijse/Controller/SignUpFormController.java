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

public class SignUpFormController {

    @FXML
    private AnchorPane anpSignUp;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private Hyperlink linkLogIn;

    @FXML
    private TextField txtUserEmail;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtUserPassword;

    @FXML
    private TextField txtUserRoll;

    @FXML
    void btnSignInOnAction(ActionEvent event) {

    }

    @FXML
    void linkLogInOnAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) anpSignUp.getScene().getWindow();
        currentStage.close();

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(rootNode);
        Stage loginStage = new Stage();
        loginStage.setScene(scene);
        loginStage.setTitle("Login Form");
        loginStage.show();
    }

}
