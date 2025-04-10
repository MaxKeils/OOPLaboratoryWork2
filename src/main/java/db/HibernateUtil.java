package db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    private HibernateUtil() { }

    private static volatile SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateUtil.class) {
                if (sessionFactory == null) {
                    try {
                        sessionFactory = new Configuration()
                                .configure("hibernate.cfg.xml")
                                .buildSessionFactory();
                    } catch (Exception e) {
                        System.err.println("Ошибка создания SessionFactory: " + e);
                        throw new ExceptionInInitializerError(e);
                    }
                }

            }
        }
        return sessionFactory;
    }

}
