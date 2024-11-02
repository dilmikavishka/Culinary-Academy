package lk.ijse.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentDto {
    private String Student_ID;
    private String Student_Name;
    private String Student_Address;
    private String Student_Phone;
    private String Student_Email;
    private LocalDate joinedDate;
}
