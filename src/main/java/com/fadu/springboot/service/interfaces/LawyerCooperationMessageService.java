package com.fadu.springboot.service.interfaces;


import com.fadu.springboot.model.LawyerCooperationMessageDTO;

/**
 * 律师合作消息
 *
 * @author wangchun
 * @since 2019/7/22 16:05
 */
public interface LawyerCooperationMessageService {

    /**
     * 延迟发送消息到队列
     *
     * @param dto 传输数据
     */
    void convertAndSend(LawyerCooperationMessageDTO dto);
}
