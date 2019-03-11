package vudq.service.base.dto;

import vudq.service.base.model.BaseFWModelImpl;

public interface BaseFWDTO<TModel extends BaseFWModelImpl> extends Comparable<BaseFWDTO> {
    TModel toModel();
    Long getFWModelId();
    String catchName();
   
}