package com.coek.takeout.common;

/**
 * @author MaakCheukVing
 * @create 2022-06-28 20:23
 *
 * 自定义业务异常
 */
public class CustomException extends RuntimeException{
    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }
}