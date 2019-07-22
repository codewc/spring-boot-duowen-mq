package com.fadu.springboot.model.rmi.app.b;

import com.fadu.springboot.model.LawyerCooperationMessageDTO;
import com.fadu.springboot.model.rmi.BaseRequest;

/**
 * @author wangchun
 * @since 2019/7/22 17:21
 */
public class MT006Request extends BaseRequest {

    private LawyerCooperationMessageDTO dto;

    public LawyerCooperationMessageDTO getDto() {
        return dto;
    }

    public void setDto(LawyerCooperationMessageDTO dto) {
        this.dto = dto;
    }

    @Override
    public String toString() {
        return "MT006Request{" +
                "dto=" + dto +
                '}' + super.toString();
    }
}

