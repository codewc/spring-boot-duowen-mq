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
        MT002Request request = new MT002Request();
        serviceRMIHelper.doService(request);
    }
}