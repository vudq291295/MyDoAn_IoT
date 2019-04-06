package com.dqv.spring.oauth2.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dqv.spring.oauth2.DTO.ScriptDTO;
import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.bo.ScriptBO;

@Transactional
public class ScriptDAO {
	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }

		public List<ScriptBO> getAllScript() {
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        Query query = session.createQuery("from ScriptBO");
		        List<ScriptBO> result=  query.list();
		        return result;
			}
			catch (Exception e) {
		        return null;
			}
	    }

		public ScriptDTO getDetailsScript(int idScript) {
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        Query query = session.createQuery("from ScriptBO where id = :idScript");
		        query.setParameter("idScript", idScript);
		        List<?> list = query.list();
		        
		        ScriptDTO result=  (ScriptDTO)list.get(0);
		        
		        //get details
		        Query query2 = session.createQuery("from ScriptHasEquimentBO where scripID = :idScript");
		        query2.setParameter("idScript", idScript);
		        List<?> list2 = query.list();

		        return result;
			}
			catch (Exception e) {
		        return null;
			}
	    }

}
