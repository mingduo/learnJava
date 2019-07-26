package com.ais.brm.common.domain2;

import java.sql.Date;
/**
 * 
 * @author zhaoyq3
 * 20161110
 *
 */
public class AlarmNotice {
	
	long id ;
	String messageInfo;
	String emailInfo;
	Date  alermDate;
	Date messageDatetime;
	Date emailDatetime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessageInfo() {
		return messageInfo;
	}
	public void setMessageInfo(String messageInfo) {
		this.messageInfo = messageInfo;
	}
	public String getEmailInfo() {
		return emailInfo;
	}
	public void setEmailInfo(String emailInfo) {
		this.emailInfo = emailInfo;
	}
	public Date getAlermDate() {
		return alermDate;
	}
	public void setAlermDate(Date alermDate) {
		this.alermDate = alermDate;
	}
	public Date getMessageDatetime() {
		return messageDatetime;
	}
	public void setMessageDatetime(Date messageDatetime) {
		this.messageDatetime = messageDatetime;
	}
	public Date getEmailDatetime() {
		return emailDatetime;
	}
	public void setEmailDatetime(Date emailDatetime) {
		this.emailDatetime = emailDatetime;
	}
	
	@Override
	public String toString()
	{
		return "id:"+id+",messageInfo:"+messageInfo+",emailInfo:"+emailInfo+
				",alermDate:"+alermDate+",messageDatetime:"+messageDatetime+
				",emailDatetime:"+emailDatetime+",emailDatetime:"+emailDatetime;
	}
}
