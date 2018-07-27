package com.fadu.springboot.rabbit.sms;

import com.fadu.springboot.model.MonitorSsdbOrder;
import com.fadu.springboot.model.rmi.MT002Request;
import com.fadu.springboot.model.rmi.MT002Response;
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
 * 延迟监控中心
 *
 * @Auther: wangchun
 * @Date: 2018/7/5 15:00
 */
@Component
@Slf4j
public class RepeatMonitorScheduleReceiver {

    @Autowired
    private AppBServiceRMIHelper serviceRMIHelper;

    @RabbitListener(queues = "repeatMonitorScheduleQueue", containerFactory = "rabbitListenerContainerFactory")
    public void process(@Payload MonitorSsdbOrder monitorSsdbOrder, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        if (monitorSsdbOrder == null) {
            channel.basicAck(tag, false);
            return;
        }
        log.info(monitorSsdbOrder.toString());
        try {
            MT002Request request = new MT002Request();
            request.setMonitorSchedule(monitorSsdbOrder.getMonitorSchedule());
            request.setMonitorRemark(monitorSsdbOrder.getMonitorRemark());
            request.setOrderId(monitorSsdbOrder.getOrderId());
            request.setWaitMinutes(monitorSsdbOrder.getWaitMinutes());
            request.setToken("8af40d1763952f960163a54b4f8717e1");//设置请求token.
            request.setActionCode("MT002");
            request.setVersion(100);
            request.setTime(System.currentTimeMillis());
            MT002Response response = serviceRMIHelper.doService(request);
        } catch (Exception e) {
            log.error("发生了错误->", e);
        } finally {
            channel.basicAck(tag, false);// 处理成功
        }
    }
}
