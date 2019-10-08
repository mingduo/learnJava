package com.ais.brm.common.domain2;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaocw
 */
public enum FieldType {
    CHAR("char"),
    STRING("string"),
    BOOLEAN("boolean"),
    NUMERIC("numeric"),
    BYTE("byte"),
    SHORT("short"),
    INT("int"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double"),
    BYTES("bytes"),
    DATE("date"),
    TIME("time"),
    TIMESTAMP("timestamp");

    private String name;

    public String getName() {
        return name;
    }

    FieldType(String name) {
        this.name = name;
    }

    private static Map<String, FieldType> NameMap = new HashMap<>();

    static {
        for (FieldType f : FieldType.values()) {
            NameMap.put(f.getName(), f);
        }
    }

    public static FieldType fieldType(String name) {
        return NameMap.get(name);
    }
}
