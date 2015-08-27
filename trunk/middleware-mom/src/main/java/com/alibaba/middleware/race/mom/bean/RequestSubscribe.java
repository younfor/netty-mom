package com.alibaba.middleware.race.mom.bean;

import java.io.Serializable;

public class RequestSubscribe implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2332635735690246184L;
	public RequestSubscribe(){}
	private String groupId;
	private String topic;
	private String filter;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public RequestSubscribe(String groupId, String topic, String filter) {
		super();
		this.groupId = groupId;
		this.topic = topic;
		this.filter = filter;
	}
	
}
