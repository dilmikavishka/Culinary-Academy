package lk.ijse.BO.custom.Impl;

import lk.ijse.BO.custom.CoursesBO;
import lk.ijse.DAO.DaoFactory;
import lk.ijse.DAO.custom.CoursesDAO;
import lk.ijse.DTO.CoursesDto;
import lk.ijse.DTO.StudentDto;
import lk.ijse.Entity.Courses;
import lk.ijse.Entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CoursesBOImpl implements CoursesBO {
    CoursesDAO coursesDAO = (CoursesDAO) DaoFactory.getDaoFactory().getDAOType(DaoFactory.DAOTYPE.COURSES);
    @Override
    public boolean saveCourses(CoursesDto coursesDto) {
        return coursesDAO.save(new Courses(
                coursesDto.getCourseId(),
                coursesDto.getCourseName(),
                coursesDto.getDuration(),
                coursesDto.getFee(),
                new HashSet<>()
        ));
    }

    @Override
    public boolean updateCourses(CoursesDto coursesDto) {
        return coursesDAO.update(new Courses(
                coursesDto.getCourseId(),
                coursesDto.getCourseName(),
                coursesDto.getDuration(),
                coursesDto.getFee(),
                new HashSet<>()
        ));
    }

    @Override
    public List<CoursesDto> getAllCourses() {
        List<CoursesDto> course = new ArrayList<>();
        List<Courses> coursesList = coursesDAO.getAllCourses();
        for (Courses courses  : coursesList) {
            course.add(new CoursesDto(courses.getCourseId(),courses.getCourseName(),courses.getDuration(),courses.getFee()));
        }
        return course;
    }

    @Override
    public String generateNextIdCourse() {
        return coursesDAO.generateNewId();
    }

    @Override
    public boolean deleteCourse(String courseId) {
        Courses courses = new Courses();
        courses.setCourseId(courseId);
        return coursesDAO.deleteCourse(courses);
    }

    @Override
    public CoursesDto searchCoursebyId(String id) {
        Courses courses = coursesDAO.search(id);
        CoursesDto coursesDto = new CoursesDto();
        coursesDto.setCourseId(courses.getCourseId());
        coursesDto.setCourseName(courses.getCourseName());
        coursesDto.setDuration(courses.getDuration());
        coursesDto.setFee(courses.getFee());
        return coursesDto;
    }


}
