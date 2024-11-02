package lk.ijse.DTO;

import jakarta.persistence.*;
import lk.ijse.Entity.Courses;
import lk.ijse.Entity.Student;
import lk.ijse.Enum.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RegistrationDTO {
    private String id;
    private StudentDto student;
    private CoursesDto course;
    private LocalDate registrationDate;
    private BigDecimal paymentAmount;
    private PaymentStatus paymentStatus;
    private String notes;
}
