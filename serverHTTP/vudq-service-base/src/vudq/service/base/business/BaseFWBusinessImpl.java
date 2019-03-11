package vudq.service.base.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import vudq.service.base.dao.BaseFWDAOImpl;
import vudq.service.base.dto.BaseFWDTOImpl;
import vudq.service.base.model.BaseFWModelImpl;

@Transactional
public class BaseFWBusinessImpl<TDAO extends BaseFWDAOImpl, TDTO extends BaseFWDTOImpl, 
TModel extends BaseFWModelImpl> implements BaseFWBusiness<TDTO, TModel> {

	@Override
	public List<TDTO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseFWDTOImpl getOneById(Long costCenterId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long save(TDTO costCenterBO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long update(TDTO costCenterBO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(TDTO costCenterBO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getSysDate() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getNextValSequence(String sequense) {
		// TODO Auto-generated method stub
		return null;
	}

	   //gen code
    public List convertListModeltoDTO(List<TModel> listModel) {
        List<BaseFWDTOImpl> lstForm = new ArrayList<BaseFWDTOImpl>();
        if (listModel != null) {
            for (TModel model : listModel) {
                lstForm.add(model.toDTO());
            }
        }

        return lstForm;
    }

    public List convertListDTOtoModel(List<TDTO> listDTO) {

        List<BaseFWModelImpl> lstModel = new ArrayList<BaseFWModelImpl>();
        if (listDTO != null) {
            for (TDTO dto : listDTO) {
                lstModel.add(dto.toModel());
            }
        }
        return lstModel;
    }

}	
