package db;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    protected abstract Class<T> getEntityClass();

    @Override
    public void save(T object) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(object);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
        }
    }

    @Override
    public List<T> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from " + getEntityClass().getSimpleName(), getEntityClass()).list();
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;
        boolean isDeleted = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            T entity = session.find(getEntityClass(), id);
            if (entity != null) {
                session.remove(entity);
                isDeleted = true;
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
        }
        return isDeleted;
    }

    @Override
    public void update(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
                session.merge(entity);
                transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
        }
    }

    @Override
    public T getById(Long id) {
        Transaction transaction = null;
        T entity = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            entity = session.find(getEntityClass(), id);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
        }
        return entity;
    }
}
