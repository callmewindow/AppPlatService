package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by yezhang989@163.com on 14-5-31.
 */
public class BaseDAO{
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public Session getSession() {
        return getSessionFactory().getCurrentSession();
    }
    private SessionFactory sessionFactory;
}
