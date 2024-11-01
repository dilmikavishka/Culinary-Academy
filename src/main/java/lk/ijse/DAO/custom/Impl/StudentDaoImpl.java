package lk.ijse.DAO.custom.Impl;

import lk.ijse.Config.FactoryConfiguration;
import lk.ijse.DAO.custom.StudentDAO;
import lk.ijse.Entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDaoImpl implements StudentDAO {
    @Override
    public boolean save(Student entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Student entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Student search(String id) {
        return null;
    }


    @Override
    public String generateNewID() {
            Session session = FactoryConfiguration.getInstance().getSession();
            Transaction transaction = session.beginTransaction();

            Query query = session.createNativeQuery("SELECT student_id FROM Student ORDER BY student_id DESC LIMIT 1");
            List results = query.getResultList();

            transaction.commit();
            session.close();

            return (results.isEmpty()) ? null : (String) results.get(0);


    }

    @Override
    public List<Student> getAllStudents() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Student> studentArrayList = session.createNativeQuery("SELECT * FROM student").addEntity(Student.class).list();

        transaction.commit();
        session.close();
        return studentArrayList;
    }

    @Override
    public boolean deleteStudent(Student student) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.remove(student);
        transaction.commit();
        session.close();

        return true;
    }
}
