package com.test.spzx.common.exception;

import com.test.spzx.model.vo.common.Result;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//统一异常处理
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)//注解表示这个方法是一个异常处理方法，它将处理所有Exception类的异常，包括其子类。
    @ResponseBody//表示这个方法返回的响应体将直接作为HTTP响应的正文
    public Result error(Exception e){
         e.printStackTrace();//打印了异常的堆栈跟踪信息。这通常用于调试目的，以便开发人员能够查看异常的详细信息。
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }
    @ExceptionHandler(GuiguException.class)//注解表示这个方法是一个异常处理方法，它将处理所有Exception类的异常，包括其子类。
    @ResponseBody//表示这个方法返回的响应体将直接作为HTTP响应的正文
    public  Result error(GuiguException e){
        e.printStackTrace();
        return Result.build(null,e.getResultCodeEnum());
    }
}
