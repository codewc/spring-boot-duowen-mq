package com.fadu.springboot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * rabbitMQ定义常量，从application.yml文件读取
 *
 * @Auther: wangchun
 * @Date: 2018/6/29 15:32
 */
@ConfigurationProperties
@Component
public class RabbitMQConstant {

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.exchanges.smsExchangeName}")
    private String smsExchangeName;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.exchanges.smDelayedExchangeName}")
    private String smsDelayedExchangeName;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.queues.immediate}")
    private String smsImmediateQueue;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.queues.delayed}")
    private String smsDelayedQueue;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.queues.smsDelayedAfterRepeat}")
    private String smsDelayedAfterRepeatQueue;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.routingKey.smsRootingKey}")
    private String smsRootingKey;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.routingKey.smDelayedKey}")
    private String smDelayedKey;

}
