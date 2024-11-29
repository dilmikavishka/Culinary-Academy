package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.BO.BoFactory;
import lk.ijse.BO.custom.CoursesBO;
import lk.ijse.DTO.CoursesDto;
import lk.ijse.Enum.Role;
import lk.ijse.Regex.RegexFactory;
import lk.ijse.Regex.RegexType;
import lk.ijse.Tm.CoursesTm;



import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class CoursesFormController implements Initializable {

    CoursesBO coursesBO = (CoursesBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.COURSES);

    @FXML
    private TableView<CoursesTm> TableCourses;

    @FXML
    private AnchorPane anpCourse;

    @FXML
    private TableColumn<?, ?> colCoursesDuration;

    @FXML
    private TableColumn<?, ?> colCoursesFee;

    @FXML
    private TableColumn<?, ?> colCoursesId;

    @FXML
    private TableColumn<?, ?> colCoursesName;

    @FXML
    private TextField txtCourseDuration;

    @FXML
    private TextField txtCourseFee;

    @FXML
    private TextField txtCourseId;

    @FXML
    private TextField txtCourseName;

    private Role currentRole;
    private boolean saveCourses;
    private boolean updateCourses;
    private boolean deleteCourses;

    RegexFactory regexFactory = RegexFactory.getInstance();

    private boolean validateInput() {
        boolean isValid = true;
        String cName = txtCourseName.getText().trim();
        String cFeeString = txtCourseFee.getText().trim();


        if (!regexFactory.getPattern(RegexType.COURSE_NAME).matcher(cName).matches()) {
            txtCourseName.setStyle("-fx-text-fill: red; -fx-background-color: transparent; -fx-border-color: #FDFCFC; -fx-border-width: 0px 0px 1px 0px;");
            new Alert(Alert.AlertType.WARNING, "Please enter a valid Name").show();
            isValid = false;
        } else {
            txtCourseName.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: #FDFCFC; -fx-border-width: 0px 0px 1px 0px;");
        }

        if (!regexFactory.getPattern(RegexType.COURSE_FEE).matcher(cFeeString).matches()) {
            txtCourseFee.setStyle("-fx-text-fill: red; -fx-background-color: transparent; -fx-border-color: #FDFCFC; -fx-border-width: 0px 0px 1px 0px;");
            new Alert(Alert.AlertType.WARNING, " should contain only letters.").show();
            isValid = false;
        } else {
            txtCourseFee.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: #FDFCFC; -fx-border-width: 0px 0px 1px 0px;");
            try {
                double cFee = Double.parseDouble(cFeeString);
                txtCourseFee.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: #FDFCFC; -fx-border-width: 0px 0px 1px 0px;");
            } catch (NumberFormatException e) {
                txtCourseFee.setStyle("-fx-text-fill: red; -fx-background-color: transparent; -fx-border-color: #FDFCFC; -fx-border-width: 0px 0px 1px 0px;");
                new Alert(Alert.AlertType.WARNING, "Invalid number format for course fee.").show();
                isValid = false;
            }
        }

        return isValid;
    }

    public void setRolePermissions(Role role) {
        this.currentRole = role;
        if (role != null){
            System.out.println("Role selected: " + role);
            switch (role){
                case Admin:
                    saveCourses = true;
                    updateCourses = true;
                    deleteCourses = true;
                    break;
                case AdmissionsCoordinator:
                    saveCourses = true;
                    updateCourses = false;
                    deleteCourses = false;
                    break;
                default:
                    saveCourses = false;
                    updateCourses = false;
                    deleteCourses = false;
            }
        }
        System.out.println("Save Courses: " + saveCourses+"Update Course :"+updateCourses+"Delete Courses :"+deleteCourses);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        generatCoursesNextId();
        setCellValueFactory();
    }
    private void setCellValueFactory() {
        colCoursesId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCoursesName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colCoursesDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colCoursesFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    private void generatCoursesNextId() {
        String id = coursesBO.generateNextIdCourse();
        txtCourseId.setText(String.valueOf(id));

    }

    private void getAll() {
        ObservableList<CoursesTm> studentTmObservableList = FXCollections.observableArrayList();
        List<CoursesDto> coursesDtoList = coursesBO.getAllCourses();
        for (CoursesDto coursesDto : coursesDtoList) {
            CoursesTm coursesTm = new CoursesTm(
                    coursesDto.getCourseId(),
                    coursesDto.getCourseName(),
                    coursesDto.getDuration(),
                    coursesDto.getFee()
            );
            studentTmObservableList.add(coursesTm);
            TableCourses.setItems(studentTmObservableList);
//            System.out.println("Courses Details: " + coursesTm);

        }
    }

    @FXML
    void SaveBtnOnaction(ActionEvent event) {
        if (validateInput()) {
            Alert alert;
            if (saveCourses) {
                String courseId = txtCourseId.getText();
                String courseName = txtCourseName.getText();
                String duration = txtCourseDuration.getText();
                double fee = Double.parseDouble(txtCourseFee.getText());

                CoursesDto coursesDto = new CoursesDto(courseId, courseName, duration, fee);
                boolean isSaved = coursesBO.saveCourses(coursesDto);
                if (isSaved) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Course Saved");
                    alert.setContentText("Course Saved");
                    alert.showAndWait();
                    getAll();
                    clearFields();
                    generatCoursesNextId();
                }
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Access granted to Course section.");

            } else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Access denied to Course section.");
            }
            alert.show();
        }

    }

    private void clearFields() {
        txtCourseName.setText("");
        txtCourseDuration.setText("");
        txtCourseFee.setText("");
    }

    @FXML
    void UpdateBtnOnAction(ActionEvent event) {
        if (validateInput()) {
            Alert alert;
            if (updateCourses) {
                String courseId = txtCourseId.getText();
                String courseName = txtCourseName.getText();
                String duration = txtCourseDuration.getText();
                double fee = Double.parseDouble(txtCourseFee.getText());

                CoursesDto coursesDto = new CoursesDto(courseId, courseName, duration, fee);
                boolean isUpdate = coursesBO.updateCourses(coursesDto);
                if (isUpdate) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Course Update");
                    alert.setContentText("Course Update");
                    alert.showAndWait();
                    setCellValueFactory();
                    getAll();
                    clearFields();
                    generatCoursesNextId();
                }
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Access granted to Course section.");

            } else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Access denied to Course section.");
            }
            alert.show();
        }
    }

    @FXML
    void DeleteBtnOnAction(ActionEvent event) {
        Alert alert;
        if (deleteCourses) {
            CoursesTm selectedItem = TableCourses.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    boolean isDeleted = coursesBO.deleteCourse(selectedItem.getCourseId());
                    if (isDeleted) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Course deleted!").show();
                        getAll();
                        setCellValueFactory();
                        clearFields();
                        generatCoursesNextId();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Course not deleted!").show();
                    }
                } catch (Exception e) {
                    if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                        new Alert(
                                Alert.AlertType.ERROR,
                                "Cannot delete this course as it is referenced in other records (e.g., registrations)."
                        ).show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage()).show();
                    }
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "No course selected for deletion!").show();
            }
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Access granted to Course Delete Privilege.");
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Access denied to Course Delete Privilege.");
        }
        alert.show();
    }

    @FXML
    void rowOnMouseClicked(MouseEvent event) {
        Integer index = TableCourses.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtCourseId.setText(colCoursesId.getCellData(index).toString());
        txtCourseName.setText(colCoursesName.getCellData(index).toString());
        txtCourseDuration.setText(colCoursesDuration.getCellData(index).toString());
        txtCourseFee.setText(colCoursesFee.getCellData(index).toString());

    }


}
