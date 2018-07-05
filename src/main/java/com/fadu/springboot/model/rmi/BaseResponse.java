package com.fadu.springboot.model.rmi;

import lombok.*;

import java.io.Serializable;

/**
 * @Auther: wangchun
 * @Date: 2018/7/4 13:59
 */
@Data
@ToString
public class BaseResponse implements Serializable {
    /**
     * 是否成功
     */
    private boolean success = false;
    /**
     * 返回信息
     */
    private String message;

}
