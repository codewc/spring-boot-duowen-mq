package com.fadu.springboot.service.interfaces.rmi;

import com.fadu.springboot.model.rmi.MT002Request;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 多问B工程远程调用
 * <p>
 * produces = "text/html;charset=UTF-8",兼容b应用返回报文格式，json解析提示报错.使用{@link AppBServiceRMIHelper}进行str转json的格式
 *
 * @Auther: wangchun
 * @Date: 2018/7/4 11:55
 */
@FeignClient(name = "duowenapp-b", url = "${duowen.feign.client.url.evn}")
public interface AppBServiceRMI {

    /**
     * 诉讼担保订单进度监控短信
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/B/android", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = "text/html;charset=UTF-8")
    String doService(@RequestBody MT002Request request);

}
