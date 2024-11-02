package lk.ijse.DAO.custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.Courses;

import java.util.List;

public interface CoursesDAO extends CrudDAO<Courses> {
    List<Courses> getAllCourses();

    String generateNewId();

    boolean deleteCourse(Courses courses);
}
