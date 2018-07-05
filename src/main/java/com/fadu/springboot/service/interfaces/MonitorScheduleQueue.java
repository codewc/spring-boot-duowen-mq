package com.fadu.springboot.service.interfaces;

import com.fadu.springboot.model.MonitorSsdbOrder;

/**
 * 监控短信
 *
 * @Auther: wangchun
 * @Date: 2018/7/5 15:13
 */
public interface MonitorScheduleQueue {
    /**
     * 延迟发送消息到队列
     *
     * @param monitorSsdbOrder 消息内容
     * @param times   延迟时间 单位毫秒
     */
    void monitorSchedule(MonitorSsdbOrder monitorSsdbOrder, long times);
}
