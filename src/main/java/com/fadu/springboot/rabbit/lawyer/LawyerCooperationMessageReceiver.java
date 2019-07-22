package com.fadu.springboot.rabbit.lawyer;

import com.fadu.springboot.model.LawyerCooperationMessageDTO;
import com.fadu.springboot.service.interfaces.LawyerCooperationMessageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 律师合作消息
 *
 * @author wangchun
 * @since 2019/7/22 14:58
 */
@Component
@Slf4j
public class LawyerCooperationMessageReceiver {

    @Autowired
    private LawyerCooperationMessageService lawyerCooperationMessageService;

    @RabbitListener(queues = "appMessage", containerFactory = "rabbitListenerContainerFactory")
    public void process(@Payload LawyerCooperationMessageDTO dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        log.info("律师合作消息->{}", dto);
        try {
            if (dto.getWaitMinutes() > 0) {// 需要延时发送处理
                log.info("延时发送：dto：{} ->等待时长为{}分钟", dto, dto.getWaitMinutes());
                lawyerCooperationMessageService.convertAndSend(dto);
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            channel.basicAck(tag, false);// 短信发送失败，但是确认消息已经处理，不再处理
        }

    }

}

