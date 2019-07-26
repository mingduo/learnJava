package com.ais.brm.common.domain2;

import java.util.Map;

/**
 * 风险对象信息包装类
 * 
 * @author lulj
 *
 */
public class RiskObject {
	private int objectType;
	private String objectId;
	private String objectName;
	
	private Map<String, Object> props;
	
	public int getObjectType() {
		return objectType;
	}
	public String getObjectId() {
		return objectId;
	}
	public String getObjectName() {
		return objectName;
	}
	public Map<String, Object> getProps() {
		return props;
	}
	
	public RiskObject(int objectType, String objectId, String objectName, Map<String, Object> props)
	{
		this.objectType = objectType;
		this.objectId = objectId;
		this.objectName = objectName;
		this.props = props;
	}
		
	public RiskObject(int objectType, String objectId, String objectName)
	{
		this(objectType, objectId, objectName, null);
	}

	public RiskObject(int objectType, String objectId)
	{
		this(objectType, objectId, null, null);
	}
	
	public static RiskObject fromRiskResult(int objectType, Map<String, Object> riskResult) {
		if (riskResult != null) {
			Object o = riskResult.get("risk_object_id");
			if (o != null) {
				String objectId = o.toString();
				
				o = riskResult.get("risk_object_name");
				String objectName = (o == null ? null : o.toString());
				
				return new RiskObject(objectType, objectId, objectName, riskResult);
			}
		}

		return null;
	}
}
