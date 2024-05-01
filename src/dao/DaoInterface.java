package dao;

import java.sql.SQLException;
import java.util.List;

public interface DaoInterface <T>{
    public void create(T entity)  throws SQLException;

    public List<T> read() throws SQLException;

    public void delete(T entity) throws SQLException;

    public void update(T entity) throws SQLException;
}
