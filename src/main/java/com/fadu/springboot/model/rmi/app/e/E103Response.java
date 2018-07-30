package com.fadu.springboot.model.rmi.app.e;

import lombok.Data;
import lombok.ToString;

/**
 *
 * @Auther: wangchun
 * @Date: 2018/7/27 18:29
 */
@Data
@ToString
public class E103Response {

    private boolean success = false;// 是否成功

    private String message = ""; // 返回信息
}
