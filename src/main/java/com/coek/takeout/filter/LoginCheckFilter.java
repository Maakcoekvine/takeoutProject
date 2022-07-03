package com.coek.takeout.filter;

import com.alibaba.fastjson.JSON;
import com.coek.takeout.common.BaseContext;
import com.coek.takeout.common.R;
import com.coek.takeout.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author MaakCheukVing
 * @create 2022-06-26 17:27
 * <p>
 * 检查用户是否登录的过滤器
 */
@WebFilter(filterName = "loginCheck", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    // 匹配路径
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取请求路径
        String requestURI = request.getRequestURI();
        log.info("拦截到的请求 :{}", requestURI);
        // 允许访问的资源
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login",
        };

        // 本次请求是否需要处理
        boolean check = check(urls, requestURI);
        if (check) {
            log.info("本次请求无需处理");
            // 放行
            filterChain.doFilter(request, response);
            return;
        }

        // 用户是否登录
        Long id = (Long) request.getSession().getAttribute("employee");
        if (!ObjectUtils.isEmpty(id)){
            // 已登录
            log.info("用户已登录,id：{}",id);
            // 存入线程池
            BaseContext.setValue(id);
            filterChain.doFilter(request,response);
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (!ObjectUtils.isEmpty(user)){
            // 已登录
            log.info("用户已登录,id：{}",user);
            // 存入线程池
            BaseContext.setUserId(user.getId());
            filterChain.doFilter(request,response);
            return;
        }

        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 路径匹配，查看当前路径是否需要处理
     * @param urls
     * @param uri
     * @return
     */
    private boolean check(String[] urls, String uri) {

        for (String url : urls) {
            if (PATH_MATCHER.match(url, uri)) {
                return true;
            }
        }
        return false;
    }
}