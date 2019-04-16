package com.dqv.spring.oauth2.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dqv.spring.oauth2.bo.EquipmentBO;
import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.helper.Response;

@Transactional
public class RoomDAO {
	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }
	  
	public List<RoomBO> getAllRoom(RoomBO bo) {
		List<RoomBO> result = new ArrayList<RoomBO>();
		try {
	        Session session = this.sessionFactory.getCurrentSession();
	        if(bo!=null) {
	        	String nameRoomSearch = bo.getName()!=null ? bo.getName() : "";
		        Query query = session.createQuery("from RoomBO where name like :search");
		        query.setParameter("search", "%"+nameRoomSearch+"%");
		        result =  query.list();
		        return result;
	        }
	        else {
		        Query query = session.createQuery("from RoomBO");
		        result =  query.list();
		        return result;
	        }
		}
		catch (Exception e) {
	        return result;
		}
    }

	public List<RoomBO> getRoomByChanel(int id) {
		List<RoomBO> result = new ArrayList<RoomBO>();
		try {
	        Session session = this.sessionFactory.getCurrentSession();
	        Query query = session.createQuery("from RoomBO where chanel = :chanel");
	        query.setParameter("chanel", id);
	        result=  query.list();
	        return result;
		}
		catch (Exception e) {
	        return result;
		}
    }

	public RoomBO getRoomByID(int id) {
		RoomBO result = new RoomBO();
		try {
	        Session session = this.sessionFactory.getCurrentSession();
	        Query query = session.createQuery("from RoomBO where id = :id");
	        query.setParameter("id", id);
	        List<RoomBO> temp =  query.list();
	        result = temp.get(0);
	        System.out.println(result.getName());
	        return result;
		}
		catch (Exception e) {
	        return result;
		}
    }

	public boolean insertRoom(RoomBO bo) {
		try {
	        Session session = this.sessionFactory.getCurrentSession();
	        session.save(bo);
	        return true;
		}
		catch (Exception e) {
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
	        return false;
		}
    }

	public boolean deleteRoom(RoomBO bo) {
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
