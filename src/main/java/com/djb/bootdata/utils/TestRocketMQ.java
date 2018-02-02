package com.djb.bootdata.utils;

import com.alibaba.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestRocketMQ {
    @Value("${rocketmq.producer.topic}")
    private String PRODUCER_TOPIC;

    @Value("${rocketmq.producer.tag}")
    private String tag;

    @Value("${rocketmq.producer.key}")
    private String key;

    @Bean("testMessage")
    public Message getTestMessage() {
        Message message = new Message();
        message.setTopic(PRODUCER_TOPIC);
        message.setTags(tag);
        message.setKeys(key);
        return message;
    }
}
