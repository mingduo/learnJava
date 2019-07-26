package com.ais.brm.study.brmTest.hadoop.hdfs.datacollect;

import java.util.Properties;

/**
 * 单例模式：懒汉式——考虑了线程安全
 * @author ThinkPad
 *
 */

public class PropertyHolderLazy {

	private static Properties prop = null;

	public static Properties getProps() throws Exception {
		if (prop == null) {
			synchronized (PropertyHolderLazy.class) {
				if (prop == null) {
					prop = new Properties();
					prop.load(PropertyHolderLazy.class.getClassLoader().getResourceAsStream("collect.properties"));
				}
			}
		}
		return prop;
	}

}
