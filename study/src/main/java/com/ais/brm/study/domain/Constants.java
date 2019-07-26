package com.ais.brm.study.domain;

/**
 * 规则引擎模块常量定义
 * 
 * @author weizc
 *
 */
public class Constants {
	//作为一个独立的processor，消费TOPIC_INDEX_RESULT主题
	public static final String TOPIC_INDEX_RESULT="TOPIC_INDEX_RESULT";

	//redis key 值 指标值
	public static final String RISK_INDEX="RISK_INDEX";
	//redis key 值 多指标规则
	public static final String RISK_INDEX_MULTI_RULE="RISK_INDEX_MULTI_RULE";

	//单指标规则引擎变更，发消息
	public static final String TOPIC_INDEX_RULE_CHANGED="TOPIC_INDEX_RULE_CHANGED";

	//多指标规则引擎负责监听，根据收到的指标值列表和对应的规则ID进行指标规则评估
	public static final String TOPIC_MULTIINDEX_COLLECT_RESULT="TOPIC_MULTIINDEX_COLLECT_RESULT";

	//按照配置按日或者按小时调度触发的时候，发消息
	public static final String TOPIC_MULTIINDEX_ANALYZE="TOPIC_MULTIINDEX_ANALYZE";

	//多指标规则引擎变更，发消息
	public static final String TOPIC_INDEX_MULTI_RULE_CHANGED="TOPIC_INDEX_MULTI_RULE_CHANGED";
	
	// 取全部
	public static final int MULTI_INDEX_VALUE_GET_TYPE_WHOLE = 99;
	
	// 取最新
	public static final int MULTI_INDEX_VALUE_GET_TYPE_LATEST = 11;
	
	// 取最早
	public static final int MULTI_INDEX_VALUE_GET_TYPE_EARLIEST = 12;
	
	// 取最大
	public static final int MULTI_INDEX_VALUE_GET_TYPE_MAX = 13;
	
	// 取最小
	public static final int MULTI_INDEX_VALUE_GET_TYPE_MIN = 14;
	
	// 取均值
	public static final int MULTI_INDEX_VALUE_GET_TYPE_MEAN = 15;
	
	// 取中值
	public static final int MULTI_INDEX_VALUE_GET_TYPE_MEDIAN = 16;
	
	// 指标特定
	public static final int MULTI_INDEX_VALUE_GET_TYPE_BYINDEX = 17;
	
	// 采集调度方式：按天
	public static final int RUN_TYPE_DAY = 10;
	
	// 采集调度方式：按小时
	public static final int RUN_TYPE_HOUR = 11;
	
	// RedisKey：多指标配置数据变更检查
	public static final String CHANGE_CHECK_SHA256_OF_MULTI_INDEX_RULE =
			"CHANGE_CHECK_SHA256_OF_MULTI_INDEX_RULE";
}
