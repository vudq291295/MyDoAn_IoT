package com.dqv.spring.oauth2.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dqv.spring.oauth2.DTO.ScheduleDTO;
import com.dqv.spring.oauth2.bo.EquipmentBO;
import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.bo.ScheduleBO;


@Transactional
public class ScheduleDAO {
	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }
	  
		public List<ScheduleDTO> getAllScheduleEquip(ScheduleBO bo) {
			List<ScheduleDTO> result = new ArrayList<ScheduleDTO>();
			List<ScheduleBO> resultBO = new ArrayList<ScheduleBO>();
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        if(bo!=null) {
		        	String nameRoomSearch = bo.getName()!=null ? bo.getName() : "";
			        Query query = session.createQuery("from ScheduleBO WHERE equipmentID > 0 and name like :search");
			        query.setParameter("search", "%"+nameRoomSearch+"%");
			        resultBO =  query.list();
		        }
		        else {
			        Query query = session.createQuery("from ScheduleBO WHERE equipmentID > 0");
			        resultBO =  query.list();
		        }
		        if(resultBO.size()>0) {
		        	for(int i =0;i<resultBO.size();i++) {
		        		ScheduleDTO resultTemp = new ScheduleDTO();
		        		resultTemp = resultBO.get(i).toDTO();
		    			List<EquipmentBO> resultQeuipBO = new ArrayList<EquipmentBO>();
				        Query query3 = session.createQuery("from EquipmentBO WHERE id = :id");
				        query3.setParameter("id",resultBO.get(i).getEquipmentID());
				        resultQeuipBO =  query3.list();
				        System.out.println(resultQeuipBO.size());
				        if(resultQeuipBO.size()>0) {
				        	resultTemp.setEquipmentName(resultQeuipBO.get(0).getName());
				        }
				        result.add(resultTemp);
				        System.out.println("result.size(): "+result.size());
		        	}
		        }
		        return result;

			}
			catch (Exception e) {
		        return result;
			}
	    }
		
		public List<ScheduleDTO> getAllScheduleScrpit(ScheduleBO bo) {
			List<ScheduleDTO> result = new ArrayList<ScheduleDTO>();
			List<ScheduleBO> resultBO = new ArrayList<ScheduleBO>();
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        if(bo!=null) {
		        	String nameRoomSearch = bo.getName()!=null ? bo.getName() : "";
			        Query query = session.createQuery("from ScheduleBO WHERE scriptID > 0 and name like :search");
			        query.setParameter("search", "%"+nameRoomSearch+"%");
			        resultBO =  query.list();
		        }
		        else {
			        Query query = session.createQuery("from ScheduleBO WHERE scriptID > 0");
			        resultBO =  query.list();
		        }
		        if(resultBO.size()>0) {
		        	for(int i =0;i<resultBO.size();i++) {
		        		ScheduleDTO resultTemp = new ScheduleDTO();
		        		resultTemp = resultBO.get(i).toDTO();
		    			List<EquipmentBO> resultQeuipBO = new ArrayList<EquipmentBO>();
				        Query query3 = session.createQuery("from EquipmentBO WHERE id = :id");
				        query3.setParameter("id",resultBO.get(i).getEquipmentID());
				        resultQeuipBO =  query3.list();
				        System.out.println(resultQeuipBO.size());
				        if(resultQeuipBO.size()>0) {
				        	resultTemp.setEquipmentName(resultQeuipBO.get(0).getName());
				        }
				        result.add(resultTemp);
				        System.out.println("result.size(): "+result.size());
		        	}
		        }
		        return result;

			}
			catch (Exception e) {
		        return result;
			}
	    }
		public boolean insertSchedule(ScheduleDTO bo) {
			try {
				ScheduleBO objBO = bo.toBO();
		        Session session = this.sessionFactory.getCurrentSession();
		        objBO.getTimeStart().setSeconds(0);
		        objBO.setStatus(1);
		        session.save(objBO);
		        return true;
			}
			catch (Exception e) {
		        return false;
			}
	    }

		public boolean updateSchedule(ScheduleDTO bo) {
			try {
				ScheduleBO objBO = bo.toBO();
		        Session session = this.sessionFactory.getCurrentSession();
		        session.merge(objBO);
		        return true;
			}
			catch (Exception e) {
		        return false;
			}
	    }

		public boolean deleteSchedule(ScheduleDTO bo) {
			try {
				ScheduleBO objBO = bo.toBO();
		        Session session = this.sessionFactory.getCurrentSession();
		        session.delete(objBO);
		        return true;
			}
			catch (Exception e) {
		        return false;
			}
	    }

}
