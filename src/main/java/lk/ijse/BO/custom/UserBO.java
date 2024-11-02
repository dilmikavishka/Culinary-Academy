package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.DTO.UserDto;
import lk.ijse.Entity.User;

public interface UserBO extends SuperBO {
    boolean save(UserDto userDto);

    boolean check(String username, String password);

    UserDto checkifUserExsist(String username, String password);

    boolean isUsersExsistorNotDB();

}
