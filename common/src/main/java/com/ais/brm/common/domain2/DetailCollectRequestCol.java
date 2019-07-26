package com.ais.brm.common.domain2;

import com.ais.brm.web.domain.BaseDomain;

/**
 * 明细采集请求SQL查询字段
 * @author wangyong10
 *
 */
public class DetailCollectRequestCol extends BaseDomain{
	private Integer id;
	
	private String englishName;
	
	private String chineseName;
	
	private Integer columnOrder;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public Integer getColumnOrder() {
		return columnOrder;
	}

	public void setColumnOrder(Integer columnOrder) {
		this.columnOrder = columnOrder;
	}

	@Override
	public String toString() {
		return "DetailCollectRequestCol [id=" + id + ", "
				+ "englishName=" + englishName + ", chineseName=" + chineseName
				+ ", columnOrder=" + columnOrder + "]";
	}
}