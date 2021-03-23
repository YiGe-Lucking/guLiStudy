package com.xh.crmService.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.crmService.entity.CrmBanner;
import com.xh.crmService.mapper.CrmBannerMapper;
import com.xh.crmService.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author XH
 * @since 2021-02-08
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {


    /**
     * 查询banner
     *
     * Cacheable 注解开启缓存  value，key属性组成redis缓存中的key 方法的返回值为value
     * @param current
     * @param limit
     * @return
     */

    @Cacheable(value = "banner",key = "'bannerList'")
    @Override
    public List<CrmBanner> getBannerList(long current, long limit) {
        Page<CrmBanner> crmBannerPage = new Page<>(current, limit);
        IPage<CrmBanner> page = this.page(crmBannerPage,null);
        List<CrmBanner> records = page.getRecords();
        return records;
    }
}
