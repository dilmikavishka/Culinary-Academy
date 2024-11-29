package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.BO.BoFactory;
import lk.ijse.BO.custom.CoursesBO;
import lk.ijse.BO.custom.RegistrationBO;
import lk.ijse.BO.custom.StudentBO;
import lk.ijse.DTO.CoursesDto;
import lk.ijse.DTO.RegistrationDTO;
import lk.ijse.DTO.StudentDto;
import lk.ijse.Entity.Student;
import lk.ijse.Enum.PaymentStatus;
import lk.ijse.Enum.Role;
import lk.ijse.Regex.RegexFactory;
import lk.ijse.Regex.RegexType;
import lk.ijse.Tm.RegistrationTm;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;



public class RegistrationController implements Initializable {
    public JFXButton savebutton;
    private boolean saveRegistration;
    StudentBO studentBO = (StudentBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.STUDENT);
    CoursesBO coursesBO = (CoursesBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.COURSES);

    RegistrationBO registrationBO = (RegistrationBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.REGISTRATION);

    private boolean isUpdateMode = false;
    public JFXComboBox<String> StudentComboBox;
    public JFXComboBox<String> CourseComboBox;
    public TextField txtRegistrationId;
    public TextField txtStudentName;
    public TextField txtCourseFee;
    public TextField txtRegistrationDate;
    public TextField txtPaymentAmount;

    @FXML
    private JFXRadioButton PaidOnAction;

    @FXML
    private TableColumn<?, ?> colCourse;

    @FXML
    private TableColumn<?, ?> colCourseFee;

    @FXML
    private TableColumn<?, ?> colPayment;

    @FXML
    private TableColumn<?, ?> colRegistrationDate;

    @FXML
    private TableColumn<?, ?> colRegistrationID;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colStudent;

    @FXML
    private TableView<RegistrationTm> tblRegistration;

    @FXML
    private TableColumn<?, ?> colBalance;

    @FXML
    private TextField txtBalance;

    private BigDecimal selectedCourseFee;

    public void SaveBtnOnaction(ActionEvent actionEvent) {
        if (!validateInput()) {
            return;
        }
        Alert alert;

        if (isUpdateMode) {
            RegistrationDTO UpregistrationDTO = new RegistrationDTO();
            UpregistrationDTO.setId(txtRegistrationId.getText());
            UpregistrationDTO.setRegistrationDate(LocalDate.parse(txtRegistrationDate.getText()));
            BigDecimal paymentAmountValue = new BigDecimal(txtPaymentAmount.getText());
            UpregistrationDTO.setPaymentAmount(paymentAmountValue);
            UpregistrationDTO.setBalanceToPay(new BigDecimal(txtCourseFee.getText()).subtract(paymentAmountValue));
            StudentDto studentDto = studentBO.searchStudent(StudentComboBox.getValue());
            UpregistrationDTO.setStudent(studentDto);
            CoursesDto coursesDto = coursesBO.searchCoursebyId(CourseComboBox.getValue());
            UpregistrationDTO.setCourse(coursesDto);
            UpregistrationDTO.setPaymentStatus(PaymentStatus.valueOf((PaidOnAction.isSelected() ? "Paid" : "Unpaid")));

            boolean isUpdateSuccess = registrationBO.update(UpregistrationDTO);

            if (isUpdateSuccess) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Update Success");
                alert.setHeaderText("Update Success");
                alert.setContentText("Registration successfully updated!");
                alert.showAndWait();
                getAll();
                clearFields();
                generateNextRegId();
                savebutton.setText("Register");

                isUpdateMode = false;
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Update Failed");
                alert.setHeaderText("Update Failed");
                alert.setContentText("Failed to update registration.");
                alert.showAndWait();
            }
        } else {
            if (saveRegistration) {
                RegistrationDTO registrationDTO = new RegistrationDTO();
                registrationDTO.setId(txtRegistrationId.getText());
                registrationDTO.setRegistrationDate(LocalDate.parse(txtRegistrationDate.getText()));
                BigDecimal paymentAmountValue = BigDecimal.valueOf(Long.parseLong(txtPaymentAmount.getText()));
                registrationDTO.setPaymentAmount(paymentAmountValue);
                registrationDTO.setBalanceToPay(new BigDecimal(txtCourseFee.getText()).subtract(paymentAmountValue));
                StudentDto studentDto = studentBO.searchStudent(StudentComboBox.getValue());
                registrationDTO.setStudent(studentDto);
                CoursesDto coursesDto = coursesBO.searchCoursebyId(CourseComboBox.getValue());
                registrationDTO.setCourse(coursesDto);
                registrationDTO.setPaymentStatus(PaymentStatus.valueOf((PaidOnAction.isSelected() ? "Paid" : "Unpaid")));

                boolean isAddRegistrationWorked = registrationBO.save(registrationDTO);
                if (isAddRegistrationWorked) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registration Success");
                    alert.setHeaderText("Registration Success");
                    alert.setContentText("Registration Success");
                    alert.showAndWait();
                    clearFields();
                    getAll();
                    generateNextRegId();

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Registration Failed");
                    alert.setHeaderText("Registration Failed");
                    alert.setContentText("Registration Failed");
                    alert.showAndWait();
                }
            } else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Access denied to Registration Privilege.");
                alert.show();
            }
        }

        isUpdateMode = false;

    }

    private void clearFields() {
        txtStudentName.clear();
        txtCourseFee.clear();
        txtPaymentAmount.clear();
        txtBalance.clear();
        PaidOnAction.setSelected(false);

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private boolean validateInput() {
        RegexFactory regexFactory = RegexFactory.getInstance();
        if (!regexFactory.getPattern(RegexType.STUDENT_ID).matcher(StudentComboBox.getValue()).matches()) {
            showAlert("Invalid Student ID", "Student ID format is incorrect.");
            return false;
        }


        if (!regexFactory.getPattern(RegexType.COURSE_ID).matcher(CourseComboBox.getValue()).matches()) {
            showAlert("Invalid Course ID", "Course ID format is incorrect.");
            return false;
        }


        try {
            BigDecimal paymentAmount = new BigDecimal(txtPaymentAmount.getText());
            if (paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
                showAlert("Invalid Payment Amount", "Payment amount must be greater than zero.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Payment Amount", "Payment amount must be a valid number.");
            return false;
        }


        try {
            LocalDate.parse(txtRegistrationDate.getText());
        } catch (Exception e) {
            showAlert("Invalid Date", "Please enter a valid registration date.");
            return false;
        }

        return true;
    }


    private void generateNextRegId() {
        String regId = registrationBO.generateNextIdRegistration();
        txtRegistrationId.setText(regId);
    }

    private void getAll() {
        List<RegistrationDTO> registrationDTO = registrationBO.getAllRegistrations();
        ObservableList<RegistrationTm> obList = FXCollections.observableArrayList();
        for (RegistrationDTO dto : registrationDTO){
            obList.add(
                    new RegistrationTm(
                            dto.getId(),
                            dto.getStudent().getStudent_ID(),

                            dto.getCourse().getCourseId(),
                            dto.getRegistrationDate(),
                            dto.getPaymentAmount(),
                            dto.getPaymentStatus(),
                            dto.getCourse().getFee(),
                            dto.getBalanceToPay()

                    )
            );
            tblRegistration.setItems(obList);
        }
    }

    private void setCellValueFactory(){
        colRegistrationID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRegistrationDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        colStudent.setCellValueFactory(new PropertyValueFactory<>("student"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colCourseFee.setCellValueFactory(new PropertyValueFactory<>("courseFee"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balanceToPay"));
    }

    private void setRegistrationDate() {
        txtRegistrationDate.setText(String.valueOf(LocalDate.now()));
    }

    private void setCourseIDs() {
        List<CoursesDto> coursesDtoList = coursesBO.getAllCourses();
        ObservableList<String> ids = FXCollections.observableArrayList();
        for (CoursesDto coursesDto : coursesDtoList) {
            ids.add(coursesDto.getCourseId());
        }
        CourseComboBox.setItems(ids);
    }

    private void setStudentIds() {
        List<StudentDto> allStudent = studentBO.getAllStudents();
        ObservableList<String> ids = FXCollections.observableArrayList();
        for (StudentDto studentDTO : allStudent) {
            ids.add(studentDTO.getStudent_ID());
        }
        StudentComboBox.setItems(ids);
    }

    public void setRolePermissions(Role currentRole) {
        if (currentRole != null){
            System.out.println("Role selected in registration form: " + currentRole);
            switch (currentRole){
                case Admin:
                    saveRegistration = true;
                    break;
                case AdmissionsCoordinator:
                    saveRegistration = false;
                    break;
                default:
                   saveRegistration = false;
            }
        }


    }

    public void RegStudentComboBoxOnAction(ActionEvent actionEvent) {
        String id = StudentComboBox.getValue();
        StudentDto studentDto = studentBO.searchStudent(id);
        txtStudentName.setText(studentDto.getStudent_Name());
    }

    public void RegCourseComboBoxOnAction(ActionEvent actionEvent) {
        String id = CourseComboBox.getValue();
        CoursesDto  coursesDto = coursesBO.searchCoursebyId(id);
        txtCourseFee.setText(String.valueOf(coursesDto.getFee()));
        selectedCourseFee = BigDecimal.valueOf(coursesDto.getFee());
    }
    public void rowOnMouseClicked(MouseEvent mouseEvent) {
        int index = tblRegistration.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        RegistrationTm selectedRegistration = tblRegistration.getSelectionModel().getSelectedItem();

        if (selectedRegistration != null) {
            txtRegistrationId.setText(selectedRegistration.getId());
            StudentComboBox.setValue(selectedRegistration.getStudent());
            CourseComboBox.setValue(selectedRegistration.getCourse());
            txtRegistrationDate.setText(selectedRegistration.getRegistrationDate().toString());
            txtCourseFee.setText(String.valueOf(selectedRegistration.getBalanceToPay()));

            isUpdateMode = true;
            savebutton.setText("Update");
            selectedCourseFee = selectedRegistration.getBalanceToPay();

            System.out.println("Row clicked - Update Mode Activated");
        }
    }



    public void StudentsWithCoursesBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/StudentsAlongCourseForm.fxml"))));
        stage.show();
        stage.centerOnScreen();
        stage.setTitle("Student Along Course Form");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setStudentIds();
        setCourseIDs();
        getAll();
        setRegistrationDate();
        setCellValueFactory();
        generateNextRegId();
        txtPaymentAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                BigDecimal paymentAmount = newValue.isEmpty() ? BigDecimal.ZERO : new BigDecimal(newValue);

                if (selectedCourseFee != null) {
                    BigDecimal balance = selectedCourseFee.subtract(paymentAmount);
                    txtBalance.setText(balance.toString());
                }
            } catch (NumberFormatException e) {
                if (selectedCourseFee != null) {
                    txtBalance.setText(selectedCourseFee.toString());
                } else {
                    txtBalance.setText("0.00");
                }
            }
        });
    }

    public void StudentsWhoDoAllCourses(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/StudentWhoDoFive.fxml"))));
        stage.show();
        stage.centerOnScreen();
        stage.setTitle("Student Along Course Form");
    }

    public void DeleteBtnOnaction(ActionEvent actionEvent) {
        RegistrationTm selectedRegistration = tblRegistration.getSelectionModel().getSelectedItem();

        if (selectedRegistration == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Registration Selected");
            alert.setContentText("Please select a registration to delete.");
            alert.showAndWait();
            return;
        }


        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete this registration?");
        confirmationAlert.setContentText("Registration ID: " + selectedRegistration.getId());
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response.getText().equalsIgnoreCase("OK")) {

                boolean isDeleted = registrationBO.delete(selectedRegistration.getId());

                if (isDeleted) {

                    getAll();


                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Deletion Successful");
                    successAlert.setHeaderText("Registration Deleted");
                    successAlert.setContentText("Registration with ID " + selectedRegistration.getId() + " has been deleted.");
                    successAlert.showAndWait();
                } else {

                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Deletion Failed");
                    errorAlert.setHeaderText("Unable to Delete Registration");
                    errorAlert.setContentText("An error occurred while trying to delete the registration.");
                    errorAlert.showAndWait();
                }
            }
        });
    }
}
