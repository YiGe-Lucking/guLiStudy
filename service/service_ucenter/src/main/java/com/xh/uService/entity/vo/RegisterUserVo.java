package com.xh.uService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 用户注册接收前端的数据
 * @author a3818
 */
@Data
public class RegisterUserVo {
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
