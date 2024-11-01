package lk.ijse.DAO;

public interface CrudDAO<T> extends SuperDAO {
    boolean save(T entity);

    boolean update(T entity);

    boolean delete(String id);

    T search(String id);
}
