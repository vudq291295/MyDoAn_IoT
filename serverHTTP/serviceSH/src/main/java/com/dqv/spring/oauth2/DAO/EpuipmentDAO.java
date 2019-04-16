package com.dqv.spring.oauth2.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dqv.spring.oauth2.DTO.EquipmentDTO;
import com.dqv.spring.oauth2.bo.EquipmentBO;
import com.dqv.spring.oauth2.bo.RoomBO;

@Transactional
public class EpuipmentDAO {
	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }
	  
	  public boolean isTrung(int roomID,int portNumber) {
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        Query query = session.createQuery("from EquipmentBO  where roomId = :idRoom and portOutput = :portOutput");
		        query.setParameter("idRoom", roomID);
		        query.setParameter("portOutput", portNumber);
		        System.out.println(roomID);
		        System.out.println(portNumber);
		        System.out.println(query.getQueryString());

		        List<EquipmentBO> result=  query.list();
		        System.out.println(result.size());
		        if(result.size() > 0) {
			        return true;
		        }
		        else {
					return false;
				}
			}
			catch (Exception e) {
		        return true;
			}
	  }
	  
		public EquipmentBO getEquipmentByID(int id) {
			EquipmentBO result = new EquipmentBO();
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        Query query = session.createQuery("from EquipmentBO where id = :id");
		        query.setParameter("id", id);
		        List<EquipmentBO> temp =  query.list();
		        result = temp.get(0);
		        System.out.println(result.getName());
		        return result;
			}
			catch (Exception e) {
		        return result;
			}
	    }

		public List<EquipmentDTO> getAllEpuipment(EquipmentBO bo) {
			try {
				System.out.println(bo);
				List<EquipmentBO> result = new ArrayList<>();
				List<EquipmentDTO> lstResul = new ArrayList<>();
		        Session session = this.sessionFactory.getCurrentSession();
		        if(bo!=null) {
		        	String nameRoomSearch = bo.getName()!=null ? bo.getName() : "";
			        Query query = session.createQuery("from EquipmentBO where name like :search");
			        query.setParameter("search", "%"+nameRoomSearch+"%");
			        result=  query.list();
		        }
		        else {
			        Query query = session.createQuery("from EquipmentBO");
			        result=  query.list();
		        }
		        System.out.println("result "+ result.size() );
		        if(result.size() > 0) {
			        for(int i = 0 ; i< result.size();i++) {
			        	EquipmentDTO model = new EquipmentDTO();
			        	model.setId(result.get(i).getId());
			        	model.setName(result.get(i).getName());
			        	model.setPortOutput(result.get(i).getPortOutput());
			        	model.setRoomId(result.get(i).getRoomId());
			        	model.setStatus(result.get(i).getStatus());
				        Query query2 = session.createQuery("from RoomBO Where id = :id");
				        query2.setParameter("id", result.get(i).getRoomId());
				        List<RoomBO> result2 =  query2.list();
				        if(result2.size()>0) {
				        	model.setChanel(result2.get(0).getChanel());
				        	model.setNameRoom(result2.get(0).getName());

				        }
				        lstResul.add(model);
			        }
		        }
		        return lstResul;
			}
			catch (Exception e) {
		        return null;
			}
	    }
		
		public List<EquipmentDTO> getEpuipmentByRoom(int idRoom) {
			List<EquipmentBO> result = new ArrayList<EquipmentBO>();
			List<EquipmentDTO> lstResul = new ArrayList<>();
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        Query query = session.createQuery("from EquipmentBO where roomId = :idRoom");
		        query.setParameter("idRoom", idRoom);
		        result =  query.list();
		        Query query2 = session.createQuery("from RoomBO Where id = :id");
		        query2.setParameter("id", idRoom);
		        List<RoomBO> result2 =  query2.list();
		        if(result.size() > 0) {
			        for(int i = 0 ; i< result.size();i++) {
			        	EquipmentDTO model = new EquipmentDTO();
			        	model.setId(result.get(i).getId());
			        	model.setName(result.get(i).getName());
			        	model.setPortOutput(result.get(i).getPortOutput());
			        	model.setRoomId(result.get(i).getRoomId());
			        	model.setStatus(result.get(i).getStatus());
				        if(result2.size()>0) {
				        	model.setChanel(result2.get(0).getChanel());
				        	model.setNameRoom(result2.get(0).getName());

				        }
				        lstResul.add(model);
			        }
		        }

		        return lstResul;
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
