package lk.ijse.DAO.custom.Impl;

import jakarta.persistence.TypedQuery;
import lk.ijse.Config.FactoryConfiguration;
import lk.ijse.DAO.custom.RegistrationDAO;
import lk.ijse.Entity.Registration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RegistrationDAOImpl implements RegistrationDAO {
    @Override
    public boolean save(Registration entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Registration entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Registration search(String id) {
        return null;
    }

    @Override
    public List<Object[]> getStudentsWithCourses() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT r.student.Student_ID, r.student.Student_Name, r.course.courseId, r.course.courseName " +
                "FROM Registration r " +
                "JOIN r.student s " +
                "JOIN r.course c";

        TypedQuery<Object[]> query = session.createQuery(hql, Object[].class);
        List<Object[]> resultList = query.getResultList();

        transaction.commit();
        session.close();

        return resultList;
    }
}
