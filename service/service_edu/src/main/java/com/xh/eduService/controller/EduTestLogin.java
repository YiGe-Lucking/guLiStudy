package com.xh.eduService.controller;

import com.xh.commentUtils.R;
import org.springframework.web.bind.annotation.*;

@RestController
/**
 * 解决跨域问题
 */
@CrossOrigin
@RequestMapping("/eduService/user")
public class EduTestLogin {

    /**
     * login
     * @return
     */
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token","admin");
    }

    /**
     *
     * @return
     */
    @GetMapping("/info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
