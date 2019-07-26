package com.ais.brm.common.domain2;
/**
 * 星级风险等级-实体类
 * @author wangcb
 *
 */

public class RiskStarLevel  {
    private Integer riskLevel;

    private Integer minStars;
    
    private Integer maxStars;

    private String remarks;

	public Integer getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(Integer riskLevel) {
		this.riskLevel = riskLevel;
	}

	public Integer getMinStars() {
		return minStars;
	}

	public void setMinStars(Integer minStars) {
		this.minStars = minStars;
	}

	public Integer getMaxStars() {
		return maxStars;
	}

	public void setMaxStars(Integer maxStars) {
		this.maxStars = maxStars;
	}

	public String getremarks() {
		return remarks;
	}

	public void setremarks(String remarks) {
		this.remarks = remarks;
	}
    
    
}
