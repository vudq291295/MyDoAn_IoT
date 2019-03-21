package com.tce.spring.oauth2.DAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tce.spring.oauth2.bo.UserBO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;

@Transactional
public class UserDAO{
//    public UserDAO() {
//        this.model = new UserBO();
//    }

//    public UserDAO(Session session) {
//        this.session = session;
//    }

	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }
//	  
    public List<UserBO> getAllUsers() {
//        Criteria cr = getSession().createCriteria(UserBO.class);
//        cr.addOrder(Order.desc("id"));
//        List<UserBO> lstAddressBO = cr.list();
        return null;
    }

	public UserBO getUserByUP(UserBO bo) {
//        Criteria cr = getSession().createCriteria(UserBO.class);
//        cr.add(Expression.eq("userName",bo.getUserName()).ignoreCase());
//        cr.add(Expression.eq("passWord",bo.getPassWord()).ignoreCase());
//        cr.addOrder(Order.desc("id"));
//        UserBO result=  (UserBO) cr.uniqueResult();
        return null;
    }

	public UserBO findUserByUsername(String bo) {
		try {
	        System.out.println(bo);
	        Session session = this.sessionFactory.getCurrentSession();
	        System.out.println("1 "+bo);
	        Criteria cr = session.createCriteria(UserBO.class);
	        System.out.println("2 "+bo);
	        cr.add(Expression.eq("username",bo).ignoreCase());
//	        cr.addOrder(Order.desc("id"));
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
