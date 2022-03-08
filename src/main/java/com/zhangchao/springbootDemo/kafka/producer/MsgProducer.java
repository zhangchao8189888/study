package com.zhangchao.springbootDemo.kafka.producer;

import com.zhangchao.springbootDemo.kafka.dto.KafkaMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class MsgProducer {
    @Resource
    KafkaTemplate kafkaTemplate;
    public boolean pushMsg (KafkaMessageDto kafkaMessageDto) {
        log.info("kafkaMessageDto:{}",kafkaMessageDto);
        kafkaTemplate.send(kafkaMessageDto.getTopic(), kafkaMessageDto.getKey(), kafkaMessageDto.getValue());
        return true;
    }
}
