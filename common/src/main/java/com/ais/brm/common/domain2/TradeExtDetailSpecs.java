package com.ais.brm.common.domain2;

import java.util.Date;

import org.springframework.util.StringUtils;

/**
 * @author zhaocw.
 */
public class TradeExtDetailSpecs {
    private long id;
    private int tradeTypeCode;
    private String sourceTable;
    private String tableSchema;
    private String fieldSpces;
    private String entityClass;
    private Date effectTime;
    private String remark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTradeTypeCode() {
        return tradeTypeCode;
    }

    public void setTradeTypeCode(int tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getFieldSpces() {
        return fieldSpces;
    }

    public void setFieldSpces(String fieldSpces) {
        this.fieldSpces = fieldSpces;
        this.fieldDefs = null;
    }

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private FieldDef[] fieldDefs = null;

    public synchronized FieldDef[] parseFieldSpecs() {
        if (fieldDefs == null) {
            if (StringUtils.isEmpty(this.fieldSpces)) {
                fieldDefs = new FieldDef[0];
            } else {
                String[] fields = this.fieldSpces.split(",");

                fieldDefs = new FieldDef[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    String[] fieldSpec = fields[i].split(":");
                    fieldDefs[i] = new FieldDef(/*name*/fieldSpec[0], /*type*/fieldSpec[1]);
                }
            }
        }

        return fieldDefs;
    }

    public boolean validate() {
        for (FieldDef fieldDef : parseFieldSpecs()) {
            if (fieldDef.getType() == null) {
                return false;
            }
        }

        return true;
    }
}
