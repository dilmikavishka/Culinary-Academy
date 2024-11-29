package lk.ijse.BO.custom.Impl;


import lk.ijse.BO.custom.UserBO;
import lk.ijse.DAO.DaoFactory;
import lk.ijse.DAO.custom.UserService;
import lk.ijse.DTO.UserDto;
import lk.ijse.Entity.User;

import java.util.ArrayList;
import java.util.List;


public class UserBoImpl implements UserBO {
    UserService userService = (UserService) DaoFactory.getDaoFactory().getDAOType(DaoFactory.DAOTYPE.USER);
    @Override
    public boolean save(UserDto userDto) {
        return userService.save(new User(userDto.getId(),userDto.getUsername(),userDto.getPassword(),userDto.getRole()));
    }

    @Override
    public boolean check(String username, String password) {
        return userService.check(username,password);
    }

    @Override
    public UserDto checkifUserExsist(String username, String password) {
        User user = userService.checkifUserExsist(username, password);
        if (user != null) {
            return new UserDto(user.getId(), user.getUsername(),user.getPassword(),user.getRole());
        }
        return null;
    }

    @Override
    public boolean isUsersExsistorNotDB() {
        return userService.isUsersExsistorNotDB();
    }

    @Override
    public boolean isUpdatedPassword(String email, String confirmPassword) {
        return  userService.updatePassword(email,confirmPassword);
    }

    @Override
    public boolean updateUser(UserDto userDto) {
        return userService.updateUser(new User(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getRole()
        ));
    }

    @Override
    public List<UserDto> getAllUser() {
        List<UserDto> users = new ArrayList<>();
        List<User> userList = userService.getAllUser();
        for (User user : userList) {
            users.add(new UserDto(user.getId(),user.getUsername(),user.getPassword(),user.getRole()));
        }
        return users;
    }


}
