package vudq.service.base.dao;

import org.hibernate.Session;

public interface HibernateSessionFactory {
    Session getSession();
    Session openSession();
}
