package com.fadu.springboot.model.rmi;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Auther: wangchun
 * @Date: 2018/7/4 13:46
 */
@Data
@ToString
@EqualsAndHashCode
public class MT002Request extends BaseRequest {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 进度监控点
     */
    private String monitorSchedule;

    /**
     * 进度监控备注详情
     */
    private String monitorRemark;
}
