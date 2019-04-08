package com.dqv.spring.oauth2.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.dqv.spring.oauth2.DAO.EpuipmentDAO;
import com.dqv.spring.oauth2.DTO.EquipmentDTO;
import com.dqv.spring.oauth2.bo.EquipmentBO;
import com.dqv.spring.oauth2.bo.RoomBO;
import com.dqv.spring.oauth2.helper.Response;

@Service("equipmentBusinessImpl")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EquipmentBusinessImpl implements EquipmentBusiness{
	
	@Autowired
	EpuipmentDAO epuipmentDAO;

	@Override
	public Response<List<EquipmentDTO>> getAllEpuipment() {
		Response<List<EquipmentDTO>> result = new Response<>();
		List<EquipmentDTO> temp = new ArrayList<EquipmentDTO>();
		temp = epuipmentDAO.getAllEpuipment();
		result.error = false;
		result.data = temp;
		return result;
	}

	@Override
	public Response<List<EquipmentDTO>> getEpuipmentByRoom(int idRoom) {
		Response<List<EquipmentDTO>> result = new Response<>();
		List<EquipmentDTO> temp = new ArrayList<EquipmentDTO>();
		temp = epuipmentDAO.getEpuipmentByRoom(idRoom);
		result.error = false;
		result.data = temp;
		return result;
	}

	@Override
	public Response<Boolean> insertEpuipment(EquipmentBO bo) {
		Response<Boolean> result = new Response<>();
		if(epuipmentDAO.isTrung(bo.getRoomId(), bo.getPortOutput())){
        	result.error = true;
        	result.message = "Đã tồn tại phòng với kênh "+bo.getPortOutput();

		}
		else {
			if(epuipmentDAO.insertEpuipment(bo)) {
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
	public Response<Boolean> updateEpuipment(EquipmentBO bo) {
		Response<Boolean> result = new Response<>();
		EquipmentBO currentBO = epuipmentDAO.getEquipmentByID(bo.getId());
		System.out.println(currentBO.getName());
		if(bo.getRoomId() == currentBO.getRoomId() && bo.getPortOutput() == currentBO.getPortOutput()) {
			if(epuipmentDAO.updateEpuipment(bo)) {
		        result.error = false;
		        result.message = "Thành công";

			}
			else {
	        	result.error = true;
	        	result.message = "Đã xảy ra lỗi trong quá trình thêm mới";
			}
		}
		else {
			if(epuipmentDAO.isTrung(bo.getRoomId(), bo.getPortOutput())){
	        	result.error = true;
	        	result.message = "Đã tồn tại phòng với kênh "+bo.getPortOutput();
			}
			else {
				if(epuipmentDAO.updateEpuipment(bo)) {
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
	public Response<Boolean> deleteEpuipment(EquipmentBO bo) {
		Response<Boolean> result = new Response<>();
		if(epuipmentDAO.deleteEpuipment(bo)) {
	        result.error = false;
	        result.message = "Thành công";

		}
		else {
        	result.error = true;
        	result.message = "Đã xảy ra lỗi trong quá trình xóa";
		}
		return result;
	}
	
	

}
