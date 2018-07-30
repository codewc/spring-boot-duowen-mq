package com.fadu.springboot.service.interfaces.rmi;

import com.fadu.springboot.model.rmi.app.e.E103Request;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * E工程远程调用组件
 *
 * @Auther: wangchun
 * @Date: 2018/7/27 18:25
 */
@FeignClient(name = "duowenapp-e", url = "${duowen.feign.client.app.e.evn}")
public interface AppEServiceRMI {

    /**
     * 诉讼担保订单进度监控短信
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/E/android", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = "text/html;charset=UTF-8")
    String doService(@RequestBody E103Request request);
}
