package com.xh.uService.controller;


import com.xh.commentUtils.JwtUtils;
import com.xh.commentUtils.R;
import com.xh.commentUtils.orderVo.UcenterMemberOrder;
import com.xh.uService.entity.UcenterMember;
import com.xh.uService.entity.vo.RegisterUserVo;
import com.xh.uService.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author XH
 * @since 2021-02-13
 */
@RestController
@RequestMapping("/uService/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;



    /**
     * 登录
     * @param ucenterMember
     * @return
     */
    @PostMapping("/login")
        public R loginUser(@RequestBody UcenterMember ucenterMember){
        String token = ucenterMemberService.loginUser(ucenterMember);
        return R.ok().data("token",token);
    }

    /**
     * 注册 register
     * @param registerUserVo
     * @return
     */
    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterUserVo registerUserVo){
        ucenterMemberService.registerUser(registerUserVo);
        return R.ok();
    }

    /**
     * 根据token获取用户信息
     * @param request
     * @return
     */
    @GetMapping("/getUserInfo")
    public R getUserInfo(HttpServletRequest request){
        // 根据token获取到用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println(memberId);
        // 用户id进行查询数据库获取到用户
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    /**
     * 根据用户id获取用户信息 提供给远程服务调用
     * @param userId
     * @return
     */
    @GetMapping("/getUserInfo/{userId}")
    public UcenterMemberOrder getUserInfo(@PathVariable("userId") String userId){
        UcenterMember user = ucenterMemberService.getById(userId);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(user,ucenterMemberOrder);
        return ucenterMemberOrder;

    }


}

