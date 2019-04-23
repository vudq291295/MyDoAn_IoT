package com.dqv.spring.oauth2.DAO;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.transaction.annotation.Transactional;

import com.dqv.spring.oauth2.bo.MenuBO;
import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.bo.UserBO;
import com.dqv.spring.oauth2.provider.MyUserPrincipal;

@Transactional
public class MenuDAO {
	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sessionFactory) {
	      this.sessionFactory = sessionFactory;
	  }
	  
		public List<MenuBO> getAllMenu() {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			int typeUser = 0;
			if (principal instanceof MyUserPrincipal) {
				  typeUser = ((MyUserPrincipal)principal).getUser().getType();
					System.out.println("if: "+typeUser);

				}
			List<MenuBO> result = new ArrayList<MenuBO>();
			try {
		        Session session = this.sessionFactory.getCurrentSession();
		        Query query = session.createQuery("from MenuBO where id in (select menuId from GroupMenuBO where groupId = :groupId) order by sTT");
		        query.setParameter("groupId", typeUser);
		        
		        result =  query.list();
		        return result;
			}
			catch (Exception e) {
		        return result;
			}
	    }
}
