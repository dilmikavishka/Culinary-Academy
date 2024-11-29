package lk.ijse.BO.custom.Impl;

import lk.ijse.BO.custom.DashBoardBO;
import lk.ijse.DAO.DaoFactory;
import lk.ijse.DAO.custom.DashBoardDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class DashBoardBOImpl implements DashBoardBO {
    DashBoardDAO dashBoardDAO = (DashBoardDAO) DaoFactory.getDaoFactory().getDAOType(DaoFactory.DAOTYPE.DASHBOARD);
    @Override
    public int getStudentCount() {
        return dashBoardDAO.getStuCount();
    }

    @Override
    public int getCourseCount() {
        return dashBoardDAO.getCouCount();
    }

    @Override
    public int getRegistrationCount() {
        return dashBoardDAO.getRegiCount();
    }

    @Override
    public Map<LocalDate, BigDecimal> getRegistrationByDay() {
        return dashBoardDAO.getRegiByDay();
    }
}
