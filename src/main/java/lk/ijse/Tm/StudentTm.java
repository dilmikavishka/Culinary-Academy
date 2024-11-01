package lk.ijse.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentTm {
    private String Student_ID;
    private String Student_Name;
    private String Student_Phone;
    private String Student_Email;
    private String Student_Address;
    private LocalDate joinedDate;
}
