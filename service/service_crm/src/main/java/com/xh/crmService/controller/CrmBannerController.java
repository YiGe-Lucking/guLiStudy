package com.xh.crmService.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.commentUtils.R;
import com.xh.crmService.entity.CrmBanner;
import com.xh.crmService.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author XH
 * @since 2021-02-08
 */
@RestController
@RequestMapping("/crmService/banner")
@CrossOrigin
public class CrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    /**
     * 分页查询banner
     * @return
     */
    @GetMapping("/getBanners/{current}/{limit}")
    public R getBanners(@PathVariable("current")long current,@PathVariable("limit") long limit){
        List<CrmBanner> bannerList = crmBannerService.getBannerList(current, limit);
        return  R.ok().data("bannerList",bannerList);
    }
}

