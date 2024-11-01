package lk.ijse.BO;

import lk.ijse.BO.Impl.StudentBoImpl;
import lk.ijse.BO.Impl.UserBoImpl;

public class BoFactory {
    private static BoFactory boFactory;

    public BoFactory() {
    }

    public static BoFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BoFactory() : boFactory;
    }
    public enum BoType{
        USER,STUDENT
    }
    public SuperBO getBO(BoType type){
        switch (type){
            case USER:
                return new UserBoImpl();
            case STUDENT:
                return new StudentBoImpl();
            default:
                return null;
        }
    }
}
