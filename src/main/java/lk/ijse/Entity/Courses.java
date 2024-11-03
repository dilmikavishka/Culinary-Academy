package lk.ijse.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class
Courses {
    @Id
    private String courseId;
    private String courseName;
    private String duration;
    private double fee;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Registration> registrations = new HashSet<>();

}

