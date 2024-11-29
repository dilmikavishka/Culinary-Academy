package lk.ijse.DAO.custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.Registration;

import java.util.List;

public interface RegistrationDAO extends CrudDAO<Registration> {
    List<Object[]> getStudentsWithCourses();

    List<Registration> getAllRegistrations();

    List<String> getStudentsRegisteredForAllCourses();

    String generateNewId();

}
