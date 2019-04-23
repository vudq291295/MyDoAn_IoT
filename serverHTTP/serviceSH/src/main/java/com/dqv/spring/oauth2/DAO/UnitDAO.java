package com.dqv.spring.oauth2.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dqv.spring.oauth2.bo.UnitBO;
import com.dqv.spring.oauth2.helper.Response;

@Transactional
public class UnitDAO {
	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }
	  
		public List<UnitBO> getAllUnit(UnitBO bo) {
			List<UnitBO> result = new ArrayList<UnitBO>();
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        if(bo!=null) {
		        	String nameRoomSearch = bo.getName()!=null ? bo.getName() : "";
			        Query query = session.createQuery("from UnitBO where name like :search");
			        query.setParameter("search", "%"+nameRoomSearch+"%");
			        result =  query.list();
			        return result;
		        }
		        else {
			        Query query = session.createQuery("from UnitBO");
			        result =  query.list();
			        return result;
		        }
			}
			catch (Exception e) {
		        return result;
			}
	    }

		public boolean insertUnit(UnitBO bo) {
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        session.save(bo);
		        return true;
			}
			catch (Exception e) {
		        return false;
			}
	    }
		
		public boolean updateUnit(UnitBO bo) {
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        session.merge(bo);
		        return true;
			}
			catch (Exception e) {
		        return false;
			}
	    }

		public boolean deleteUnit(UnitBO bo) {
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
