package com.fadu.springboot.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 律师合作 延迟app消息处理
 *
 * @author wangchun
 * @since 2019/7/19 10:09
 */
public class LawyerCooperationMessageDTO implements Serializable {
    /**
     * 消息类型
     */
    private String type;

    /**
     * 消息元数据
     * 根据type设置不同的自定义参数.
     */
    private Map<String, String> meta = new HashMap<>();

    /**
     * 延迟处理时长:分钟为单位
     */
    private long waitMinutes = 0L;

    public LawyerCooperationMessageDTO(String type, Map<String, String> meta, long waitMinutes) {
        this.type = type;
        this.meta = meta;
        this.waitMinutes = waitMinutes;
    }

    public LawyerCooperationMessageDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }

    public long getWaitMinutes() {
        return waitMinutes;
    }

    public void setWaitMinutes(long waitMinutes) {
        this.waitMinutes = waitMinutes;
    }

    @Override
    public String toString() {
        return "LawyerCooperationMessageDTO{" +
                "type='" + type + '\'' +
                ", meta=" + meta +
                ", waitMinutes=" + waitMinutes +
                '}';
    }
}
