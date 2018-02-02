package com.djb.bootdata.utils;


import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RocketMQConfig {

    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.producer.instanceName}")
    private String instanceName;

    @Value("${rocketmq.producer.maxMessageSize}")
    private int maxMessageSize;

    @Value("${rocketmq.producer.sendMsgTimeout}")
    private int sendMsgTimeout;
    @Bean
    public DefaultMQProducer getRocketMQProducer()  {
        DefaultMQProducer producer = new DefaultMQProducer(this.groupName);
        producer.setNamesrvAddr(this.namesrvAddr);
        producer.setInstanceName(instanceName);
        producer.setMaxMessageSize(this.maxMessageSize);
        producer.setSendMsgTimeout(this.sendMsgTimeout);
        try {
            producer.start();
            System.out.println((String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]", this.groupName, this.namesrvAddr)));
        } catch (MQClientException e) {
            System.out.println((String.format("producer is error {}", e.getMessage(), e)));
        }
        return producer;
    }

    public Map<String,String> send(Message message){
        Map<String,String> result=new HashMap<>();
        try {
            //发送mq
            getRocketMQProducer().send(message);
            result.put("msg","success");
        } catch (Exception e) {
            result.put("msg",e.getMessage());
        }
        return result;
    }

}
