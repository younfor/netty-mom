package com.alibaba.middleware.race.mom.broker;

import java.util.HashSet;

import io.netty.channel.Channel;

public class ConsumerGroup {
	private String groupId;
	private String topicId;
	private String filter;
	private HashSet<Channel> consumerSet=new HashSet<Channel>();
	public void removeDead()
	{
		
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public HashSet<Channel> getConsumerSet() {
		return consumerSet;
	}
	public void setConsumerSet(HashSet<Channel> consumerSet) {
		this.consumerSet = consumerSet;
	}
	
}
