package lk.ijse.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    @Id
    private String Student_ID;
    private String Student_Name;
    private String Student_Address;
    private String Student_Phone;
    private String Student_Email;
    private LocalDate joinedDate;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Registration> registrations = new HashSet<>();
}
