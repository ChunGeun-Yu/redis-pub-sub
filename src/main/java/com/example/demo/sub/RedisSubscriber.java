package com.example.demo.sub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MsgEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisSubscriber implements MessageListener {

	StringRedisSerializer serializer = new StringRedisSerializer();
	
	@Autowired
    ObjectMapper objectMapper;	
	
    public void onMessage(Message message, byte[] pattern) {
    	String channel = serializer.deserialize(message.getChannel());
    	
    	try {
    		MsgEntity msgEntity = objectMapper.readValue(message.getBody(), MsgEntity.class);
			log.info("channel: " + channel +  " , msgEntity: " + msgEntity);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	
        
    }
}
