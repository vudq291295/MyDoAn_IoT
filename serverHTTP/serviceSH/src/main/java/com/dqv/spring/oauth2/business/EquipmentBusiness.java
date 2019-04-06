package com.dqv.spring.oauth2.business;

import java.util.List;

import com.dqv.spring.oauth2.bo.EquipmentBO;

public interface EquipmentBusiness {

	List<EquipmentBO> getAllEpuipment();
	
	List<EquipmentBO> getEpuipmentByRoom(int idRoom);
	
	boolean insertEpuipment(EquipmentBO bo);
	
	boolean updateEpuipment(EquipmentBO bo);
	
	boolean deleteEpuipment(EquipmentBO bo);
}
