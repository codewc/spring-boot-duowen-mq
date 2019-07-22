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
    // -- exchange定义 开始 --
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
    @Value("${duowen.rabbitmq.exchanges.lawyerCooperationMessageExchangeName}")
    private String lawyerCooperationMessageExchangeName;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.exchanges.delayedLawyerCooperationMessageExchangeName}")
    private String delayedLawyerCooperationMessageExchangeName;
    // -- exchange定义 结束 --

    // -- queue定义 开始 --
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
    @Value("${duowen.rabbitmq.queues.appMessage}")
    private String appMessageQueue;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.queues.delayedAppMessage}")
    private String delayedAppMessageQueue;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.queues.delayedAppMessageRepeat}")
    private String delayedAppMessageRepeatQueue;
    // -- queue定义 结束 --

    // -- key定义 开始 --
    @Getter
    @Setter
    @Value("${duowen.rabbitmq.routingKey.smsRootingKey}")
    private String smsRootingKey;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.routingKey.smDelayedKey}")
    private String smDelayedKey;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.routingKey.appMessageKey}")
    private String appMessageKey;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.routingKey.delayedAppMessageKey}")
    private String delayedAppMessageKey;

    @Getter
    @Setter
    @Value("${duowen.rabbitmq.routingKey.delayedAppMessageRepeatKey}")
    private String delayedAppMessageRepeatKey;
    // -- key定义 结束 --

}
