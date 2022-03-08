package com.zhangchao.springbootDemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhangchao.springbootDemo.kafka.dto.KafkaMessageDto;
import com.zhangchao.springbootDemo.kafka.producer.MsgProducer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class IndexController {
    @Resource
    MsgProducer msgProducer;
    @PostMapping("/index")
    public String index (@RequestBody JSONObject jsonObject) {
        String topic = jsonObject.getString("topic");
        String key = jsonObject.getString("key");
        String value = jsonObject.getString("value");
        KafkaMessageDto messageDto = KafkaMessageDto.builder().topic(topic).key(key).value(value).build();
        msgProducer.pushMsg(messageDto);
        return null;
    }
    @PostMapping("/test")
    public String test (@RequestBody JSONObject jsonObject) {

        return "OK";
    }

}
