package com.fadu.springboot.rabbit.lawyer;

import com.fadu.springboot.model.LawyerCooperationMessageDTO;
import com.fadu.springboot.model.rmi.app.b.MT006Request;
import com.fadu.springboot.service.interfaces.rmi.AppBServiceRMIHelper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 律师合作 延时处理
 *
 * @author wangchun
 * @since 2019/7/22 17:10
 */
@Component
@Slf4j
public class DelayedAppMessageRepeatReceiver {

    @Autowired
    private AppBServiceRMIHelper serviceRMIHelper;

    @RabbitListener(queues = "delayedAppMessageRepeat", containerFactory = "rabbitListenerContainerFactory")
    public void process(@Payload LawyerCooperationMessageDTO dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        log.info("律师合作消息->{}", dto);
        if (dto == null) {
            channel.basicAck(tag, false);
            return;
        }
        //     private LawyerCooperationMessageDTO dto;
        MT006Request request = new MT006Request();
        request.setActionCode("MT006");
        request.setDto(dto);
        try {
            serviceRMIHelper.doService(request);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            channel.basicAck(tag, false);// 确认消息已经处理，不再处理
        }
    }

}
