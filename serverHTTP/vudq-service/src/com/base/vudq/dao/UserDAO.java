package com.base.vudq.dao;

import org.springframework.stereotype.Repository;

import com.base.vudq.bo.UserBO;
import com.base.vudq.dto.UserDTO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

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

}
