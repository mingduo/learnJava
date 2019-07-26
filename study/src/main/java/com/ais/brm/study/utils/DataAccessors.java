package com.ais.brm.study.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册全局数据访问器实例，用于非注入代码中对连接对象的访问；比如JdbcTemplate
 * 
 * 通过ID来进行访问，ID通常采用beanName
 * 
 * @author lulj
 *
 */
public class DataAccessors {
	private static Map<String, Object> accessorRegistry = 
			Collections.synchronizedMap(new HashMap<>());
	
	private static Object defaultAccessor;
	
	public static Object register(String id, Object accessor) {
		return accessorRegistry.put(id, accessor);
	}
	
	public static Object registerDefault(String id, Object accessor) {
		defaultAccessor = accessor;
		return accessorRegistry.put(id, accessor);
	}
	
	public static Object get(String id) {
		Object accessor = accessorRegistry.get(id);
		return accessor!=null ? accessor : defaultAccessor;
	}
}
