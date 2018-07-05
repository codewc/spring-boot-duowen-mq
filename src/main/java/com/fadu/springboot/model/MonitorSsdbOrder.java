package com.fadu.springboot.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 监控SsdbOrder订单情况
 *
 * @Auther: wangchun
 * @Date: 2018/7/5 11:50
 */
@Data
@ToString
public class MonitorSsdbOrder implements Serializable {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * {@link #monitorSchedule}
     * 付款后，在出函时长后未出函，发送短信给多问客服以及保险公司。注意非工作日以及非工作时不提醒。工作时为早上9点-下午16点。
     */
    public static final String MONITOR_SCHEDULE_1 ="1";

    /**
     * 进度监控点
     */
    private String monitorSchedule;

    /**
     * 进度监控备注详情
     */
    private String monitorRemark;

    /**
     * 延迟监控时长:分钟为单位
     */
    private long waitMinutes;
}
