package vudq.service.base.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateSessionFactoryImpl implements HibernateSessionFactory{
	
    private SessionFactory sessionFactory;

	@Override
	public Session getSession() {
        return this.sessionFactory.getCurrentSession();
	}

	@Override
	public Session openSession() {
        return this.sessionFactory.openSession();
	}
	
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
