package com.fadu.springboot.rabbit.sms;

import com.fadu.springboot.model.MonitorSsdbOrder;
import com.fadu.springboot.service.interfaces.MonitorScheduleQueue;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 监控订单
 *
 * @Auther: wangchun
 * @Date: 2018/7/5 15:22
 */
@Component
@Slf4j
public class MonitorScheduleReceiver {

    @Autowired
    MonitorScheduleQueue monitorScheduleQueue;

    @RabbitListener(queues = "monitorScheduleQueue", containerFactory = "rabbitListenerContainerFactory")
    public void process(@Payload MonitorSsdbOrder dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        log.info("参数为:->{}", dto.toString());
        try {
            monitorScheduleQueue.monitorSchedule(dto, dto.getWaitMinutes() * 60 * 1000);
        } catch (Exception e) {
            log.error("发生错误->", e);
        } finally {
            channel.basicAck(tag, false);// 确认处理过，不再重复处理
        }
    }
}
