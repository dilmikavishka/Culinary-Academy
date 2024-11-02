package lk.ijse.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.BO.BoFactory;
import lk.ijse.BO.custom.CoursesBO;
import lk.ijse.BO.custom.RegistrationBO;
import lk.ijse.BO.custom.StudentBO;
import lk.ijse.DTO.CoursesDto;
import lk.ijse.DTO.RegistrationDTO;
import lk.ijse.DTO.StudentDto;
import lk.ijse.Enum.PaymentStatus;
import lk.ijse.Enum.Role;


import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;



public class RegistrationController implements Initializable {
    private boolean saveRegistration;
    StudentBO studentBO = (StudentBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.STUDENT);
    CoursesBO coursesBO = (CoursesBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.COURSES);

    RegistrationBO registrationBO = (RegistrationBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.REGISTRATION);

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
    private TableView<?> tblRegistration;

    public void SaveBtnOnaction(ActionEvent actionEvent) {
        Alert alert;
        if (saveRegistration){
            RegistrationDTO  registrationDTO = new RegistrationDTO();
            registrationDTO.setId(txtRegistrationId.getText());
            registrationDTO.setRegistrationDate(LocalDate.parse(txtRegistrationDate.getText()));
            registrationDTO.setPaymentAmount(BigDecimal.valueOf(Long.parseLong(txtPaymentAmount.getText())));
            StudentDto studentDto =studentBO.searchStudent(StudentComboBox.getValue());
            registrationDTO.setStudent(studentDto);
            CoursesDto coursesDto = coursesBO.searchCoursebyId(CourseComboBox.getValue());
            registrationDTO.setCourse(coursesDto);
            registrationDTO.setPaymentStatus(PaymentStatus.valueOf((PaidOnAction.isSelected() ? "Paid" : "Unpaid")));

            boolean isAddRegistrationWorked = registrationBO.save(registrationDTO);
            if (isAddRegistrationWorked){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Success");
                alert.setHeaderText("Registration Success");
                alert.setContentText("Registration Success");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Failed");
                alert.setHeaderText("Registration Failed");
                alert.setContentText("Registration Failed");
                alert.showAndWait();
            }


            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Access granted to Registration Privilege.");
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Access denied to Registration Privilege.");
        }
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setStudentIds();
        setCourseIDs();
        setRegistrationDate();
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
        System.out.println("Save Courses: " + saveRegistration);

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
    }

    public void StudentsWithCoursesBtnOnAction(ActionEvent actionEvent) {

    }
}
