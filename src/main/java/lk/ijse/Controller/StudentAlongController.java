package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.BO.BoFactory;
import lk.ijse.BO.custom.RegistrationBO;
import lk.ijse.DTO.StudentCourseDTO;
import lk.ijse.Tm.StudentCourseTm;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentAlongController implements Initializable {
    RegistrationBO registrationBO = (RegistrationBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.REGISTRATION);
    public TableView<StudentCourseTm> tblStudentAlongCourse;
    public TableColumn<?,?> colstudentId;
    public TableColumn<?,?> colstudentName;
    public TableColumn<?,?> colcousreId;
    public TableColumn<?,?> colcourseName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colstudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colstudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colcousreId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colcourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
    }

    private void getAll() {
        List<StudentCourseDTO> studentCourseDTOS = registrationBO.getAllStudentsAlongTheirCourses();
        ObservableList<StudentCourseTm> studentCourseList = FXCollections.observableArrayList();
        for(StudentCourseDTO studentCourseDTO : studentCourseDTOS){
           studentCourseList.add(new StudentCourseTm(
                   studentCourseDTO.getStudentId(),
                   studentCourseDTO.getStudentName(),
                   studentCourseDTO.getCourseId(),
                   studentCourseDTO.getCourseName()
           ));
           tblStudentAlongCourse.setItems(studentCourseList);
        }
    }


}
