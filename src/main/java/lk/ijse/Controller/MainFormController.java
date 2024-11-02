package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.Enum.Role;

import java.io.IOException;

public class MainFormController {
    private Role currentRole;
    private boolean userAllowed;
    private boolean coursesAllowed;

    private boolean studentAllowed;
    private boolean registrationAllowed;

    @FXML
    private AnchorPane anpMain1;

    @FXML
    private AnchorPane anpMain2;

    @FXML
    private Text txtDashBoard;

    public void setRolePermissions(Role role) {
        this.currentRole = role;
        if (role != null){
            System.out.println("Role received: " + role);
            switch (role){
                case AdmissionsCoordinator:
                    userAllowed = false;
                    studentAllowed = true;
                    coursesAllowed = true;
                    registrationAllowed = true;
                    break;
                case Admin:
                    userAllowed = true;
                    studentAllowed = true;
                    coursesAllowed = true;
                    registrationAllowed = true;
                    break;
                default:
                    userAllowed = false;
                    studentAllowed = false;
                    coursesAllowed = false;
                    registrationAllowed = false;
            }
        }
        System.out.println("userAllowed: " + userAllowed + ", coursesAllowed: " + studentAllowed + ", coursesAllowed: " + coursesAllowed);
    }


    @FXML
    void txtDashBoardOnMouseClicked(MouseEvent event) throws IOException {
        loadDashboardForm();
    }

    public void initialize() throws IOException {
        loadDashboardForm();
    }

    private void loadDashboardForm() throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashBoardForm.fxml"));
        anpMain2.getChildren().clear();
        anpMain2.getChildren().add(dashboardPane);
    }

    public void btnUserOnAction(ActionEvent actionEvent) {
        Alert alert;
        if (userAllowed){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Access granted to User section.");
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Access denied to User section.");
        }
        alert.show();
    }

    public void btnStudentOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert;
        if (studentAllowed) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Access granted to Student section.");

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/StudentForm.fxml"));
            AnchorPane studentFormPane = loader.load();

            StudentFormController studentFormController = loader.getController();
            studentFormController.setRolePermissions(currentRole);

            anpMain2.getChildren().clear();
            anpMain2.getChildren().add(studentFormPane);
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Access denied to Student section.");
        }
        alert.show();
    }

    @FXML
    void btnCourseOnAction(ActionEvent event) throws IOException {
        Alert alert;
        if (coursesAllowed){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Access granted to Courses section.");

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/CoursesForm.fxml"));
            AnchorPane coursesFormAnp = loader.load();

            CoursesFormController coursesFormController = loader.getController();
            coursesFormController.setRolePermissions(currentRole);

            anpMain2.getChildren().clear();
            anpMain2.getChildren().add(coursesFormAnp);
        }else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Access denied to Courses section.");
        }
        alert.show();

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {

    }

    @FXML
    void btnRegistrationOnAction(ActionEvent event) throws IOException {
        Alert alert;
        if (registrationAllowed){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Access granted to Courses section.");

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/RegistrationForm.fxml"));
            AnchorPane registrationFormAnp = loader.load();

            RegistrationController registrationController = loader.getController();
            registrationController.setRolePermissions(currentRole);

            anpMain2.getChildren().clear();
            anpMain2.getChildren().add(registrationFormAnp);
        }else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Access denied to Courses section.");
        }
        alert.show();
    }

}


