package com.dqv.spring.oauth2.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.dqv.spring.oauth2.DAO.EpuipmentDAO;
import com.dqv.spring.oauth2.DAO.RoomDAO;
import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.helper.Response;

@Service("roomBusinessImpl")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RoomBusinessImpl implements RoomBusiness{
    @Autowired
    private RoomDAO roomDAO;
    
    @Autowired
    private EpuipmentDAO epuipmentDAO;

	@Override
	public Response<List<RoomBO>> getAllRoom(RoomBO bo) {
		Response<List<RoomBO>> result = new Response<>();
		List<RoomBO> temp = new ArrayList<RoomBO>();
		temp = roomDAO.getAllRoom(bo);
		result.error = false;
		result.data = temp;
		return result;
	}

	@Override
	public Response<Boolean> insertRoom(RoomBO bo) {
		Response<Boolean> result = new Response<>();
		if(roomDAO.getRoomByChanel(bo.getChanel()) != null & roomDAO.getRoomByChanel(bo.getChanel()).size() > 0){
        	result.error = true;
        	result.message = "Đã tồn tại phòng với kênh "+bo.getChanel();

		}
		else {
			if(roomDAO.insertRoom(bo)) {
		        result.error = false;
		        result.message = "Thành công";

			}
			else {
	        	result.error = true;
	        	result.message = "Đã xảy ra lỗi trong quá trình thêm mới";
			}
		}
		return result;
	}
	
	@Override
	public Response<Boolean> updateRoom(RoomBO bo) {
		Response<Boolean> result = new Response<>();
		System.out.println(bo.getChanel());
		System.out.println(roomDAO.getRoomByID(bo.getId()).getChanel());
		if(bo.getChanel() == roomDAO.getRoomByID(bo.getId()).getChanel()) {
			if(roomDAO.updateRoom(bo)) {
		        result.error = false;
		        result.message = "Thành công";

			}
			else {
	        	result.error = true;
	        	result.message = "Đã xảy ra lỗi trong quá trình thêm mới";
			}
		}
		else {
			if(roomDAO.getRoomByChanel(bo.getChanel()) != null & roomDAO.getRoomByChanel(bo.getChanel()).size() > 0){
	        	result.error = true;
	        	result.message = "Đã tồn tại phòng với kênh "+bo.getChanel();
			}
			else {
				if(roomDAO.updateRoom(bo)) {
			        result.error = false;
			        result.message = "Thành công";

				}
				else {
		        	result.error = true;
		        	result.message = "Đã xảy ra lỗi trong quá trình thêm mới";
				}
			}
		}
		return result;
	}

	@Override
	public Response<Boolean> deleteRoom(RoomBO bo) {
		Response<Boolean> result = new Response<>();
		if(epuipmentDAO.getEpuipmentByRoom(bo.getId()) != null && epuipmentDAO.getEpuipmentByRoom(bo.getId()).size() > 0){
        	result.error = true;
        	result.message = "Tồn tại thiết bị thuộc phòng "+bo.getName();

		}
		else {
			if(roomDAO.deleteRoom(bo)) {
		        result.error = false;
		        result.message = "Thành công";

			}
			else {
	        	result.error = true;
	        	result.message = "Đã xảy ra lỗi trong quá trình xóa";
			}
		}
		return result;
	}

}
