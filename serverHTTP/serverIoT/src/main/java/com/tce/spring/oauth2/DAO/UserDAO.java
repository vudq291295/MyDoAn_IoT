package com.tce.spring.oauth2.DAO;

import org.springframework.stereotype.Repository;

import com.tce.spring.oauth2.bo.UserBO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import vudq.service.base.dao.BaseFWDAOImpl;

@Repository("userDAO")
public class UserDAO  extends BaseFWDAOImpl<UserBO, Long> {
    public UserDAO() {
        this.model = new UserBO();
    }

    public UserDAO(Session session) {
        this.session = session;
    }

    public List<UserBO> getAllUsers() {
        Criteria cr = getSession().createCriteria(UserBO.class);
//        cr.addOrder(Order.desc("id"));
        List<UserBO> lstAddressBO = cr.list();
        return lstAddressBO;
    }

	public UserBO getUserByUP(UserBO bo) {
        Criteria cr = getSession().createCriteria(UserBO.class);
        cr.add(Expression.eq("userName",bo.getUserName()).ignoreCase());
        cr.add(Expression.eq("passWord",bo.getPassWord()).ignoreCase());
//        cr.addOrder(Order.desc("id"));
        UserBO result=  (UserBO) cr.uniqueResult();
        return result;
    }

	public UserBO findUserByUsername(String bo) {
        System.out.println(bo);
        Criteria cr = getSession().createCriteria(UserBO.class);
        System.out.println("2 "+bo);
        cr.add(Expression.eq("username",bo).ignoreCase());
//        cr.addOrder(Order.desc("id"));
        UserBO result=  (UserBO) cr.uniqueResult();
        return result;
    }

}
