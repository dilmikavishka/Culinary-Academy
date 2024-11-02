package lk.ijse.BO.custom.Impl;

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
    public boolean saveStudent(StudentDto studentDto) {
        return studentDAO.save(new Student(
                studentDto.getStudent_ID(),
                studentDto.getStudent_Name(),
                studentDto.getStudent_Address(),
                studentDto.getStudent_Phone(),
                studentDto.getStudent_Email(),
                studentDto.getJoinedDate(),
                new HashSet<>()
        ));
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<StudentDto> students = new ArrayList<>();
        List<Student> studentList = studentDAO.getAllStudents();
        for (Student student : studentList) {
            students.add(new StudentDto(student.getStudent_ID(),student.getStudent_Name(),student.getStudent_Address(),student.getStudent_Phone(),student.getStudent_Email(),student.getJoinedDate()));
        }
        return students;
    }

    @Override
    public boolean updateStudent(StudentDto studentDto) {
        return studentDAO.update(new Student(
                studentDto.getStudent_ID(),
                studentDto.getStudent_Name(),
                studentDto.getStudent_Address(),
                studentDto.getStudent_Phone(),
                studentDto.getStudent_Email(),
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

    @Override
    public String generateNextIdStudent() {
        return studentDAO.generateNewID();
    }

    @Override
    public StudentDto searchStudent(String id) {
        Student student = studentDAO.search(id);
        StudentDto studentDto = new StudentDto();
        studentDto.setStudent_ID(student.getStudent_ID());
        studentDto.setStudent_Name(student.getStudent_Name());
        studentDto.setStudent_Address(student.getStudent_Address());
        studentDto.setStudent_Phone(student.getStudent_Phone());
        studentDto.setStudent_Email(student.getStudent_Email());
        studentDto.setJoinedDate(student.getJoinedDate());
        return studentDto;
    }


}
