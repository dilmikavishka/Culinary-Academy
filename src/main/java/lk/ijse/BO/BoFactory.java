package lk.ijse.BO;

import lk.ijse.BO.custom.Impl.*;

public class BoFactory {
    private static BoFactory boFactory;

    public BoFactory() {
    }

    public static BoFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BoFactory() : boFactory;
    }
    public enum BoType{
        USER,STUDENT,COURSES,REGISTRATION,DASHBOARD
    }
    public SuperBO getBO(BoType type){
        switch (type){
            case USER:
                return new UserBoImpl();
            case STUDENT:
                return new StudentBoImpl();
            case COURSES:
                return new CoursesBOImpl();
            case REGISTRATION:
                return  new RegistrationBoImpl();
            case DASHBOARD:
                return new DashBoardBOImpl();
            default:
                return null;
        }
    }
}
