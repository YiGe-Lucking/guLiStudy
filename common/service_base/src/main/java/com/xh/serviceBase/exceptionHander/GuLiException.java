package com.xh.serviceBase.exceptionHander;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuLiException extends RuntimeException {

    @ApiModelProperty(value =  "异常状态码")
    private Integer code;
    @ApiModelProperty(value =  "异常信息")
    private String msg;


}
