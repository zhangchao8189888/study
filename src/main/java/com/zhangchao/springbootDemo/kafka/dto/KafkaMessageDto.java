package com.zhangchao.springbootDemo.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessageDto {
    private String key;
    private String value;
    private Integer partition;
    private String topic;
}
