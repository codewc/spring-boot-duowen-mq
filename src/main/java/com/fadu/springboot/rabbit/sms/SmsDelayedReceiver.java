package com.fadu.springboot.rabbit.sms;

import com.fadu.springboot.common.JsonData;
import com.fadu.springboot.model.SMS;
import com.fadu.springboot.service.interfaces.SMSService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 延迟短信接受
 *
 * @Auther: wangchun
 * @Date: 2018/6/29 16:57
 */
@Component
@Slf4j
public class SmsDelayedReceiver {

    @Autowired
    SMSService smsService;

    @RabbitListener(queues = "smsDelayedAfterRepeat", containerFactory = "rabbitListenerContainerFactory")
    public void process(@Payload SMS dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        if (dto == null) {
            channel.basicAck(tag, false);
            return;
        }
        log.info(dto.toString());
        JsonData data = smsService.sendDayuSystemMsg(dto.getPhone(), dto.getTemplateCode(), dto.getTemplateParam());
        log.info(data.toString());
        if (data != null && data.isRet() == true) {
            channel.basicAck(tag, false);// 短信发送成功,确认消息接受成功。
        } else {
            channel.basicAck(tag, false);// 短信发送失败，但是确认消息已经处理，不再处理
        }
    }
}
