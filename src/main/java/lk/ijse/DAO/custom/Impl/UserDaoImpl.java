package lk.ijse.DAO.custom.Impl;

import lk.ijse.Config.FactoryConfiguration;
import lk.ijse.DAO.custom.UserService;
import lk.ijse.Entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserDaoImpl implements UserService {
    @Override
    public boolean save(User entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hashedPassword = BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt());
        entity.setPassword(hashedPassword);
        session.persist(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User entity) {
       return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public User search(String id) {
        return null;
    }

    @Override
    public boolean check(String userName, String password) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<User> userList = session.createNativeQuery("SELECT * FROM user WHERE username = ?", User.class)
                .setParameter(1, userName)
                .getResultList();

        transaction.commit();
        session.close();

        if (!userList.isEmpty()) {
            User user = userList.get(0);
            return BCrypt.checkpw(password, user.getPassword());
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public boolean delete(User user1) {
        return false;
    }


    @Override
    public User checkifUserExsist(String username, String password) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<User> userList = session.createNativeQuery("SELECT * FROM user WHERE username = :username", User.class)
                .setParameter("username", username)
                .getResultList();

        transaction.commit();
        session.close();

        if (!userList.isEmpty()) {
            User user = userList.get(0);
            if (BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean isUsersExsistorNotDB() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        boolean exists = false;
        try {

            Long userCount = (Long) session.createQuery("SELECT COUNT(u) FROM User u").uniqueResult();
            exists = userCount > 0;
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return exists;
    }

    @Override
    public boolean updatePassword(String email, String confirmPassword) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            List<User> userList = session.createNativeQuery("SELECT * FROM user WHERE id = :email", User.class)
                    .setParameter("email", email)
                    .getResultList();

            if (!userList.isEmpty()) {
                User user = userList.get(0);
                String hashedPassword = BCrypt.hashpw(confirmPassword, BCrypt.gensalt());
                user.setPassword(hashedPassword);
                session.update(user);
                transaction.commit();
                session.close();
                return true;
            } else {
                session.close();
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            session.close();
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        user.setPassword(hashedPassword);
        session.update(user);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<User> getAllUser() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<User> userList = session.createNativeQuery("SELECT * FROM user").addEntity(User.class).list();

        transaction.commit();
        session.close();
        return userList;
    }


}
