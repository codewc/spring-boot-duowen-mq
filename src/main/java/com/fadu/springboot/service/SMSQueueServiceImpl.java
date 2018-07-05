package com.fadu.springboot.service;

import com.fadu.springboot.config.RabbitMQConstant;
import com.fadu.springboot.model.SMS;
import com.fadu.springboot.service.interfaces.SMSQueueService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 延迟发送短信
 *
 * @Auther: wangchun
 * @Date: 2018/6/29 17:18
 */
@Service
public class SMSQueueServiceImpl implements SMSQueueService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitMQConstant rabbitMQConstant;

    @Override
    public void send(SMS message, long times) {
        MessagePostProcessor postProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(times + "");
                return message;
            }
        };
        rabbitTemplate.convertAndSend(rabbitMQConstant.getSmsDelayedExchangeName(), rabbitMQConstant.getSmsDelayedQueue(), message, postProcessor);
    }
}
