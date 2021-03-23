package com.xh.uService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.commentUtils.JwtUtils;
import com.xh.commentUtils.MD5;
import com.xh.serviceBase.exceptionHander.GuLiException;
import com.xh.uService.entity.UcenterMember;
import com.xh.uService.entity.vo.RegisterUserVo;
import com.xh.uService.mapper.UcenterMemberMapper;
import com.xh.uService.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author XH
 * @since 2021-02-13
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录
     * @param ucenterMember
     * @return
     */
    @Override
    public String loginUser(UcenterMember ucenterMember) {
        // 获取登录手机和密码
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        // 手机跟密码是否为空
        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuLiException(20001,"手机号或密码不能为空");
        }

        // 验证手机号是否正确
        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper = new QueryWrapper<>();
        ucenterMemberQueryWrapper.eq("mobile",mobile);
        // user是查询到的数据库用户
        UcenterMember user = this.getOne(ucenterMemberQueryWrapper);
        if (user==null){
            throw new GuLiException(20001,"该手机号没有注册");
        }

        // 验证密码是否正常
        // 存储到数据库的密码是进行加密的 需要对登录的密码进行一个加密在比较
        if (!MD5.encrypt(password).equals(user.getPassword())){
            throw new GuLiException(20001,"密码错误");
        }

        //判断用户是否被禁用
        if(user.getIsDisabled()){
            throw new GuLiException(20001,"用户被禁用登录失败");
        }

        // 将登录的用户用JWT生成令牌
        String token = JwtUtils.getJwtToken(user.getId(), user.getNickname());
        return token;
    }

    @Override
    public void registerUser(RegisterUserVo registerUserVo) {
        // 获取注册基本信息
        String code = registerUserVo.getCode();
        String mobile = registerUserVo.getMobile();
        String nickname = registerUserVo.getNickname();
        String password = registerUserVo.getPassword();

        // 验证基本信息是否为空
        if (StringUtils.isEmpty(code)||StringUtils.isEmpty(mobile)||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(password)){
            throw new GuLiException(20001,"注册信息不能为空");
        }

        // 验证码 TODO  需要登录阿里云短信服务验证通过

        // 验证手机号是否以及注册过
        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper = new QueryWrapper<>();
        ucenterMemberQueryWrapper.eq("mobile",mobile);
        int count = this.count(ucenterMemberQueryWrapper);
        if (count>0){
            throw new GuLiException(20001,"该手机号已经注册过");
        }

        // 将数据添加到数据库
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setNickname(nickname);
        //用户不禁用
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("https://edu-longyang.oss-cn-beijing.aliyuncs.com/fa104ef58c4e5bc4270d911da1d1b34d.jpg");
        baseMapper.insert(ucenterMember);
    }

    /**
     * 根据用户微信号查询用户
     * @param openId
     * @return
     */
    @Override
    public UcenterMember getUserInfoByOpenId(String openId) {
        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper = new QueryWrapper<>();
        ucenterMemberQueryWrapper.eq("openid",openId);
        return baseMapper.selectOne(ucenterMemberQueryWrapper);
    }
}
