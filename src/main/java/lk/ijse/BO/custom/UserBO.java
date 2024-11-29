package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBO;
import lk.ijse.DTO.UserDto;

import java.util.List;


public interface UserBO extends SuperBO {
    boolean save(UserDto userDto);

    boolean check(String username, String password);

    UserDto checkifUserExsist(String username, String password);

    boolean isUsersExsistorNotDB();


    boolean isUpdatedPassword(String email, String confirmPassword);

    boolean updateUser(UserDto userDto);

    List<UserDto> getAllUser();
}
