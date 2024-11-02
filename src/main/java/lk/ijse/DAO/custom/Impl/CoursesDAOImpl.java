package lk.ijse.DAO.custom.Impl;

import lk.ijse.Config.FactoryConfiguration;
import lk.ijse.DAO.custom.CoursesDAO;
import lk.ijse.Entity.Courses;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CoursesDAOImpl implements CoursesDAO {
    @Override
    public boolean save(Courses entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Courses entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Courses search(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Courses courses = session.get(Courses.class, id);
        transaction.commit();
        session.close();
        return courses;
    }

    @Override
    public List<Courses> getAllCourses() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Courses> coursesList = session.createNativeQuery("SELECT * FROM courses").addEntity(Courses.class).list();

        transaction.commit();
        session.close();
        return coursesList;
    }

  /*  @Override
    public String generateNewId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String nextId="";

        Object course = session.createQuery("SELECT courseId FROM Courses ORDER BY courseId DESC LIMIT 1").uniqueResult();
        if (course!=null) {
            String courseId = course.toString();
            String prefix = "C";
            String[] split = courseId.split(prefix);
            int idNum = Integer.parseInt(split[1]);
            nextId = prefix + String.format("%03d", ++idNum);

        }else {
            return "C001";
        }

        transaction.commit();
        session.close();
        return nextId;
    }*/
  @Override
  public String generateNewId() {
      Session session = FactoryConfiguration.getInstance().getSession();
      Transaction transaction = session.beginTransaction();
      String nextId = "";

      try {
          Object course = session.createQuery("SELECT courseId FROM Courses ORDER BY courseId DESC")
                  .setMaxResults(1)
                  .uniqueResult();
          if (course != null) {
              String courseId = course.toString();
              String prefix = "C";
              if (courseId.startsWith(prefix)) {
                  String numericPart = courseId.substring(prefix.length());
                  try {
                      int idNum = Integer.parseInt(numericPart);
                      nextId = prefix + String.format("%03d", ++idNum);
                  } catch (NumberFormatException e) {
                      System.out.println("Error parsing course ID number: " + e.getMessage());
                      nextId = prefix + "001";
                  }
              } else {
                  nextId = prefix + "001";
              }
          } else {
              nextId = "C001";
          }
      } finally {
          transaction.commit();
          session.close();
      }
      return nextId;
  }

    @Override
    public boolean deleteCourse(Courses courses) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.remove(courses);
        transaction.commit();
        session.close();

        return true;
    }

}
