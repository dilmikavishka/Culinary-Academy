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
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Student student = session.get(Student.class, id);
        transaction.commit();
        session.close();
        return student;
    }


    @Override
    public String generateNewID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String nextId="";

        Object student = session.createQuery("SELECT Student_ID FROM Student ORDER BY Student_ID DESC LIMIT 1").uniqueResult();
        if (student!=null) {
            String studentId = student.toString();
            String prefix = "S";
            String[] split = studentId.split(prefix);
            int idNum = Integer.parseInt(split[1]);
            nextId = prefix + String.format("%03d", ++idNum);

        }else {
            return "S001";
        }
        transaction.commit();
        session.close();
        return nextId;


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
