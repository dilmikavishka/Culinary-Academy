package lk.ijse.BO.custom.Impl;

import lk.ijse.BO.custom.RegistrationBO;
import lk.ijse.DAO.DaoFactory;
import lk.ijse.DAO.custom.RegistrationDAO;
import lk.ijse.DTO.*;
import lk.ijse.Entity.Courses;
import lk.ijse.Entity.Registration;
import lk.ijse.Entity.Student;

import java.util.ArrayList;
import java.util.List;

public class RegistrationBoImpl implements RegistrationBO {
    RegistrationDAO  registrationDAO = (RegistrationDAO) DaoFactory.getDaoFactory().getDAOType(DaoFactory.DAOTYPE.REGISTRATION);
    @Override
    public boolean save(RegistrationDTO registrationDTO) {
        Registration registration = new Registration();
        registration.setId(registrationDTO.getId());
        registration.setRegistrationDate(registrationDTO.getRegistrationDate());
        registration.setPaymentAmount(registrationDTO.getPaymentAmount());
        registration.setPaymentStatus(registrationDTO.getPaymentStatus());
        registration.setBalanceToPay(registrationDTO.getBalanceToPay());
        Student student = new Student();
        student.setStudent_ID(registrationDTO.getStudent().getStudent_ID());
        student.setStudent_Name(registrationDTO.getStudent().getStudent_Name());
        student.setStudent_Phone(registrationDTO.getStudent().getStudent_Phone());
        student.setStudent_Email(registrationDTO.getStudent().getStudent_Email());
        student.setStudent_Address(registrationDTO.getStudent().getStudent_Address());
        student.setJoinedDate(registrationDTO.getStudent().getJoinedDate());
        registration.setStudent(student);

        Courses courses = new Courses();
        courses.setCourseId(registrationDTO.getCourse().getCourseId());
        courses.setCourseName(registrationDTO.getCourse().getCourseName());
        courses.setDuration(registrationDTO.getCourse().getDuration());
        courses.setFee(registrationDTO.getCourse().getFee());
        registration.setCourse(courses);
        return  registrationDAO.save(registration);
    }

    @Override
    public List<RegistrationDTO> getAllRegistrations() {
        List<Registration> registrationList = registrationDAO.getAllRegistrations();
        List<RegistrationDTO> registrationDTOList = new ArrayList<>();
        for (Registration registration : registrationList) {
            RegistrationDTO registrationDTO = new RegistrationDTO();
            registrationDTO.setId(registration.getId());
            registrationDTO.setRegistrationDate(registration.getRegistrationDate());
            registrationDTO.setPaymentAmount(registration.getPaymentAmount());
            registrationDTO.setPaymentStatus(registration.getPaymentStatus());
            registrationDTO.setBalanceToPay(registration.getBalanceToPay());
            StudentDto student = new StudentDto();
            student.setStudent_ID(registration.getStudent().getStudent_ID());
            student.setStudent_Name(registration.getStudent().getStudent_Name());
            student.setStudent_Phone(registration.getStudent().getStudent_Phone());
            student.setStudent_Email(registration.getStudent().getStudent_Email());
            student.setStudent_Address(registration.getStudent().getStudent_Address());
            student.setJoinedDate(registration.getStudent().getJoinedDate());
            registrationDTO.setStudent(student);
            CoursesDto courses = new CoursesDto();
            courses.setCourseId(registration.getCourse().getCourseId());
            courses.setCourseName(registration.getCourse().getCourseName());
            courses.setDuration(registration.getCourse().getDuration());
            courses.setFee(registration.getCourse().getFee());
            registrationDTO.setCourse(courses);
            registrationDTOList.add(registrationDTO);
        }
        return registrationDTOList;
    }

    public List<StudentCourseDTO> getAllStudentsAlongTheirCourses(){
        List<Object[]>objects = registrationDAO.getStudentsWithCourses();
        List<StudentCourseDTO> studentCourseDTOList = new ArrayList<>();
        for (Object[] object : objects) {
            String studentId = (String) object[0];
            String studentName = (String) object[1];
            String courseId = (String) object[2];
            String courseName = (String) object[3];

            studentCourseDTOList.add(new StudentCourseDTO(studentId, studentName, courseId, courseName));
        }
        return studentCourseDTOList;
    }

    @Override
    public List<FiveStuDto> getStudentsRegisteredForAllCourses() {
        List<String> studentIds = registrationDAO.getStudentsRegisteredForAllCourses();
        List<FiveStuDto> fiveStuDtos = new ArrayList<>();
        for (String studentId : studentIds) {
            fiveStuDtos.add(new FiveStuDto(studentId));
        }
        return fiveStuDtos;
    }

    @Override
    public boolean update(RegistrationDTO upregistrationDTO) {
        Registration registration = new Registration();
        registration.setId(upregistrationDTO.getId());
        registration.setRegistrationDate(upregistrationDTO.getRegistrationDate());
        registration.setPaymentAmount(upregistrationDTO.getPaymentAmount());
        registration.setPaymentStatus(upregistrationDTO.getPaymentStatus());
        registration.setBalanceToPay(upregistrationDTO.getBalanceToPay());
        Student student = new Student();
        student.setStudent_ID(upregistrationDTO.getStudent().getStudent_ID());
        student.setStudent_Name(upregistrationDTO.getStudent().getStudent_Name());
        student.setStudent_Phone(upregistrationDTO.getStudent().getStudent_Phone());
        student.setStudent_Email(upregistrationDTO.getStudent().getStudent_Email());
        student.setStudent_Address(upregistrationDTO.getStudent().getStudent_Address());
        student.setJoinedDate(upregistrationDTO.getStudent().getJoinedDate());
        registration.setStudent(student);

        Courses courses = new Courses();
        courses.setCourseId(upregistrationDTO.getCourse().getCourseId());
        courses.setCourseName(upregistrationDTO.getCourse().getCourseName());
        courses.setDuration(upregistrationDTO.getCourse().getDuration());
        courses.setFee(upregistrationDTO.getCourse().getFee());
        registration.setCourse(courses);
        return  registrationDAO.update(registration);
    }

    @Override
    public String generateNextIdRegistration() {
        return registrationDAO.generateNewId();
    }

    @Override
    public boolean delete(String id) {
        return registrationDAO.delete(id);
    }
}
