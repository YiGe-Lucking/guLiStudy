package com.xh.eduService.feign;

import com.xh.commentUtils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * FeignClient 远程服务客户端 name 指定远程服务名
 * 服务名对应 服务器ip端口号
 * 调用远程方法 请求路径=服务器ip端口号+xxxMapping值
 *
 * fallback 添加熔断实现类
 * @author a3818
 */
@FeignClient(name = "service-vod",fallback = HystrixImplFeign.class)
@Component
public interface VodFeignClient {

    /**
     *  调用远程vod服务根据视频id删除单个视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/vodService/vod/deleteVideoAly/{videoId}")
    R deleteVideoAly(@PathVariable("videoId") String videoId);

    /**
     * 调用远程vod服务根据多个视频id删除多个视频
     * 需要指定@RequestParam("videos") 不然报错
     * @param videoIds
     * @return
     */
    @DeleteMapping("/vodService/vod/deleteVideosAly")
    R deleteVideosAly(@RequestParam("videos") List<String> videoIds);





}
