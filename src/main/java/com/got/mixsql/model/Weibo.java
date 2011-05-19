package com.got.mixsql.model;

import java.io.Serializable;
import java.util.Date;

public class Weibo implements Serializable {
	
	private static final long serialVersionUID = -1573041353988833546L;

	private long id;
	
	private String from;

	private String content;
	
	private Date pubTime;
	
	private int fwTimes;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public int getFwTimes() {
		return fwTimes;
	}

	public void setFwTimes(int fwTimes) {
		this.fwTimes = fwTimes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
