package com.dqv.spring.oauth2.DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dqv.spring.oauth2.DTO.ScriptDTO;
import com.dqv.spring.oauth2.DTO.ScriptDetailDTO;
import com.dqv.spring.oauth2.bo.EquipmentBO;
import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.bo.ScriptBO;
import com.dqv.spring.oauth2.bo.ScriptHasEquimentBO;

@Transactional
public class ScriptDAO {
	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }

		public List<ScriptBO> getAllScript(ScriptBO bo) {
			List<ScriptBO> result = new ArrayList<ScriptBO>();

			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        if(bo!=null) {
		        	String nameRoomSearch = bo.getName()!=null ? bo.getName() : "";
			        Query query = session.createQuery("from ScriptBO where name like :search");
			        query.setParameter("search", "%"+nameRoomSearch+"%");
			        result =  query.list();
			        return result;
		        }
		        else {
			        Query query = session.createQuery("from ScriptBO");
			        result =  query.list();
			        return result;
		        }
			}
			catch (Exception e) {
		        return null;
			}
	    }

		public ScriptDTO getDetailsScript(int idScript) {
			try {
				ScriptDTO result = new ScriptDTO();
				List<ScriptDetailDTO> resultDetail = new ArrayList<ScriptDetailDTO>();
		        Session session = this.sessionFactory.getCurrentSession();
		        Query query = session.createQuery("from ScriptBO where id = :idScript");
		        query.setParameter("idScript", idScript);
		        List<?> list = query.list();
		        ScriptBO resultBO =  (ScriptBO)list.get(0);
		        if(resultBO!= null) {
		        	result = resultBO.toDTO();
			        //get details
			        Query query2 = session.createQuery("from ScriptHasEquimentBO where scripID = :idScript");
			        query2.setParameter("idScript", idScript);
			        List<ScriptHasEquimentBO> list2 = query2.list();
			        if(list2.size() > 0) {
			        	for(int i =0;i<list2.size();i++) {
			        		ScriptDetailDTO objDTO = new ScriptDetailDTO();
			        		objDTO = list2.get(i).toDTO();
			        		
					        Query query3 = session.createQuery("from EquipmentBO where id = :idEquip");
					        query3.setParameter("idEquip", objDTO.getEquipmentID());
					        List<?> list3 = query3.list();
					        EquipmentBO resultEquiptBO =  (EquipmentBO)list3.get(0);
					        if(resultEquiptBO!=null) {
						        objDTO.setEquipmentPort(resultEquiptBO.getPortOutput());
						        objDTO.setEquipmentName(resultEquiptBO.getName());
						        objDTO.setRoomID(resultEquiptBO.getRoomId());
						        
						        Query query4 = session.createQuery("from RoomBO where id = :idRoom");
						        query4.setParameter("idRoom", objDTO.getRoomID());
						        List<?> list4 = query4.list();
						        RoomBO resultRoomBO =  (RoomBO)list4.get(0);
						        if(resultRoomBO != null) {
						        	objDTO.setEquipmentChanel(resultRoomBO.getChanel());
						        	objDTO.setRoomName(resultRoomBO.getName());
						        }
					        }
			        		resultDetail.add(objDTO);		
		        		}
				        result.setDetails(resultDetail);
			        }
		        }
		        return result;
			}
			catch (Exception e) {
				System.out.println(e.toString());
		        return null;
			}
	    }
		
		public boolean insertScript(ScriptDTO dto) {
			try {
				ScriptBO bo = dto.toBO();
				System.out.println(bo.getName());
		        Session session = this.sessionFactory.getCurrentSession();
//		        Serializable a = session.save(bo);
		        Integer a = (Integer)session.save(bo);
		        if(a>0) {
		        	if(dto.details.size() > 0) {
		        		for(int i =0;i<dto.details.size();i++) {
		        			ScriptHasEquimentBO boDetail = new ScriptHasEquimentBO();
		        			boDetail = dto.details.get(i).toBO();
		        			boDetail.setScripID(a);
		        			session.save(boDetail);
		        		}
		        	}
		        }
		        return true;
			}
			catch (Exception e) {
				System.out.println(e);
		        return false;
			}
	    }
		
		public boolean updateScript(ScriptDTO dto) {
			try {
				ScriptBO bo = dto.toBO();
				System.out.println(bo.getName());
		        Session session = this.sessionFactory.getCurrentSession();
//		        Serializable a = session.save(bo);
		        session.update(bo);
		        Query query2 = session.createQuery("from ScriptHasEquimentBO where scripID = :idScript");
		        query2.setParameter("idScript", dto.getId());
		        List<ScriptHasEquimentBO> list2 = query2.list();
		        for(int i =0;i<list2.size();i++) {
		        	session.delete(list2.get(i));
		        	System.out.println(list2.get(i).getEquipmentID());
		        }
	        	if(dto.details.size() > 0) {
	        		for(int i =0;i<dto.details.size();i++) {
	        			
	        			ScriptHasEquimentBO boDetail = new ScriptHasEquimentBO();
	        			boDetail = dto.details.get(i).toBO();
	        			boDetail.setScripID(dto.getId());
	        			session.save(boDetail);
			        	System.out.println(dto.details.get(i).getEquipmentID());

	        		}
	        	}
		        return true;
			}
			catch (Exception e) {
				System.out.println(e);
		        return false;
			}
	    }

		public boolean deleteScript(ScriptDTO dto) {
			try {
				ScriptBO bo = dto.toBO();
				System.out.println(bo.getName());
		        Session session = this.sessionFactory.getCurrentSession();
		        
		        Query query2 = session.createQuery("from ScriptHasEquimentBO where scripID = :idScript");
		        query2.setParameter("idScript", dto.getId());
		        List<ScriptHasEquimentBO> list2 = query2.list();
		        for(int i =0;i<list2.size();i++) {
		        	session.delete(list2.get(i));
		        }

//		        Serializable a = session.save(bo);
		        System.out.println(bo.getId());
		        session.delete(bo);
		        
		        return true;
			}
			catch (Exception e) {
				System.out.println(e);
		        return false;
			}
	    }

}
