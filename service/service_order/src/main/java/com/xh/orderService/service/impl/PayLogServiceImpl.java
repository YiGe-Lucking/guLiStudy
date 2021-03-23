package com.xh.orderService.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.xh.orderService.entity.Order;
import com.xh.orderService.entity.PayLog;
import com.xh.orderService.mapper.PayLogMapper;
import com.xh.orderService.service.OrderService;
import com.xh.orderService.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.orderService.utils.HttpClient;
import com.xh.orderService.utils.WXpayParamUtil;
import com.xh.serviceBase.exceptionHander.GuLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author XH
 * @since 2021-02-18
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {


    @Autowired
    private OrderService orderService;

    /**
     * 生成微信支付二维码
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, Object> createPayCode(String orderNo) {

        try {
            //1 根据订单id 获取订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            Order order = orderService.getOne(wrapper);
            //2 使用map设置生成二维码需要参数
            Map m = new HashMap();
            m.put("appid",WXpayParamUtil.APP_ID);
            m.put("mch_id",WXpayParamUtil.PARTNER);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            //课程标题
            m.put("body", order.getCourseTitle());
            //订单号
            m.put("out_trade_no", orderNo);
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url",WXpayParamUtil.NOTIFY_URL);
            m.put("trade_type", "NATIVE");

            //3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式的参数
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(m,WXpayParamUtil.PARTNER_KEY));
            //设置支持https协议
            httpClient.setHttps(true);
            //执行post请求发送
            httpClient.post();
            //4 得到发送请求返回结果
            //返回内容，是使用xml格式返回
            String xml = httpClient.getContent();
            //把xml格式转换map集合，把map集合返回
            Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);
            //最终返回数据 的封装
            Map<String, Object> map = new HashMap();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            //返回二维码操作状态码
            map.put("result_code", resultMap.get("result_code"));
            //二维码地址
            map.put("code_url", resultMap.get("code_url"));

            return map;
        }catch (Exception e){
            throw new GuLiException(20001,"生成二维码失败");
        }
    }

    /**
     * 查询订状态
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {

        try{
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", WXpayParamUtil.APP_ID);
            m.put("mch_id", WXpayParamUtil.PARTNER);
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2 发送httpclient
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,WXpayParamUtil.PARTNER_KEY));
            client.setHttps(true);
            client.post();
            //3 得到请求返回内容
            String xml = client.getContent();
            //6、转成Map再返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            return resultMap;
        }catch (Exception e){
            return null;
        }

    }

    /**
     *  修改支付状态以及生成订单支付记录
     * @param map
     */
    @Override
    public void updateOrdersStatus(Map<String, String> map) {
        //从map获取订单号
        String orderNo = map.get("out_trade_no");
        //根据订单号查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        if (order.getStatus().intValue()==1){
            return;
        }
        // 1代表 支付成功
        order.setStatus(1);
        orderService.updateById(order);
        // 向支付表添加支付记录
        PayLog payLog = new PayLog();
        //订单号
        payLog.setOrderNo(orderNo);
        //订单完成时间
        payLog.setPayTime(new Date());
        //支付类型 1微信
        payLog.setPayType(1);
        //总金额(分)
        payLog.setTotalFee(order.getTotalFee());
        //支付状态
        payLog.setTradeState(map.get("trade_state"));
        //流水号
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));

        baseMapper.insert(payLog);
    }
}
