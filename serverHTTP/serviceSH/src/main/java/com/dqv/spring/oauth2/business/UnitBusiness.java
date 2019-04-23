package com.dqv.spring.oauth2.business;

import java.util.List;

import com.dqv.spring.oauth2.bo.UnitBO;
import com.dqv.spring.oauth2.helper.Response;

public interface UnitBusiness {
	Response<List<UnitBO>> getAllUnit(UnitBO bo);
	
	Response<Boolean> insertUnit(UnitBO bo); 
	
	Response<Boolean> updateUnit(UnitBO bo);
	
	Response<Boolean> deleteUnit(UnitBO bo);

}
