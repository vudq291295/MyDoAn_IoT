package com.dqv.spring.oauth2.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.dqv.spring.oauth2.DTO.EnvironmentDTO;
import com.dqv.spring.oauth2.bo.EnvironmentBO;
import com.dqv.spring.oauth2.bo.RoomBO;

@Transactional
public class EnvironmentDAO {
	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }

		public List<EnvironmentDTO> getAllEnviroment() {
			List<EnvironmentDTO> result = new ArrayList<EnvironmentDTO>();
			try {
			        Session session = this.sessionFactory.getCurrentSession();
			        Query query = session.createQuery("select distinct(roomId) from EnvironmentBO"); 
			        List<Integer> resultInt = query.list();
			        System.out.println("size: "+resultInt.size());

			        if(resultInt.size() > 0) {
			        	for(int i =0;i<resultInt.size();i++) {
			        		System.out.println("resultInt.get(i): "+resultInt.get(i));
//					        Query query3 = session.createQuery("from roomBO where id = :roomId");
//					        query3.setParameter("roomId", resultInt.get(i));
//					        List<RoomBO> tempRoom =  query3.list();
//					        if(tempRoom.size()>0) {
//					        	
//					        }
					        Query query2 = session.createQuery("from EnvironmentBO where time = (SELECT MAX(time) FROM EnvironmentBO where roomId = :roomId) and roomId = :roomId");
					        query2.setParameter("roomId", resultInt.get(i));
					        List<EnvironmentBO> temp =  query2.list();
					        System.out.println("temo: "+temp.size());
					        if(temp.size()>0) {
					        	EnvironmentDTO tempBO = temp.get(0).toDTO();
						        Query query3 = session.createQuery("from RoomBO Where id = :id");
						        query3.setParameter("id", temp.get(0).getRoomId());
						        List<RoomBO> result2 =  query3.list();
						        if(result2.size()>0) {
						        	tempBO.setRoomName(result2.get(0).getName());

						        }
					        	result.add(tempBO);
					        	
					        }
			        	}
			        }
			        System.out.println("size2: "+result.size());

			        return result;
//		        }
			}
			catch (Exception e) {
				System.out.println(e);
		        return result;
			}
	    }
}
