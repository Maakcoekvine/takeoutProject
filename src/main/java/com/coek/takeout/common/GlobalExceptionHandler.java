package com.coek.takeout.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author MaakCheukVing
 * @create 2022-06-26 21:03
 *
 * 全局异常处理
 */
@RestControllerAdvice(annotations = {RestController.class, Service.class})
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> handlerException(SQLIntegrityConstraintViolationException ex){

        String msg =ex.getMessage();
        log.info("捕获到异常 {}",msg);

        // 处理并响应
        if (msg.contains("Duplicate entry")){
            String[] strings = msg.split(" ");
            msg=strings[2];
            return R.error("账号："+msg+" 已存在");
        }

        return R.error("未知错误");
    }
    @ExceptionHandler(CustomException.class)
    public R handlerCustomException(CustomException ex){
        log.info("捕获到了异常：{}",ex.getMessage());
        return R.error(ex.getMessage());
    }
}