package com.dqv.spring.oauth2.DAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.bo.UserBO;
import com.dqv.spring.oauth2.helper.Response;
import com.dqv.spring.oauth2.helper.Util;

import java.util.ArrayList;
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

	public List<UserBO> getAllUser(UserBO bo) {
		List<UserBO> result = new ArrayList<UserBO>();
		UserBO currentUser = Util.getCurrentUser();
		try {
	        Session session = this.sessionFactory.getCurrentSession();
	        if(bo!=null) {
	        	System.out.println(currentUser.getUnitCode());
	        	String unitCode = currentUser.getUnitCode().equals("HT") ? "" : currentUser.getUnitCode();
	        	System.out.println(unitCode);
	        	String nameRoomSearch = bo.getUserName()!=null ? bo.getUserName() : "";
		        Query query = session.createQuery("from UserBO where userName like :search and unitCode like :unitCode");
		        query.setParameter("search", "%"+nameRoomSearch+"%");
		        query.setParameter("unitCode", "%"+unitCode+"%");
//		        query.setParameter("type", ""+currentUser.getType()+","+(currentUser.getType()+1)+"");
		        System.out.println(""+currentUser.getType()+","+(currentUser.getType()+1)+"");
		        System.out.println(query.getQueryString());

		        result =  query.list();
		        System.out.println(result.size());
		        return result;
	        }
	        else {
	        	String unitCode = currentUser.getUnitCode().equals("HT") ? "" : currentUser.getUnitCode();
		        Query query = session.createQuery("from UserBO where unitCode like :unitCode and type in :type");
		        query.setParameter("unitCode", "%"+unitCode+"%");
		        query.setParameter("type", "("+currentUser.getType()+","+(currentUser.getType()+1)+")");
		        System.out.println("("+currentUser.getType()+","+(currentUser.getType()+1)+")");

		        result =  query.list();
		        return result;
	        }
		}
		catch (Exception e) {
			System.out.println(e);
	        return result;
		}
    }
	
	public boolean insertUser(UserBO bo) {
		try {
	        Session session = this.sessionFactory.getCurrentSession();
	        session.save(bo);
	        return true;
		}
		catch (Exception e) {
	        return false;
		}
    }
	
	public boolean updateUser(UserBO bo) {
		try {
	        Session session = this.sessionFactory.getCurrentSession();
	        session.merge(bo);
	        return true;
		}
		catch (Exception e) {
	        return false;
		}
    }

	public boolean deleteUser(UserBO bo) {
		Response<Boolean> result = new Response<>();
		try {
	        Session session = this.sessionFactory.getCurrentSession();
	        session.delete(bo);
	        return true;
		}
		catch (Exception e) {
			// TODO: handle exception
	        return false;

		}
    }
}
