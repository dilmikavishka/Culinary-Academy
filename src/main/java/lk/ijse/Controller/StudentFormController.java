package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.BO.BoFactory;
import lk.ijse.BO.custom.StudentBO;
import lk.ijse.DTO.StudentDto;
import lk.ijse.Entity.Student;
import lk.ijse.Enum.Role;
import lk.ijse.Regex.RegexFactory;
import lk.ijse.Regex.RegexType;
import lk.ijse.Tm.StudentTm;
import org.hibernate.sql.results.graph.Initializer;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {
    StudentBO studentBO = (StudentBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.STUDENT);
    private Role currentRole;
    private boolean saveStudent;
    private boolean updateStudent;
    private boolean deleteStudent;
    @FXML
    private TextField txtStudentAddress;

    @FXML
    private TextField txtStudentContact;

    @FXML
    private TextField txtStudentEmail;

    @FXML
    private TextField txtStudentId;

    @FXML
    private Label txtStudentJoined;

    @FXML
    private TextField txtStudentName;

    @FXML
    private TableView<StudentTm> TableStudents;

    @FXML
    private TableColumn<?, ?> colStudentAddress;

    @FXML
    private TableColumn<?, ?> colStudentContact;

    @FXML
    private TableColumn<?, ?> colStudentEmail;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colStudentJoinDate;

    @FXML
    private TableColumn<?, ?> colStudentName;

    RegexFactory regexFactory = RegexFactory.getInstance();

    private boolean validateInput() {
        boolean isValid = true;
        String name = txtStudentName.getText().trim();
        String address = txtStudentAddress.getText().trim();
        String contact = txtStudentContact.getText().trim();
        String email = txtStudentEmail.getText().trim();

        if (!regexFactory.getPattern(RegexType.EMAIL).matcher(email).matches()) {
            txtStudentEmail.setStyle("-fx-text-fill: red; -fx-background-color: transparent; -fx-border-color: #FDFCFC; -fx-border-width: 0px 0px 1px 0px;");
            new Alert(Alert.AlertType.WARNING, "Please enter a valid email.(@gmail.com or @outlook.com)").show();
            isValid = false;
        } else {
            txtStudentEmail.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: #FDFCFC; -fx-border-width: 0px 0px 1px 0px;");
        }

        if (!regexFactory.getPattern(RegexType.STUDENT_NAME).matcher(name).matches()) {
            txtStudentName.setStyle("-fx-text-fill: red; -fx-background-color: transparent; -fx-border-color: #FDFCFC; -fx-border-width: 0px 0px 1px 0px;");
            new Alert(Alert.AlertType.WARNING, " should contain only letters.").show();
            isValid = false;
        } else {
            txtStudentName.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: #FDFCFC; -fx-border-width: 0px 0px 1px 0px;");
        }

        if (!regexFactory.getPattern(RegexType.ADDRESS).matcher(address).matches()) {
            txtStudentAddress.setStyle("-fx-text-fill: red; -fx-background-color: transparent; -fx-border-color: #FDFCFC; -fx-border-width: 0px 0px 1px 0px;");
            new Alert(Alert.AlertType.WARNING, "Address must have more than 5 characters").show();
            isValid = false;
        } else {
            txtStudentAddress.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: #FDFCFC; -fx-border-width: 0px 0px 1px 0px;");
        }

        if (!regexFactory.getPattern(RegexType.PHONE_NUMBER).matcher(contact).matches()) {
            txtStudentContact.setStyle("-fx-text-fill: red; -fx-background-color: transparent; -fx-border-color: #FDFCFC; -fx-border-width: 0px 0px 1px 0px;");
            new Alert(Alert.AlertType.WARNING, "Phone Number must be 10 characters.").show();
            isValid = false;
        } else {
            txtStudentContact.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: #FDFCFC; -fx-border-width: 0px 0px 1px 0px;");
        }

        return isValid;
    }


    public void setRolePermissions(Role role) {
        this.currentRole = role;
        if (role != null){
            System.out.println("Role received to StudentFrom: " + role);
            switch (role){
                case AdmissionsCoordinator:
                    saveStudent  = true;
                    updateStudent = false;
                    deleteStudent = false;
                    break;
                case Admin:
                    saveStudent = true;
                    updateStudent = true;
                    deleteStudent = true;
                    break;
                default:
                    saveStudent = false;
                    updateStudent = false;
                    deleteStudent = false;
            }
        }
        System.out.println("saveStudent: " + saveStudent + ", updateStudent: " + updateStudent + ", deleteStudent: " + deleteStudent);
    }

    private void clearFields() {
        txtStudentName.setText("");
        txtStudentAddress.setText("");
        txtStudentContact.setText("");
        txtStudentEmail.setText("");

    }

    public void SaveBtnOnaction(ActionEvent actionEvent) {
        if (validateInput()) {
            Alert alert;
            if (saveStudent) {
                String id = txtStudentId.getText();
                String name = txtStudentName.getText();
                String address = txtStudentAddress.getText();
                String contact = txtStudentContact.getText();
                String email = txtStudentEmail.getText();
                LocalDate joinedDate = LocalDate.now();
                StudentDto studentDto = new StudentDto(id, name, address, contact, email, joinedDate);
                boolean issaved = studentBO.saveStudent(studentDto);
                if (issaved) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Student Saved");
                    alert.setContentText("Student Saved");
                    alert.showAndWait();
                    getAll();
                    generateStudentNextId();
                    clearFields();
                }
                System.out.println("granted");
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Access granted to Student section.");
            } else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Access denied to Student section.");
            }
            alert.show();
        }
    }

    public void UpdateBtnOnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            Alert alert;
            if (updateStudent) {
                String id = txtStudentId.getText();
                String name = txtStudentName.getText();
                String address = txtStudentAddress.getText();
                String contact = txtStudentContact.getText();
                String email = txtStudentEmail.getText();
                LocalDate joinedDate = LocalDate.now();
                StudentDto studentDto = new StudentDto(id, name, address, contact, email, joinedDate);
                boolean isUpdated = studentBO.updateStudent(studentDto);
                if (isUpdated) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Student Updated");
                    alert.setContentText("Student Updated");
                    alert.showAndWait();
                    generateStudentNextId();
                    getAll();
                    clearFields();
                }
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Access granted to Student Update Privilege.");
            } else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Access denied to  Student Update Privilege.");
            }
            alert.show();
        }
    }

    public void DeleteBtnOnAction(ActionEvent actionEvent) {
        Alert alert;
        if (deleteStudent) {
            StudentTm selectedItem = TableStudents.getSelectionModel().getSelectedItem();

            if (selectedItem != null) {
                try {
                    boolean isDeleted = studentBO.deleteStudent(selectedItem.getStudent_ID());

                    if (isDeleted) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Student deleted successfully!").show();
                        getAll();
                        generateStudentNextId();
                        clearFields();
                        setCellValueFactory();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the student.").show();
                    }
                } catch (Exception e) {
                    if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                        new Alert(
                                Alert.AlertType.ERROR,
                                "Cannot delete this student as they are referenced in other records (e.g., course registrations)."
                        ).show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage()).show();
                    }
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please select a student to delete!").show();
            }

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Access granted to Student Delete Privilege.");
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Access denied to Student Delete Privilege.");
        }
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        generateStudentNextId();
        setCellValueFactory();
    }

    private void generateStudentNextId() {
        String id = studentBO.generateNextIdStudent();
        txtStudentId.setText(String.valueOf(id));
    }

    private void setCellValueFactory() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("Student_ID"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("Student_Name"));
        colStudentAddress.setCellValueFactory(new PropertyValueFactory<>("Student_Address"));
        colStudentContact.setCellValueFactory(new PropertyValueFactory<>("Student_Phone"));
        colStudentEmail.setCellValueFactory(new PropertyValueFactory<>("Student_Email"));
        colStudentJoinDate.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));
    }


    private void getAll() {
        ObservableList<StudentTm> studentTmObservableList = FXCollections.observableArrayList();
        List<StudentDto> studentDtoList = studentBO.getAllStudents();
        for (StudentDto studentDto : studentDtoList) {
            StudentTm studentTm = new StudentTm(
                    studentDto.getStudent_ID(),
                    studentDto.getStudent_Name(),
                    studentDto.getStudent_Address(),
                    studentDto.getStudent_Phone(),
                    studentDto.getStudent_Email(),
                    studentDto.getJoinedDate()
            );
            studentTmObservableList.add(studentTm);
            TableStudents.setItems(studentTmObservableList);

        }
    }

    public void rowOnMouseClicked(MouseEvent mouseEvent) {
        Integer index = TableStudents.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtStudentId.setText(colStudentId.getCellData(index).toString());
        txtStudentName.setText(colStudentName.getCellData(index).toString());
        txtStudentAddress.setText(colStudentAddress.getCellData(index).toString());
        txtStudentContact.setText(colStudentContact.getCellData(index).toString());
        txtStudentEmail.setText(colStudentEmail.getCellData(index).toString());
        txtStudentJoined.setText(colStudentJoinDate.getCellData(index).toString());
    }
}
