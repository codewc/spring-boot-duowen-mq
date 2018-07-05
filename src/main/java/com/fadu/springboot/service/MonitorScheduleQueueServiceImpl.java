package com.fadu.springboot.service;

import com.fadu.springboot.model.MonitorSsdbOrder;
import com.fadu.springboot.service.interfaces.MonitorScheduleQueue;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: wangchun
 * @Date: 2018/7/5 15:15
 */
@Service
public class MonitorScheduleQueueServiceImpl implements MonitorScheduleQueue {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void monitorSchedule(MonitorSsdbOrder monitorSsdbOrder, long times) {
        MessagePostProcessor postProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(times + "");
                return message;
            }
        };
        rabbitTemplate.convertAndSend("monitorDelayedScheduleExchange", "deadMonitorScheduleQueue", monitorSsdbOrder, postProcessor);
    }
}
