package com.ais.brm.common.domain2;

/**
 * 台账明细扩展信息中，用于表示记录中的一个字段实例，包括：字段类型，字段值
 *
 * @author lulj
 * @since 2016/10/10
 */
public class FieldInstance {
    private FieldType fieldType;
    private Object value;

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
