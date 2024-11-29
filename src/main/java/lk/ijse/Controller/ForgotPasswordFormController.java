package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.BO.BoFactory;
import lk.ijse.BO.custom.UserBO;
import util.Mail;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ForgotPasswordFormController implements Initializable {

    private String generatedOtp;
    public TextField txtOtpEmail;
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

    private boolean otpSent = false;
    private boolean NewPass = false;
    private boolean ConfirmPass = false;

    UserBO userBO = (UserBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.USER);

    @FXML
    void btnResetOnAction(ActionEvent event) throws IOException {
        String email = txtOtpEmail.getText();
        String RePassword = txtNewPassword.getText();
        String ConfirmPassword = txtConfirmPassword.getText();

        boolean isConfirm = userBO.isUpdatedPassword(email,ConfirmPassword);
        if (isConfirm) {
            Stage currentStage = (Stage) anpForgotPassword.getScene().getWindow();
            currentStage.close();
            Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
            Scene scene = new Scene(rootNode);
            Stage loginStage = new Stage();
            loginStage.setScene(scene);
            loginStage.setTitle("Login Form");
            loginStage.show();
        } else {
            Alert alert =  new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Password Mismatch");
            alert.setHeaderText("Password Mismatch");
            alert.setContentText("Password Mismatch");
            alert.show();
        }


    }

    @FXML
    void btnSendOtpOnAction(ActionEvent event) {
        if (!otpSent) {
            int otp = generateOtp();
            Mail mail = new Mail();
            mail.setTo(txtOtpEmail.getText());
            mail.setSubject("Your OTP Code");
            mail.setMsg("Your OTP is: " + otp);

            Thread mailThread = new Thread(mail);
            mailThread.start();

            txtOtp.setDisable(false);
            btnSendOtp.setText("Verify OTP");
            otpSent = true;

            generatedOtp = String.valueOf(otp);

        } else {
            String enteredOtp = txtOtp.getText();
            if (generatedOtp.equals(enteredOtp)) {
                txtNewPassword.setDisable(false);
                txtConfirmPassword.setDisable(false);
                System.out.println("OTP Verified Successfully!");
                btnSendOtp.setText("OTP Verified");
                btnSendOtp.setDisable(true);
            } else {
                System.out.println("Invalid OTP. Please try again.");
            }
        }
    }

    private int generateOtp() {
        Random rand = new Random();
        return 100000 + rand.nextInt(900000);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtOtp.setDisable(true);
        txtNewPassword.setDisable(true);
        txtConfirmPassword.setDisable(true);
    }
}
