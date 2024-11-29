package lk.ijse.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.BO.BoFactory;
import lk.ijse.BO.custom.DashBoardBO;
import lk.ijse.DTO.UserDto;
import lk.ijse.Entity.User;
import lk.ijse.Enum.Role;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


public class DashBoardFormController implements Initializable {

    public Text DashDay;
    DashBoardBO dashBoardBO = (DashBoardBO) BoFactory.getBoFactory().getBO(BoFactory.BoType.DASHBOARD);
    @FXML
    private AnchorPane anpDashBoard;

    private UserDto authUser;

    @FXML
    private Label lblCourses;

    @FXML
    private Label lblRegistration;

    @FXML
    private Label lblStudent;

    @FXML
    private LineChart<String, BigDecimal> lineChart;


    @FXML
    private Label txtUserRoleDash;

    @FXML
    private Label txtUserNameDash;

    private final String[] taglines = {
            "Every Dish Tells Its Story",
            "Crafting Flavors With Care",
            "Seasons Inspire Every Bite",
            "Where Taste Meets Passion"
    };


    private int currentTaglineIndex = 0;

    private UserDto user;



    private void startTaglineAnimation() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            DashDay.setText(taglines[currentTaglineIndex]);
            currentTaglineIndex = (currentTaglineIndex + 1) % taglines.length;
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void setuserDetail(UserDto authorizedUser) {
        this.authUser = authorizedUser;


        if (this.authUser != null) {

            setUserDetailsToUserCard(authUser);
        } else {
            System.out.println("Authorized User is null!");
        }
    }

    private void setUserDetailsToUserCard(UserDto user) {
        if (txtUserRoleDash == null || txtUserNameDash == null) {

            return;
        }
        if (user == null) {

            return;
        }
        System.out.println("Setting user details: " + user);
        Role role = user.getRole();
        String username = user.getUsername();
        txtUserNameDash.setText(username);
        txtUserRoleDash.setText(role.toString());

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUserDetailsToUserCard(authUser);
        startTaglineAnimation();
        int studentCount = 0;
        int coursesCount = 0;
        int registrationCount = 0;

        try{
            iniLineChart();
            studentCount = dashBoardBO.getStudentCount();
            coursesCount = dashBoardBO.getCourseCount();
            registrationCount = dashBoardBO.getRegistrationCount();
        }catch(Exception e){
            e.printStackTrace();
        }

        lblStudent.setText(studentCount + "");
        lblCourses.setText(coursesCount + "");
        lblRegistration.setText(registrationCount + "");
    }

    private void iniLineChart() {
        XYChart.Series<String, BigDecimal> series = new XYChart.Series<>();
        series.setName("Monthly Registrations");

        Map<LocalDate, BigDecimal> registrationByDay = dashBoardBO.getRegistrationByDay();

        for (Map.Entry<LocalDate, BigDecimal> entry : registrationByDay.entrySet()) {
            LocalDate date = entry.getKey();
            BigDecimal totalAmount = entry.getValue();

            String monthName = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            XYChart.Data<String, BigDecimal> dataPoint = new XYChart.Data<>(monthName, totalAmount);
            series.getData().add(dataPoint);

            Tooltip tooltip = new Tooltip("Month: " + monthName + "\nValue: " + totalAmount);
            Tooltip.install(dataPoint.getNode(), tooltip);

            dataPoint.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    String color = getColorForMonth(date.getMonthValue());

                }
            });
        }

        lineChart.getData().clear();
        lineChart.getData().add(series);

        styleLineChart();
    }

    private String getColorForMonth(int month) {
        String[] colors = {
                "#FF5733", "#33FF57", "#3357FF", "#FF33A8", "#FFC133", "#33FFF5",
                "#FF5733", "#C70039", "#900C3F", "#581845", "#DAF7A6", "#FFC300"
        };
        return colors[(month - 1) % colors.length];
    }

    private void styleLineChart() {
        lineChart.setStyle("");


        for (XYChart.Series<String, BigDecimal> series : lineChart.getData()) {
            series.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: linear-gradient(to right, #4CAF50, #81C784); -fx-stroke-width: 3px;");
        }
    }



}
