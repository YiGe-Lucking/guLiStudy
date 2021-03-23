package com.xh.serviceBase.exceptionHander;

import com.xh.commentUtils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller通知  统一异常
 * @author a3818
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 1.全局异常处理
     * ExceptionHandler 异常处理器 当controller出现 异常的时候会调用这个方法 指定处理什么异常进行调用
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    /**
     * ResponseBody 为了返回数据
     */
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return  R.error().message(e.getMessage());
    }


    /**
     * 特定异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return  R.error().message(e.getMessage());
    }

    /**
     * 自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(GuLiException.class)
    @ResponseBody
    public R error(GuLiException e){
        e.printStackTrace();
        return  R.error().code(e.getCode()).message(e.getMsg());
    }



}
