package com.xh.eduService.feign;

import com.xh.commentUtils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 熔断机制 当远程服务出现宕机或者延时服务  调用此类对应方法
 * @author a3818
 */
@Component
public class HystrixImplFeign implements VodFeignClient {
    @Override
    public R deleteVideoAly(String videoId) {
        return R.error().message("删除单个视频失败");
    }

    @Override
    public R deleteVideosAly(List<String> videoIds) {
        return R.error().message("删除多个视频失败");
    }
}
