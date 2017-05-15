package com.wolfesoftware.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LogMessage")
public class LogMessage {
	
	@Id
	@GeneratedValue
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private Date date;
	private String request;
	private String IP;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public LogMessage withDate(Date date) {
		this.date = date;
		return this;
	}

	public LogMessage withRequest(String request) {
		if (request.length() > 200) {
			this.request = request.substring(0, 100);
		} else {
			this.request = request;
		}
		return this;
	}

	public LogMessage withIP(String IP) {
		this.IP = IP;
		return this;
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id:" + id);
		sb.append(" date:" + date);
		sb.append(" request:" + request);
		sb.append(" IP:" + IP);
		return sb.toString();
	}
	
}
