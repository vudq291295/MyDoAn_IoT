package vudq.service.base.dto;

import java.io.Serializable;

import vudq.service.base.model.BaseFWModelImpl;

/**
*
* @author vudq
* @version 1.0
* @param <TBO>
* @since Sep 2017
*/
public abstract class BaseFWDTOImpl<TBO extends BaseFWModelImpl> implements BaseFWDTO<TBO>, Serializable {

   protected String defaultSortField = "name";

   public String getDefaultSortField() {
       return defaultSortField;
   }

   public void setDefaultSortField(String defaultSortField) {
       this.defaultSortField = defaultSortField;
   }

   @Override
   public int compareTo(BaseFWDTO o) {
       return catchName().compareTo(o.catchName());
   }

}
