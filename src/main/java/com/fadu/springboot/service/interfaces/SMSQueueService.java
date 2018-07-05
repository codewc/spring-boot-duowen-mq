package com.fadu.springboot.service.interfaces;

import com.fadu.springboot.model.SMS;

/**
 * 短信发送业务组件
 *
 * @Auther: wangchun
 * @Date: 2018/6/29 17:16
 */
public interface SMSQueueService {
    /**
     * 延迟发送消息到队列
     *
     * @param message 消息内容
     * @param times   延迟时间 单位毫秒
     */
    void send(SMS message, long times);
}
