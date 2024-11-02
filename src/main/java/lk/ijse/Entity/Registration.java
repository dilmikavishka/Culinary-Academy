package lk.ijse.Entity;

import jakarta.persistence.*;
import lk.ijse.Enum.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Registration {
    @Id
    private String id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;

    private LocalDate registrationDate;
    private BigDecimal paymentAmount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private String notes;
}
