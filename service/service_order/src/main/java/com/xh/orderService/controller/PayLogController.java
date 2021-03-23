package com.xh.orderService.controller;


import com.xh.commentUtils.R;
import com.xh.orderService.service.impl.PayLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author XH
 * @since 2021-02-18
 */
@RestController
@RequestMapping("/orderService/payLog")
@CrossOrigin
public class PayLogController {



    @Autowired
    private PayLogServiceImpl payLogService;

    /**
     * 生成微信支付二维码
     * @param orderNo
     * @return
     */
    @GetMapping("/createPayCode/{orderNo}")
    public R createPayCode(@PathVariable("orderNo")String orderNo){
        Map<String, Object> map = payLogService.createPayCode(orderNo);
        System.out.println("****返回二维码map集合:"+map);
        return R.ok().data(map);
    }

    /**
     * 根据订单号查询订单状态
     * @param orderNo
     * @return
     */
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable("orderNo")String orderNo){
        // 查询订单返回的集合
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("查询订单返回的集合==>"+map);
        // 如果没有返回任何东西,支付失败
        if(map == null) {//支付成功
            return R.error().message("支付出错了");
        }
        //如果返回map里面不为空，通过map获取订单状态
        if(map.get("trade_state").equals("SUCCESS")) {
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

