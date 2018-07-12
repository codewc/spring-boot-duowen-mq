package com.fadu.springboot.rmi;

import com.fadu.springboot.model.rmi.MT002Request;
import com.fadu.springboot.model.rmi.MT002Response;
import com.fadu.springboot.rabbit.hello.HelloSender;
import com.fadu.springboot.service.interfaces.rmi.AppBServiceRMI;
import com.fadu.springboot.service.interfaces.rmi.AppBServiceRMIHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AppBServiceRMITest {

	@Autowired
	private AppBServiceRMIHelper serviceRMIHelper;

	@Test
	public void doMT002Service() throws Exception {
        MT002Request request = new MT002Request();
        //{"actionCode":"B520","encryptCode":"","time":0,"token":"8af40d1761dc13fa0161dc17d1300004","version":560}
        request.setActionCode("MT002");
        request.setMonitorRemark("监控短信");
        request.setMonitorSchedule("SSDB_MONITOR_0");
        request.setOrderId("180705011153");
        request.setToken("8af40d1761dc13fa0161dc17d1300004");
        request.setVersion(560);
        MT002Response response = serviceRMIHelper.doService(request);
	}

    @Test
    public void doMT002Service_SSDB_MONITOR_1() throws Exception {
        MT002Request request = new MT002Request();
        //{"actionCode":"B520","encryptCode":"","time":0,"token":"8af40d1761dc13fa0161dc17d1300004","version":560}
        request.setActionCode("MT002");
        request.setMonitorRemark("监控短信");
        request.setMonitorSchedule("1");
        request.setOrderId("180705011153");
        request.setToken("8af40d1761dc13fa0161dc17d1300004");
        request.setVersion(560);
        MT002Response response = serviceRMIHelper.doService(request);
    }

}