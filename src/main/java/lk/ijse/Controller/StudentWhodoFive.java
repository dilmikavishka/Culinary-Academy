package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.BO.BoFactory;
import lk.ijse.BO.custom.RegistrationBO;
import lk.ijse.DTO.FiveStuDto;
import lk.ijse.Tm.FiveStuTm;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentWhodoFive implements Initializable {

    @FXML
    private TableView<FiveStuTm> fiveCoursetable;

    @FXML
    private TableColumn<FiveStuTm, String> StudentID;

    private RegistrationBO registrationBO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registrationBO = (RegistrationBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.REGISTRATION);
        getAllStudentsRegisteredForAllCourses();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        StudentID.setCellValueFactory(new PropertyValueFactory<>("studentId"));
    }

    private void getAllStudentsRegisteredForAllCourses() {
        List<FiveStuDto> studentIds = registrationBO.getStudentsRegisteredForAllCourses();
        ObservableList<FiveStuTm> fiveStuTmList = FXCollections.observableArrayList();
        for (FiveStuDto studentDto : studentIds) {
            fiveStuTmList.add(new FiveStuTm(studentDto.getStudentId()));
        }
        fiveCoursetable.setItems(fiveStuTmList);
    }
}