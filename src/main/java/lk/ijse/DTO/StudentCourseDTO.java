package lk.ijse.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentCourseDTO {
    private String studentId;
    private String studentName;
    private String courseId;
    private String courseName;
}
