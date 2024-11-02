package lk.ijse.Tm;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CoursesTm {
    private String courseId;
    private String courseName;
    private String duration;
    private double fee;
}
