package lk.ijse.BO.custom.Impl;

import lk.ijse.BO.custom.RegistrationBO;
import lk.ijse.DAO.DaoFactory;
import lk.ijse.DAO.custom.RegistrationDAO;
import lk.ijse.DTO.RegistrationDTO;
import lk.ijse.Entity.Courses;
import lk.ijse.Entity.Registration;
import lk.ijse.Entity.Student;

public class RegistrationBoImpl implements RegistrationBO {
    RegistrationDAO  registrationDAO = (RegistrationDAO) DaoFactory.getDaoFactory().getDAOType(DaoFactory.DAOTYPE.REGISTRATION);
    @Override
    public boolean save(RegistrationDTO registrationDTO) {
        Registration registration = new Registration();
        registration.setId(registrationDTO.getId());
        registration.setRegistrationDate(registrationDTO.getRegistrationDate());
        registration.setPaymentAmount(registrationDTO.getPaymentAmount());
        registration.setPaymentStatus(registrationDTO.getPaymentStatus());
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
}
