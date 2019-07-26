package com.ais.brm.web.domain;

import java.util.Date;
import java.util.HashMap;

/**
 * 基类
 * @author luwz
 *
 */
public class BaseDomain {
	private Integer begin;
	private Integer size;

	private String sortName;

	private String sortType = "ASC";

	private Long total;
	/**
	 * 用于对时间范围查找用
	 */
	private Date beginTime;
	/**
	 * 用于对时间范围查找用
	 */
	private Date endTime;
	/**
	 * 用于存放拓展属性
	 * 
	 */
	private HashMap<String, Object> m_extAttr;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	/**
	 * 
	 * <p>
	 * 〈功能详细描述〉
	 * 
	 * @author weizc 2017年9月11日
	 */
	public Object getExtAttr(String pKey) {
		if (m_extAttr == null) {
            return null;
        } else {
            return m_extAttr.get(pKey);
        }
	}

	/**
	 * 
	 * <p>
	 * 存放拓展属性
	 * 
	 * @author weizc 2017年9月11日
	 */
	public void setExtAttr(String pKey, Object pValue) {
		if (m_extAttr == null) {
            m_extAttr = new HashMap();
        }
		m_extAttr.put(pKey, pValue);
	}

}
