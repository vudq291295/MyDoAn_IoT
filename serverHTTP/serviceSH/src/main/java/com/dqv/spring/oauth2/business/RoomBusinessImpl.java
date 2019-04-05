package com.dqv.spring.oauth2.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.dqv.spring.oauth2.DAO.RoomDAO;
import com.dqv.spring.oauth2.bo.RoomBO;

@Service("roomBusinessImpl")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RoomBusinessImpl implements RoomBusiness{
    @Autowired
    private RoomDAO roomDAO;
    
	@Override
	public List<RoomBO> getAllRoom() {
		List<RoomBO> temp = new ArrayList<RoomBO>();
		temp = roomDAO.getAllRoom();
		return temp;
	}

	@Override
	public boolean insertRoom(RoomBO bo) {
		if(roomDAO.insertRoom(bo)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean updateRoom(RoomBO bo) {
		if(roomDAO.updateRoom(bo)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean deleteRoom(RoomBO bo) {
		if(roomDAO.deleteRoom(bo)) {
			return true;
		}
		else {
			return false;
		}
	}

}
