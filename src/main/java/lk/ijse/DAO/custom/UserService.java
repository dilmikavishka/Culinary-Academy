package lk.ijse.DAO.custom;

import lk.ijse.DAO.CrudDAO;
import lk.ijse.Entity.User;

import java.util.List;

public interface UserService extends CrudDAO<User> {
    boolean check(String userName, String password);

    List<User> getAll();

    boolean delete(User user1);

    User checkifUserExsist(String username, String password);

    boolean isUsersExsistorNotDB();
}
