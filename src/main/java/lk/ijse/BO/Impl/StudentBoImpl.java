package lk.ijse.BO.Impl;

import lk.ijse.BO.custom.StudentBO;
import lk.ijse.DAO.DaoFactory;
import lk.ijse.DAO.custom.StudentDAO;
import lk.ijse.DTO.StudentDto;
import lk.ijse.Entity.Student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StudentBoImpl implements StudentBO {
    StudentDAO  studentDAO = (StudentDAO) DaoFactory.getDaoFactory().getDAOType(DaoFactory.DAOTYPE.STUDENT);
    @Override
    public String generateNewID() {
        return studentDAO.generateNewID();
    }

    @Override
    public boolean saveStudent(StudentDto studentDto) {
        return studentDAO.save(new Student(
                studentDto.getStudent_ID(),
                studentDto.getStudent_Name(),
                studentDto.getStudent_Phone(),
                studentDto.getStudent_Email(),
                studentDto.getStudent_Address(),
                studentDto.getJoinedDate(),
                new HashSet<>()
        ));
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<StudentDto> students = new ArrayList<>();
        List<Student> studentList = studentDAO.getAllStudents();
        for (Student student : studentList) {
            students.add(new StudentDto(student.getStudent_ID(),student.getStudent_Name(),student.getStudent_Phone(),student.getStudent_Email(),student.getStudent_Address(),student.getJoinedDate()));
        }
        return students;
    }

    @Override
    public boolean updateStudent(StudentDto studentDto) {
        return studentDAO.update(new Student(
                studentDto.getStudent_ID(),
                studentDto.getStudent_Name(),
                studentDto.getStudent_Phone(),
                studentDto.getStudent_Email(),
                studentDto.getStudent_Address(),
                studentDto.getJoinedDate(),
                new HashSet<>()
        ));
    }

    @Override
    public boolean deleteStudent(String studentId) {
        Student student = new Student();
        student.setStudent_ID(studentId);
        return studentDAO.deleteStudent(student);
    }


}
