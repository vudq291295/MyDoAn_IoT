package com.dqv.spring.oauth2.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.dqv.spring.oauth2.DAO.EpuipmentDAO;
import com.dqv.spring.oauth2.bo.EquipmentBO;

@Service("equipmentBusinessImpl")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EquipmentBusinessImpl implements EquipmentBusiness{
	
	@Autowired
	EpuipmentDAO epuipmentDAO;

	@Override
	public List<EquipmentBO> getAllEpuipment() {
		List<EquipmentBO> temp = new ArrayList<EquipmentBO>();
		temp = epuipmentDAO.getAllEpuipment();
		return temp;
	}

	@Override
	public List<EquipmentBO> getEpuipmentByRoom(int idRoom) {
		List<EquipmentBO> temp = new ArrayList<EquipmentBO>();
		temp = epuipmentDAO.getEpuipmentByRoom(idRoom);
		return temp;
	}

	@Override
	public boolean insertEpuipment(EquipmentBO bo) {
		if(epuipmentDAO.insertEpuipment(bo)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean updateEpuipment(EquipmentBO bo) {
		if(epuipmentDAO.updateEpuipment(bo)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean deleteEpuipment(EquipmentBO bo) {
		if(epuipmentDAO.deleteEpuipment(bo)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	

}
