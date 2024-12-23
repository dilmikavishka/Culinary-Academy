package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.DTO.FiveStuDto;
import lk.ijse.DTO.RegistrationDTO;
import lk.ijse.DTO.StudentCourseDTO;

import java.util.List;

public interface RegistrationBO extends SuperBO {
    boolean save(RegistrationDTO registrationDTO);

    List<RegistrationDTO> getAllRegistrations();

    List<StudentCourseDTO> getAllStudentsAlongTheirCourses();

    List<FiveStuDto> getStudentsRegisteredForAllCourses();

    boolean update(RegistrationDTO upregistrationDTO);

    String generateNextIdRegistration();

    boolean delete(String id);
}
