package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MsgEntity;
import com.example.demo.pub.RedisPublisher;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TestController {
	
	@Autowired
	RedisPublisher redisPublisher;
	
	@PostMapping("/publish")
	public MsgEntity publish(@RequestBody MsgEntity msg) {
		log.info("msg: " + msg);
		
		redisPublisher.publish(msg);
		return msg;
	}	
}
