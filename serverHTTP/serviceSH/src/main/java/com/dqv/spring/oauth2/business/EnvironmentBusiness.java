package com.dqv.spring.oauth2.business;

import java.util.List;

import com.dqv.spring.oauth2.DTO.EnvironmentDTO;
import com.dqv.spring.oauth2.bo.EnvironmentBO;
import com.dqv.spring.oauth2.bo.EquipmentBO;
import com.dqv.spring.oauth2.helper.Response;

public interface EnvironmentBusiness {
	Response<List<EnvironmentDTO>> getAllEnviroment();

}
