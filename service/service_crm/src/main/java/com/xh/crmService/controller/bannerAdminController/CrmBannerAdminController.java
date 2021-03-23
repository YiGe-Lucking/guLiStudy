package com.xh.crmService.controller.bannerAdminController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.commentUtils.R;
import com.xh.crmService.entity.CrmBanner;
import com.xh.crmService.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * banner的后台管理
 * @author a3818
 */
@RestController
@RequestMapping("/crmService/adminBanner")
@CrossOrigin
public class CrmBannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;
    /**
     * 条件查询分页 获得banner
     * @return
     */
    @GetMapping("/getAllBanner/{current}/{limit}")
    public R getAllBanner(
            @PathVariable("current") long current
            ,@PathVariable("limit") long limit
            ,@RequestBody CrmBanner crmBanner){
        Page<CrmBanner> crmBannerPage = new Page<>(current,limit);
        // 条件构造
        QueryWrapper<CrmBanner> crmBannerQueryWrapper = new QueryWrapper<>();
        String title = crmBanner.getTitle();
        if (!StringUtils.isEmpty(title)){
            crmBannerQueryWrapper.like("title",title);
        }
        IPage<CrmBanner> page = crmBannerService.page(crmBannerPage, crmBannerQueryWrapper);
        List<CrmBanner> records = page.getRecords();
        long total = page.getTotal();
        long current1 = page.getCurrent();
        return R.ok().data("crmBannerList",records).data("total",total).data("current",current1);
    }

    /**
     * 添加banner
     * @param crmBanner
     * @return
     */
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }


    /**
     * 根据id 获取到banner
     * @param bannerId
     * @return
     */
    @GetMapping("/addBanner/{bannerId}")
    public R getBannerById(@PathVariable("bannerId") String bannerId){
        CrmBanner banner = crmBannerService.getById(bannerId);
        return R.ok().data("banner",banner);
    }

    /**
     * 修改banner
     * @param crmBanner
     * @return
     */
    @PostMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }

    /**
     * 根据id删除banner
     * @param bannerId
     * @return
     */
    @DeleteMapping("/deleteBanner/{bannerId}")
    public R deleteBanner(@PathVariable("bannerId") String bannerId){
        crmBannerService.removeById(bannerId);
        return R.ok();
    }

}
