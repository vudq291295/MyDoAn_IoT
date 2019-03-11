package vudq.service.base.model;

import vudq.service.base.dto.BaseFWDTOImpl;

/**
*
* @author vudq
* @version 1.0
* @since Oct 2012
*/
public abstract class BaseFWModelImpl<TModel extends BaseFWModelImpl> implements BaseFwModel {
    private transient String colId = "ID";
    private transient String colName = "NAME";
    private transient String[] uniqueColumn = new String[0];
    
    public String getModelName() {
       return this.getClass().getSimpleName();
    }

    public String getColId() {
        return colId;
    }

    public void setColId(String colId) {
        this.colId = colId;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String[] getUniqueColumn() {
        return uniqueColumn;
    }

    public void setUniqueColumn(String[] uniqueColumn) {
        this.uniqueColumn = uniqueColumn;
    }

}
