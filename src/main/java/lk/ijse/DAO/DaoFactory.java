package lk.ijse.DAO;

import lk.ijse.DAO.custom.Impl.*;

public class DaoFactory {
    public static  DaoFactory  daoFactory;

    private DaoFactory() {}

    public static DaoFactory getDaoFactory() {
        return (daoFactory == null) ? new DaoFactory() : daoFactory;
    }

    public enum DAOTYPE{
    USER,STUDENT,COURSES,REGISTRATION,DASHBOARD
    }
    public SuperDAO getDAOType(DAOTYPE daotype) {
        switch (daotype){
            case USER:
                return new UserDaoImpl();
            case STUDENT:
                return new StudentDaoImpl();
            case COURSES:
                return new CoursesDAOImpl();
            case REGISTRATION:
                return new RegistrationDAOImpl();
            case DASHBOARD:
                return new DashBoardDaoImpl();
            default:
                return null;
        }

    }
}
