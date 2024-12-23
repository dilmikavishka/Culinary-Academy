package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.DTO.StudentDto;

import java.util.List;

public interface StudentBO extends SuperBO {

    boolean saveStudent(StudentDto studentDto);

    List<StudentDto> getAllStudents();

    boolean updateStudent(StudentDto studentDto);

    boolean deleteStudent(String studentId);

    String generateNextIdStudent();

    StudentDto searchStudent(String id);
}
