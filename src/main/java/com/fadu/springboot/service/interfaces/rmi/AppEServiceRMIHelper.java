package com.fadu.springboot.service.interfaces.rmi;

import com.fadu.springboot.model.rmi.app.e.E103Request;
import com.fadu.springboot.model.rmi.app.e.E103Response;
import com.fadu.springboot.util.JSONUtils;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * @Auther: wangchun
 * @Date: 2018/7/27 18:40
 */
@Slf4j
@Component
public class AppEServiceRMIHelper {

    @Autowired
    private AppEServiceRMI serviceRMI;

    public E103Response doService(E103Request request) {
        log.info("发送报文为：->{};",request);
        String retStr = serviceRMI.doService(request);
        if (StringUtils.isEmpty(retStr)) {
            log.warn("返回报文为空，发送报文为->{}", request);
            return null;
        }
        Type type = new TypeToken<E103Response>() {
        }.getType();
        log.info("返回报文为：->{}", retStr);
        E103Response result = JSONUtils.getJsonObject(retStr, type);
        return result;
    }

}
