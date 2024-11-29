package lk.ijse.Tm;

import lk.ijse.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTm {
    private String username;
    private Role role;
    private String id;
    private String password;

}
