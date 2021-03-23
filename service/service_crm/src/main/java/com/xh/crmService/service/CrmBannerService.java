package com.xh.crmService.service;

import com.xh.crmService.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author XH
 * @since 2021-02-08
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getBannerList(long current, long limit);
}
