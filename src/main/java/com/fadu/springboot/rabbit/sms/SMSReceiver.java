package com.fadu.springboot.rabbit.sms;

import com.fadu.springboot.common.JsonData;
import com.fadu.springboot.config.RabbitMQConstant;
import com.fadu.springboot.model.SMS;
import com.fadu.springboot.service.interfaces.SMSQueueService;
import com.fadu.springboot.service.interfaces.SMSService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 短信消费者
 *
 * @Auther: wangchun
 * @Date: 2018/6/27 10:56
 */
@Component
@RabbitListener(queues = "immediate", containerFactory = "rabbitListenerContainerFactory")
@Slf4j
public class SMSReceiver {

    @Autowired
    private SMSService smsService;

    @Autowired
    SMSQueueService smsQueueService;

    @Autowired
    RabbitMQConstant rabbitMQConstant;

    @RabbitHandler
    public void process(@Payload SMS dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        if (dto == null) {
            channel.basicAck(tag, false);
            return;
        }
        log.info(dto.toString());
        if (dto.getWaitMinutes() > 0) {// 需要延时发送处理
            log.info("延时发送短信：发送手机号为：{} ->等待时长为{}", dto.getPhone(), dto.getWaitMinutes());
            smsQueueService.send(dto, dto.getWaitMinutes() * 60 * 1000);
            channel.basicAck(tag, false);// 消费成功
        } else {
            JsonData data = smsService.sendDayuSystemMsg(dto.getPhone(), dto.getTemplateCode(), dto.getTemplateParam());
            log.info(data.toString());
            if (data != null && data.isRet() == true) {
                channel.basicAck(tag, false);// 短信发送成功,确认消息接受成功。
            } else {
                channel.basicAck(tag, false);// 短信发送失败，但是确认消息已经处理，不再处理
            }
        }


    }
}