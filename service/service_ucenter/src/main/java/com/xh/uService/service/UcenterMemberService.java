package com.xh.uService.service;

import com.xh.uService.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.uService.entity.vo.RegisterUserVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author XH
 * @since 2021-02-13
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 登录
     * @param ucenterMember
     * @return
     */
    String loginUser(UcenterMember ucenterMember);

    /**
     * 注册
     * @param registerUserVo
     */
    void registerUser(RegisterUserVo registerUserVo);

    /**
     * 根据微信号查询用户
     * @return
     */
    UcenterMember getUserInfoByOpenId(String openid);
}
