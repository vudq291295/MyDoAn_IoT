package com.dqv.spring.oauth2.business;

import java.util.List;

import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.helper.Response;

public interface RoomBusiness {
	
	Response<List<RoomBO>> getAllRoom();
	
	Response<Boolean> insertRoom(RoomBO bo); 
	
	Response<Boolean> updateRoom(RoomBO bo);
	
	Response<Boolean> deleteRoom(RoomBO bo);
}
