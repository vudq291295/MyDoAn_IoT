package vudq.service.base.business;

import java.util.Date;
import java.util.List;

import vudq.service.base.dto.BaseFWDTOImpl;
import vudq.service.base.model.BaseFWModelImpl;

public interface BaseFWBusiness<TDTO extends BaseFWDTOImpl, TModel extends BaseFWModelImpl> {
    List<TDTO> getAll();

    BaseFWDTOImpl getOneById(Long costCenterId);

    Long save(TDTO costCenterBO);

    Long update(TDTO costCenterBO);

    void delete(TDTO costCenterBO);

//    List<TDTO> searchByHql(String hql, List<ConditionBean> conditionBeans);
//
//    List<TDTO> searchByHql(String hql, List<ConditionBean> conditionBeans, int startIdx, int endIdx);
//
//    Long countByHql(String hql, List<ConditionBean> conditionBeans);
//
//    int executeByHql(String hql, List<ConditionBean> conditionBeans);

    Date getSysDate() throws Exception;

    Long getNextValSequence(String sequense);

}
