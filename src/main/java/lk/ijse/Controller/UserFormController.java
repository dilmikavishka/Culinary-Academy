package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.BO.BoFactory;
import lk.ijse.BO.custom.UserBO;
import lk.ijse.DTO.UserDto;
import lk.ijse.Enum.Role;
import lk.ijse.Tm.UserTm;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class UserFormController implements Initializable {
    UserBO userBO = (UserBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.USER);

    @FXML
    private AnchorPane anpUsers;

    @FXML
    private TableColumn<?, ?> colUserEmail;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private TableColumn<?, ?> colUserPassword;

    @FXML
    private TableColumn<?, ?> colUserRoll;

    @FXML
    private TableView<UserTm> tblUsers;

    @FXML
    private TextField txtUserEmail;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtUserPassword;

    @FXML
    private TextField txtUserRoll;

    private Role currentRole;
    private boolean saveUser;
    private boolean updateUser;
    private boolean deleteUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colUserRoll.setCellValueFactory(new PropertyValueFactory<>("role"));
        colUserEmail.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    private void getAll() {
        ObservableList<UserTm> userTmObservableList = FXCollections.observableArrayList();
        List<UserDto> userDtoList = userBO.getAllUser();
        for (UserDto userDto : userDtoList) {
            UserTm userTm = new UserTm(
                    userDto.getUsername(),
                    userDto.getRole(),
                    userDto.getId(),
                    userDto.getPassword()
            );
            userTmObservableList.add(userTm);
            tblUsers.setItems(userTmObservableList);
            System.out.println("User Details: " + userTm);
        }
    }

    private void clearFields() {
        txtUserName.setText("");
        txtUserRoll.setText("");
        txtUserEmail.setText("");
        txtUserPassword.setText("");

    }

    @FXML
    void DeleteBtnOnAction(ActionEvent event) {

    }

    @FXML
    void UpdateBtnOnAction(ActionEvent event) {
        Alert alert;
        if (updateUser) {
            String id = txtUserEmail.getText();
            String name = txtUserName.getText();
            String userPassword = txtUserPassword.getText();
            String userRoll = txtUserRoll.getText();

            UserDto userDto = new UserDto(id,name,userPassword,Role.valueOf(userRoll));
            boolean isUpdated = userBO.updateUser(userDto);
            if (isUpdated) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("User Updated");
                alert.setContentText("User Updated");
                alert.showAndWait();
                getAll();
                clearFields();
            }
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Access granted to User Update Privilege.");
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Access denied to  User Update Privilege.");
        }
        alert.show();

    }

    @FXML
    void rowOnMouseClicked(MouseEvent event) {
        Integer index = tblUsers.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtUserEmail.setText(colUserEmail.getCellData(index).toString());
        txtUserName.setText(colUserName.getCellData(index).toString());
        txtUserPassword.setText(colUserPassword.getCellData(index).toString());
        txtUserRoll.setText(colUserRoll.getCellData(index).toString());

    }

    public void setRolePermissions(Role role) {
        this.currentRole = role;
        if (role != null){
            switch (role) {
                case Admin :
                    saveUser = true;
                    updateUser = true;
                    deleteUser = true;
                    break;
                case AdmissionsCoordinator:
                    saveUser = false;
                    updateUser = false;
                    deleteUser = false;
                    break;
                default:
                    saveUser = false;
                    updateUser = false;
                    deleteUser = false;
            }
            System.out.println("saveUser: " + saveUser + ", updateUser: " + updateUser + ", deleteUser: " + deleteUser);
        }
    }
}
