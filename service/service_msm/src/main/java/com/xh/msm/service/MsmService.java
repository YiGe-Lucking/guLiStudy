package com.xh.msm.service;

import java.util.Map;

public interface MsmService {
    /**
     * 短信发送
     * @param param
     * @param phone
     * @return
     */
    boolean send(Map<String, Object> param, String phone);

}
