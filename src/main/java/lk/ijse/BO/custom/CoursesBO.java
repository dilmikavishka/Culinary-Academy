package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.DTO.CoursesDto;

import java.sql.SQLException;
import java.util.List;

public interface CoursesBO extends SuperBO {
    boolean saveCourses(CoursesDto coursesDto);

    boolean updateCourses(CoursesDto coursesDto);


    List<CoursesDto> getAllCourses();

    String generateNextIdCourse();

    boolean deleteCourse(String courseId);

    CoursesDto searchCoursebyId(String id);
}
