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
import lk.ijse.Tm.CoursesTm;
import lk.ijse.Tm.StudentTm;


import java.net.URL;
import java.sql.SQLException;
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
            System.out.println("Student Details: " + coursesTm);

        }
    }

    @FXML
    void SaveBtnOnaction(ActionEvent event) {
        Alert alert;
        if (saveCourses){
            String courseId = txtCourseId.getText();
            String courseName = txtCourseName.getText();
            String duration = txtCourseDuration.getText();
            double fee = Double.parseDouble(txtCourseFee.getText());

            CoursesDto coursesDto = new CoursesDto(courseId,courseName,duration,fee);
            boolean isSaved = coursesBO.saveCourses(coursesDto);
            if (isSaved){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Course Saved");
                alert.setContentText("Course Saved");
                alert.showAndWait();
                clearFields();
            }
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Access granted to Course section.");

        }else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Access denied to Course section.");
        }
        alert.show();

    }

    private void clearFields() {
        txtCourseId.setText("");
        txtCourseName.setText("");
        txtCourseDuration.setText("");
        txtCourseFee.setText("");
    }

    @FXML
    void UpdateBtnOnAction(ActionEvent event) {
        Alert alert;
        if (updateCourses){
            String courseId = txtCourseId.getText();
            String courseName = txtCourseName.getText();
            String duration =txtCourseDuration.getText();
            double fee = Double.parseDouble(txtCourseFee.getText());

            CoursesDto coursesDto = new CoursesDto(courseId,courseName,duration,fee);
            boolean isUpdate = coursesBO.updateCourses(coursesDto);
            if (isUpdate){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Course Update");
                alert.setContentText("Course Update");
                alert.showAndWait();
                setCellValueFactory();
                getAll();
                clearFields();
            }
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Access granted to Course section.");

        }else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Access denied to Course section.");
        }
        alert.show();
    }

    @FXML
    void DeleteBtnOnAction(ActionEvent event) {
        Alert alert;
        if (deleteCourses){
            CoursesTm selectedItem = TableCourses.getSelectionModel().getSelectedItem();
            if (selectedItem != null){
                boolean isDeleted = coursesBO.deleteCourse(selectedItem.getCourseId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Course deleted!...").show();
                    getAll();
                    setCellValueFactory();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Course not deleted   !...").show();
                }
            }
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Access granted to  Course Delete Privilege.");
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
