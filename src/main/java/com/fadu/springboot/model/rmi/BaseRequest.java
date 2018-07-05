package com.fadu.springboot.model.rmi;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Auther: wangchun
 * @Date: 2018/7/4 13:57
 */
@Data
@ToString
public class BaseRequest implements Serializable {
    /**接口编码*/
    private String actionCode = "";

    /**id， 对应旧版本数据库的token字段*/
    private String token = "";

    /**md5签名串*/
    private String encryptCode = "";

    /**请求时间*/
    private long time;

    /**版本号*/
    private Integer version;
}
