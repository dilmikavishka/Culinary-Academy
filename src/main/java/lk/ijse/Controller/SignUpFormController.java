package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.BO.BoFactory;
import lk.ijse.BO.custom.UserBO;
import lk.ijse.DTO.UserDto;
import lk.ijse.Enum.Role;
import lk.ijse.Regex.RegexFactory;
import lk.ijse.Regex.RegexType;

import java.io.IOException;

public class SignUpFormController {

    UserBO  userBO = (UserBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.USER);

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

    RegexFactory  regexFactory = RegexFactory.getInstance();

    @FXML
    void btnSignInOnAction(ActionEvent event) {
        if (validateInput()) {
            String email = txtUserEmail.getText();
            String username = txtUserName.getText();
            String password = txtUserPassword.getText();
            String role = txtUserRoll.getText();

            try {
                UserDto userDto = new UserDto(email, username, password, Role.valueOf(role));
                System.out.println(userDto);
                userBO.save(userDto);
                new Alert(Alert.AlertType.CONFIRMATION, "User successfully registered!").show();
                clearInputText();
            } catch (IllegalArgumentException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid role. Please enter a valid role.").show();
                txtUserRoll.setStyle("-fx-text-fill: red;");
            }
        }
    }

    private void clearInputText() {
        txtUserEmail.setText("");
        txtUserName.setText("");
        txtUserPassword.setText("");
        txtUserRoll.setText("");
    }

    private boolean validateInput() {
        boolean isValid = true;
        String email = txtUserEmail.getText().trim();
        String username = txtUserName.getText().trim();
        String password = txtUserPassword.getText().trim();
        String role = txtUserRoll.getText().trim();

        if (!regexFactory.getPattern(RegexType.EMAIL).matcher(email).matches()) {
            txtUserEmail.setStyle("-fx-text-fill: red;");
            new Alert(Alert.AlertType.WARNING, "Please enter a valid email.").show();
            isValid = false;
        } else {
            txtUserEmail.setStyle("-fx-text-fill: black;");
        }

        if (!regexFactory.getPattern(RegexType.USERNAME).matcher(username).matches()) {
            txtUserName.setStyle("-fx-text-fill: red;");
            new Alert(Alert.AlertType.WARNING, "Username should contain only letters.").show();
            isValid = false;
        } else {
            txtUserName.setStyle("-fx-text-fill: black;");
        }

        if (!regexFactory.getPattern(RegexType.PASSWORD).matcher(password).matches()) {
            txtUserPassword.setStyle("-fx-text-fill: red;");
            new Alert(Alert.AlertType.WARNING, "Password must be exactly 8 digits.").show();
            isValid = false;
        } else {
            txtUserPassword.setStyle("-fx-text-fill: black;");
        }


        if (!regexFactory.getPattern(RegexType.USERROLE).matcher(role).matches()) {
            txtUserRoll.setStyle("-fx-text-fill: red;");
            new Alert(Alert.AlertType.WARNING, "Role must be 'Admin' or 'AdmissionsCoordinator'.").show();
            isValid = false;
        } else {
            txtUserRoll.setStyle("-fx-text-fill: black;");
        }


        return isValid;
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
