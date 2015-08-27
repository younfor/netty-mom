package com.alibaba.middleware.race.mom.broker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.middleware.race.mom.bean.RequestSubscribe;

import io.netty.channel.Channel;

public class ConsumerManager {
	private Map<String/*topic*/,ConsumerGroup> topic2consumerGroup=new ConcurrentHashMap<String,ConsumerGroup>();
	/**
	 * 添加新的消费者订阅关系
	 * @param channel
	 * @param rs
	 */
	public void addConsumer(Channel channel,RequestSubscribe rs)
	{
		String topic=rs.getTopic();
		String groupId=rs.getGroupId();
		String filter=rs.getFilter();
		if(!topic2consumerGroup.containsKey(topic))
		{
			ConsumerGroup cg=new ConsumerGroup();
			cg.setTopicId(topic);
			cg.setGroupId(groupId);
			cg.setFilter(filter);
			topic2consumerGroup.put(topic, cg);
		}
		ConsumerGroup consumerGroup=topic2consumerGroup.get(topic);
		//去除过期channel
		consumerGroup.removeDead();
		//添加新的
		consumerGroup.getConsumerSet().add(channel);
	}
}
