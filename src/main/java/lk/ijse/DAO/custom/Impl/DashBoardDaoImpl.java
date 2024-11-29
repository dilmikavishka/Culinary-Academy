package lk.ijse.DAO.custom.Impl;

import lk.ijse.Config.FactoryConfiguration;
import lk.ijse.DAO.custom.DashBoardDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashBoardDaoImpl implements DashBoardDAO {
    @Override
    public int getStuCount() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        int studentCount = 0;

        try {
            Long count = (Long) session.createQuery("SELECT COUNT(s) FROM Student s").uniqueResult();
            studentCount = count != null ? count.intValue() : 0;
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return studentCount;

    }

    @Override
    public int getCouCount() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        int courseCount = 0;

        try {
            Long count = (Long) session.createQuery("SELECT COUNT(c) FROM Courses c").uniqueResult();
            courseCount = count != null ? count.intValue() : 0;
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return courseCount;
    }

    @Override
    public int getRegiCount() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        int registrationCount = 0;

        try {
            Long count = (Long) session.createQuery("SELECT COUNT(r) FROM Registration r").uniqueResult();
            registrationCount = count != null ? count.intValue() : 0;
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return registrationCount;
    }

    @Override
    public Map<LocalDate, BigDecimal> getRegiByDay() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Map<LocalDate, BigDecimal> registerByDay = new HashMap<>();

        try {
            List<Object[]> results = session.createQuery(
                    "SELECT r.registrationDate, SUM(r.paymentAmount) FROM Registration r GROUP BY r.registrationDate",
                    Object[].class
            ).getResultList();

            for (Object[] result : results) {
                LocalDate date = (LocalDate) result[0];
                BigDecimal totalAmount = (BigDecimal) result[1];
                registerByDay.put(date, totalAmount);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return registerByDay;
    }
}
