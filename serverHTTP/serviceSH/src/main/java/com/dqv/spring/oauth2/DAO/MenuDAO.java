package com.dqv.spring.oauth2.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dqv.spring.oauth2.bo.MenuBO;
import com.dqv.spring.oauth2.bo.RoomBO;

@Transactional
public class MenuDAO {
	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }
	  
		public List<MenuBO> getAllMenu() {
			List<MenuBO> result = new ArrayList<MenuBO>();
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        Query query = session.createQuery("from MenuBO");
		        result =  query.list();
		        return result;
			}
			catch (Exception e) {
		        return result;
			}
	    }
}
