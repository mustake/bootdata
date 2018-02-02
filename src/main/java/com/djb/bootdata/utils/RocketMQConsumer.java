package com.djb.bootdata.utils;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;


public class RocketMQConsumer {

    static String topic="rocketTopic";
    static String tag="testMq";
    static String namesrvAddr="192.168.0.232:9876";

    public static void consumer(RocketMQListener rocketMQListener) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer1");
        //同样也要设置NameServer地址
        consumer.setNamesrvAddr(namesrvAddr);

        //这里设置的是一个consumer的消费策略
        //CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，即跳过历史消息
        //CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
        //CONSUME_FROM_TIMESTAMP 从某个时间点开始消费，和setConsumeTimestamp()配合使用，默认是半个小时以前
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            //设置consumer所订阅的Topic和Tag，*代表全部的Tag
            consumer.subscribe(topic,tag );
            //设置一个Listener，主要进行消息的逻辑处理
            consumer.registerMessageListener(rocketMQListener);

            //调用start()方法启动consumer
            consumer.start();
            System.out.println("Consumer Started.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {


        RocketMQListener mqListener = new RocketMQListener();
        RocketMQConsumer mqConsumer = new RocketMQConsumer();
        System.out.println("开始消费---------------");
        mqConsumer.consumer(mqListener);
        try {
            System.out.println("等待");
            Thread.sleep(1000 * 60L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
