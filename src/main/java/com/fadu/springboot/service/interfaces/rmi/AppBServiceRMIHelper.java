package com.fadu.springboot.service.interfaces.rmi;

import com.fadu.springboot.model.rmi.MT002Request;
import com.fadu.springboot.model.rmi.MT002Response;
import com.fadu.springboot.util.JSONUtils;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

/**
 * B工程报文封装
 *
 * @Auther: wangchun
 * @Date: 2018/7/4 15:55
 */
@Slf4j
public class AppBServiceRMIHelper {

    private AppBServiceRMI serviceRMI;

    public AppBServiceRMIHelper(AppBServiceRMI serviceRMI) {
        this.serviceRMI = serviceRMI;
    }

    public MT002Response doService(MT002Request request) {
        String retStr = serviceRMI.doService(request);
        if (StringUtils.isEmpty(retStr)) {
            log.warn("返回报文为空，发送报文为->{}", request);
            return null;
        }
        Type type = new TypeToken<MT002Response>() {
        }.getType();
        log.info("发送报文为：->{};返回报文为：->{}",request,retStr);
        MT002Response result = JSONUtils.getJosnObject(retStr, type);
        return result;
    }


}
