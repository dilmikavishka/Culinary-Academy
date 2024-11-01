package lk.ijse.DAO.custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.Student;

import java.util.List;

public interface StudentDAO extends CrudDAO<Student> {
    String generateNewID();

    List<Student> getAllStudents();

    boolean deleteStudent(Student student);
}
