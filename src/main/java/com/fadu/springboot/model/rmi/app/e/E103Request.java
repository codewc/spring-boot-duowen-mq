package com.fadu.springboot.model.rmi.app.e;

import com.fadu.springboot.model.rmi.BaseRequest;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: wangchun
 * @Date: 2018/7/27 18:27
 */
@Data
@ToString
public class E103Request extends BaseRequest {

    /**
     * 匹配手机号
     */
    private String phone;

}
