package com.dqv.spring.oauth2.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dqv.spring.oauth2.bo.RoomBO;

@Transactional
public class RoomDAO {
	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }
	  
	public List<RoomBO> getAllRoom() {
		try {
	        Session session = this.sessionFactory.getCurrentSession();
	        Query query = session.createQuery("from RoomBO");
	        List<RoomBO> result=  query.list();
	        return result;
		}
		catch (Exception e) {
	        return null;
		}
    }

	public boolean insertRoom(RoomBO bo) {
		try {
	        Session session = this.sessionFactory.getCurrentSession();
	        session.save(bo);
	        return true;
		}
		catch (Exception e) {
			// TODO: handle exception
	        return false;

		}
    }
	
	public boolean updateRoom(RoomBO bo) {
		try {
	        Session session = this.sessionFactory.getCurrentSession();
	        session.merge(bo);
	        return true;
		}
		catch (Exception e) {
			// TODO: handle exception
	        return false;

		}
    }

	public boolean deleteRoom(RoomBO bo) {
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
