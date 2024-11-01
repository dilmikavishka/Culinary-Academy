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
import lk.ijse.BO.BoFactory;
import lk.ijse.BO.custom.UserBO;
import lk.ijse.DTO.UserDto;
import lk.ijse.Enum.Role;

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

    @FXML
    void btnSignInOnAction(ActionEvent event) {
        String email = txtUserEmail.getText();
        String username = txtUserName.getText();
        String password = txtUserPassword.getText();
        String role = txtUserRoll.getText();


        UserDto userDto = new UserDto(email, username, password, Role.valueOf(role));
        System.out.println(userDto);
        userBO.save(userDto);
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
