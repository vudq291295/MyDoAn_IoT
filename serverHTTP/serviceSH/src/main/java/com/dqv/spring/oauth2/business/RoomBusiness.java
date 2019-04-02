package com.dqv.spring.oauth2.business;

import java.util.List;

import com.dqv.spring.oauth2.bo.RoomBO;

public interface RoomBusiness {
	
	List<RoomBO> getAllRoom();
	
	boolean insertRoom(RoomBO bo); 
}
