package com.example.demo.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MsgEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisPublisher implements MessagePublisher {
	
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
    private ChannelTopic topic;
    
    @Autowired
    ObjectMapper objectMapper;
   
    @Override
    public void publish(MsgEntity message) {
    	log.info("message: " + message);    	
    	try {
			String messageStr = objectMapper.writeValueAsString(message);
			redisTemplate.convertAndSend(topic.getTopic(), messageStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    }
}
