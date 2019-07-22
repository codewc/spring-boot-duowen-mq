package com.fadu.springboot.service;

import com.fadu.springboot.config.RabbitMQConstant;
import com.fadu.springboot.model.LawyerCooperationMessageDTO;
import com.fadu.springboot.service.interfaces.LawyerCooperationMessageService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangchun
 * @since 2019/7/22 16:07
 */
@Service
public class LawyerCooperationMessageServiceImpl implements LawyerCooperationMessageService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQConstant rabbitMQConstant;

    @Override
    public void convertAndSend(LawyerCooperationMessageDTO dto) {
        MessagePostProcessor postProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(String.valueOf(dto.getWaitMinutes() * 60 * 1000));
                return message;
            }
        };
        rabbitTemplate.convertAndSend(
                rabbitMQConstant.getDelayedLawyerCooperationMessageExchangeName(),
                rabbitMQConstant.getDelayedAppMessageQueue(),
                dto,
                postProcessor);
    }
}
