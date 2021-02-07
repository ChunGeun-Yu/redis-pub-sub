package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.demo.pub.RedisPublisher;
import com.example.demo.sub.RedisSubscriber;

@Configuration
public class RedisConfig {

	@Autowired
	RedisSubscriber redisSubscriber;
	
	@Autowired
	RedisPublisher redisPublisher;
	
	@Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        return lettuceConnectionFactory;
    }
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
	    RedisTemplate<String, Object> template = new RedisTemplate<>();
	    template.setConnectionFactory(connectionFactory);	    
	    
	    // string type 저장시 아래 설정 적용됨.
	    template.setKeySerializer(new StringRedisSerializer());
	    template.setValueSerializer(new StringRedisSerializer());
	    
	    // repository 에서 저장시 hash type으로 저장되므로, 아래 설정 적용됨.
	    template.setHashKeySerializer(new StringRedisSerializer());
	    template.setHashValueSerializer(new StringRedisSerializer());
	    return template;
	}
		
	@Bean
	MessageListenerAdapter messageListener() { 
	    return new MessageListenerAdapter(redisSubscriber);
	}
	
	@Bean
	RedisMessageListenerContainer redisContainer() {
	    RedisMessageListenerContainer container = new RedisMessageListenerContainer(); 
	    container.setConnectionFactory(redisConnectionFactory()); 
	    // 여러개의 listener 등록 가능
	    container.addMessageListener(messageListener(), patternTopic()); 
	    return container; 
	}
	
	// sub 용 topic
	@Bean
	PatternTopic patternTopic() {
	    return new PatternTopic("message.*");
	}
	
	// pub 용 topic
	@Bean
	ChannelTopic channelTopic() {
	    return new ChannelTopic("message.1");
	}
}
