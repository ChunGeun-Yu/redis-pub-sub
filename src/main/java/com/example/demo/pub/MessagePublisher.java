package com.example.demo.pub;

import com.example.demo.entity.MsgEntity;

public interface MessagePublisher {
    void publish(MsgEntity message);
}
