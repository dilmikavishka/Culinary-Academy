package lk.ijse.DAO.custom;

import lk.ijse.DAO.SuperDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface DashBoardDAO extends SuperDAO {

    int getStuCount();

    int getCouCount();

    int getRegiCount();

    Map<LocalDate, BigDecimal> getRegiByDay();
}
