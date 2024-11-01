package lk.ijse.DTO;

import lk.ijse.Enum.Role;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private String id;
    private String username;
    private String password;
    private Role role;
}
