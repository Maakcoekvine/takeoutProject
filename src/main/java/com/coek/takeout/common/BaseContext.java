package com.coek.takeout.common;

/**
 * @author MaakCheukVing
 * @create 2022-06-28 11:33
 */
public class BaseContext {
    private static ThreadLocal threadLocal=new ThreadLocal();

    public static void setValue(Long id){
        threadLocal.set(id);
    }

    public static void setUserId(Long id){
        threadLocal.set(id);
    }

    public static Long getValue(){
        return (Long) threadLocal.get();
    }
    public static Long getUserId(){
        return (Long) threadLocal.get();
    }
}