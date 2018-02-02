package com.djb.bootdata.controller;

import com.alibaba.rocketmq.common.message.Message;
import com.djb.bootdata.utils.RocketMQConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rocket")
public class RocketController {

    @Autowired
    private RocketMQConfig rocketMQConfig;
    @Autowired
    private Message testMessage;

    @GetMapping("/send")
    public String rocketSend(){
        for (int i=0;i<10;i++){
            String string="发送消息----"+i;
            testMessage.setBody(string.getBytes());
            rocketMQConfig.send(testMessage);

        }
        System.out.println("发送消息");
        return  "发送成功";
    }

    @GetMapping("/index")
    public String index(){
        return  "index";
    }
}
