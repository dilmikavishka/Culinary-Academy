package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface DashBoardBO extends SuperBO {
    int getStudentCount();

    int getCourseCount();

    int getRegistrationCount();

    Map<LocalDate, BigDecimal> getRegistrationByDay();
}
