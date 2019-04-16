package com.dqv.spring.oauth2.business;

import java.util.List;

import com.dqv.spring.oauth2.DTO.EquipmentDTO;
import com.dqv.spring.oauth2.bo.EquipmentBO;
import com.dqv.spring.oauth2.helper.Response;

public interface EquipmentBusiness {

	Response<List<EquipmentDTO>> getAllEpuipment(EquipmentBO bo);
	
	Response<List<EquipmentDTO>> getEpuipmentByRoom(int idRoom);
	
	Response<Boolean> insertEpuipment(EquipmentBO bo);
	
	Response<Boolean> updateEpuipment(EquipmentBO bo);
	
	Response<Boolean> deleteEpuipment(EquipmentBO bo);
}
