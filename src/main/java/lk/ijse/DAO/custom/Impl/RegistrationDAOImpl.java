package lk.ijse.DAO.custom.Impl;

import jakarta.persistence.TypedQuery;
import lk.ijse.Config.FactoryConfiguration;
import lk.ijse.DAO.custom.RegistrationDAO;
import lk.ijse.Entity.Registration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

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
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Registration registration = session.get(Registration.class, id);
        session.remove(registration);
        transaction.commit();
        session.close();
        return true;
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

    @Override
    public List<Registration> getAllRegistrations() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Registration> registrationList = session.createQuery("FROM Registration", Registration.class).list();

        transaction.commit();
        session.close();

        return registrationList;
    }

    @Override
    public List<String> getStudentsRegisteredForAllCourses() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT r.student.Student_ID " +
                "FROM Registration r " +
                "GROUP BY r.student.Student_ID " +
                "HAVING COUNT(DISTINCT r.course.courseId) = (SELECT COUNT(c.courseId) FROM Courses c)";

        TypedQuery<String> query = session.createQuery(hql, String.class);
        List<String> studentIds = query.getResultList();

        transaction.commit();
        session.close();
        return studentIds;
    }

    @Override
    public String generateNewId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String nextId = "";

        try {
            Object course = session.createQuery("SELECT id FROM Registration ORDER BY id DESC")
                    .setMaxResults(1)
                    .uniqueResult();
            if (course != null) {
                String courseId = course.toString();
                String prefix = "R";
                if (courseId.startsWith(prefix)) {
                    String numericPart = courseId.substring(prefix.length());
                    try {
                        int idNum = Integer.parseInt(numericPart);
                        nextId = prefix + String.format("%03d", ++idNum);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing course ID number: " + e.getMessage());
                        nextId = prefix + "001";
                    }
                } else {
                    nextId = prefix + "001";
                }
            } else {
                nextId = "R001";
            }
        } finally {
            transaction.commit();
            session.close();
        }
        return nextId;
    }


}
