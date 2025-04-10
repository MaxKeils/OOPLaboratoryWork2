package db;

import java.util.List;

public interface BaseDao <T> {

    void save(T object);

    List<T> getAll();

    boolean deleteById(Long id);

    T getById(Long Id);

    void update(T entity);
}
