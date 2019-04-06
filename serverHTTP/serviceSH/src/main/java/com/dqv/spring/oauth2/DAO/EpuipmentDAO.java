package com.dqv.spring.oauth2.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dqv.spring.oauth2.bo.EquipmentBO;
import com.dqv.spring.oauth2.bo.RoomBO;

@Transactional
public class EpuipmentDAO {
	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }
	  
		public List<EquipmentBO> getAllEpuipment() {
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        Query query = session.createQuery("from EquipmentBO");
		        List<EquipmentBO> result=  query.list();
		        return result;
			}
			catch (Exception e) {
		        return null;
			}
	    }
	
		public List<EquipmentBO> getEpuipmentByRoom(int idRoom) {
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        Query query = session.createQuery("from EquipmentBO where roomId = :idRoom");
		        query.setParameter("idRoom", idRoom);
		        List<EquipmentBO> result=  query.list();
		        return result;
			}
			catch (Exception e) {
		        return null;
			}
	    }

		public List<EquipmentBO> getEpuipmentByScript(int scripID) {
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        Query query = session.createQuery("from EquipmentBO where roomId in (select equipmentID from ScriptHasEquimentBO where scripID = :scripID)");
		        query.setParameter("scripID", scripID);
		        List<EquipmentBO> result=  query.list();
		        return result;
			}
			catch (Exception e) {
		        return null;
			}
	    }

		public boolean insertEpuipment(EquipmentBO bo) {
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
		
		public boolean updateEpuipment(EquipmentBO bo) {
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

		public boolean deleteEpuipment(EquipmentBO bo) {
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
