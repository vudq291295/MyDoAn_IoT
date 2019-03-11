package vudq.service.base.model;

import vudq.service.base.dto.BaseFWDTOImpl;

public interface BaseFwModel<TDTO extends BaseFWDTOImpl> extends java.io.Serializable {
    public TDTO toDTO();
}
