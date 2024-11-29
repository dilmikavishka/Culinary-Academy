package lk.ijse.Tm;

import lk.ijse.DTO.CoursesDto;
import lk.ijse.DTO.StudentDto;
import lk.ijse.Enum.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class RegistrationTm {
    private String id;
    private String student;
    private String course;
    private LocalDate registrationDate;
    private BigDecimal paymentAmount;
    private PaymentStatus paymentStatus;
    private double courseFee;
    private BigDecimal  balanceToPay;
}
