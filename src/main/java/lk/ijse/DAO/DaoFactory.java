package lk.ijse.DAO;

import lk.ijse.DAO.custom.Impl.StudentDaoImpl;
import lk.ijse.DAO.custom.Impl.UserDaoImpl;

public class DaoFactory {
    public static  DaoFactory  daoFactory;

    private DaoFactory() {}

    public static DaoFactory getDaoFactory() {
        return (daoFactory == null) ? new DaoFactory() : daoFactory;
    }

    public enum DAOTYPE{
        USER,STUDENT
    }
    public SuperDAO getDAOType(DAOTYPE daotype) {
        switch (daotype){
            case USER:
                return new UserDaoImpl();
            case STUDENT:
                return new StudentDaoImpl();
            default:
                return null;
        }

    }
}
