package com.xh.orderService.feign;

import com.xh.commentUtils.orderVo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * 调用 service-ucenter 远程服务
 * @author a3818
 */
@FeignClient("service-ucenter")
@Component
public interface CenterFeignClient {

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("/uService/member/getUserInfo/{userId}")
    UcenterMemberOrder getUserInfo(@PathVariable("userId") String userId);
}
