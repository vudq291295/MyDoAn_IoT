package com.dqv.spring.oauth2.DAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dqv.spring.oauth2.bo.UserBO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;

@Transactional
public class UserDAO{
	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }

	public UserBO findUserByUsername(String bo) {
		try {
	        Session session = this.sessionFactory.getCurrentSession();
	        Query query = session.createQuery("from UserBO where userName = :name ");
	        query.setParameter("name", bo);
	        List<?> list = query.list();
	        
	        UserBO result=  (UserBO)list.get(0);
	        return result;
		}
		catch (Exception e) {
			// TODO: handle exception
	        return null;

		}
    }

}
