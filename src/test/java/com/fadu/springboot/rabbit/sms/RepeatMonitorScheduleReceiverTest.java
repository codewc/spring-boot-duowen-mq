package com.fadu.springboot.rabbit.sms;

import com.fadu.springboot.model.rmi.MT002Request;
import com.fadu.springboot.model.rmi.MT002Response;
import com.fadu.springboot.service.interfaces.rmi.AppBServiceRMI;
import com.fadu.springboot.service.interfaces.rmi.AppBServiceRMIHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 监控发送短信测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepeatMonitorScheduleReceiverTest {

    @Autowired
    private AppBServiceRMIHelper serviceRMIHelper;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void process() {
        MT002Request request = buildMT002Request_Monitor_Schedule_3();
        MT002Response response = serviceRMIHelper.doService(request);
        System.out.println(response);
    }

    private MT002Request buildMT002Request_Monitor_Schedule_0() {
        MT002Request request = buildCommonParamter();
        request.setMonitorSchedule("0");
        request.setMonitorRemark("测试类：测试订单");
        return request;
    }

    private MT002Request buildMT002Request_Monitor_Schedule_1() {
        // update ssdb_order set orderState=30 where orderId='160607010015';
        MT002Request request = buildCommonParamter();
        request.setMonitorSchedule("1");
        request.setMonitorRemark("测试类：测试订单");
        return request;
    }

    private MT002Request buildMT002Request_Monitor_Schedule_2() {
        // update ssdb_order set orderState=21 where orderId='160607010015';
        MT002Request request = buildCommonParamter();
        request.setMonitorSchedule("2");
        request.setMonitorRemark("测试类：测试订单");
        return request;
    }

    private MT002Request buildMT002Request_Monitor_Schedule_3() {
        // update ssdb_order set orderState=30 where orderId='160607010015';
        MT002Request request = buildCommonParamter();
        request.setMonitorSchedule("3");
        request.setMonitorRemark("测试类：测试订单");
        return request;
    }

    private MT002Request buildCommonParamter() {
        MT002Request request = new MT002Request();
        request.setToken("8af40d1763952f960163a54b4f8717e1");//设置请求token.
        request.setActionCode("MT002");
        request.setOrderId("160607010015");
        request.setVersion(100);
        request.setTime(System.currentTimeMillis());
        return request;
    }
}