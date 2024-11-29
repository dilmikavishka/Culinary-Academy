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
import lk.ijse.Entity.User;
import lk.ijse.Enum.Role;
import lk.ijse.Regex.RegexFactory;
import lk.ijse.Regex.RegexType;
import org.eclipse.jdt.internal.compiler.batch.Main;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginFormController {
    UserBO  userBO = (UserBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.USER);

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


    RegexFactory regexFactory = RegexFactory.getInstance();

    private static void validateField(String fieldName, String fieldValue, Pattern pattern) {
        boolean matches = pattern.matcher(fieldValue).matches();
        System.out.println(fieldName + " (" + fieldValue + ") validation: " + (matches ? "Valid" : "Invalid"));
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        String username = txtLogUserName.getText();
        String password = txtLogUserPassword.getText();

        boolean isAuthorized = userBO.check(username, password);
        if (isAuthorized) {
            UserDto authorizedUser = userBO.checkifUserExsist(username, password);

            if (authorizedUser != null) {
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/DashBoardForm.fxml"));
                loader2.load();


                DashBoardFormController dashboardFormController = loader2.getController();
                dashboardFormController.setuserDetail(authorizedUser);


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainForm.fxml"));
                Parent mainFormRoot = loader.load();
                MainFormController mainFormController = loader.getController();
                mainFormController.setRolePermissions(authorizedUser.getRole());
                mainFormController.setuserDetail(authorizedUser);
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(new Scene(mainFormRoot));
                stage.show();
                stage.centerOnScreen();

                new Alert(Alert.AlertType.CONFIRMATION, "Login Successful!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password.").show();
        }
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
