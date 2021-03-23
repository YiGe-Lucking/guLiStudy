package com.xh.eduService.feign;

import com.xh.commentUtils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
@FeignClient(name = "service-ucenter")
@Component
public interface UcenterFeignClient {

    /**
     * 解析token 获取用户信息
     * @param request
     * @return
     */
    @GetMapping("/uService/member/getUserInfo")
    R getUserInfo(@RequestParam("request") HttpServletRequest request);
}
