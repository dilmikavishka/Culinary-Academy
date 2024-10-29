package lk.ijse.Config;

import lk.ijse.Entity.Courses;
import lk.ijse.Entity.Registration;
import lk.ijse.Entity.Student;
import lk.ijse.Entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    private static  FactoryConfiguration factoryConfiguration;
    private  SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration cfg = new Configuration();
        Properties props = new Properties();

        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        cfg.setProperties(props);
        cfg.addAnnotatedClass(Student.class).addAnnotatedClass(Registration.class).addAnnotatedClass(User.class).addAnnotatedClass(Courses.class);

        sessionFactory = cfg.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        return (factoryConfiguration == null) ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
