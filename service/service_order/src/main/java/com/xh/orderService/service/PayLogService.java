package com.xh.orderService.service;

import com.xh.orderService.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author XH
 * @since 2021-02-18
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 生成微信支付二维码
     * @param orderNo
     * @return
     */
    Map<String, Object> createPayCode(String orderNo);

    /**
     * 查询订状态
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     *  修改支付状态以及生成订单支付记录
     * @param map
     */
    void updateOrdersStatus(Map<String, String> map);
}
