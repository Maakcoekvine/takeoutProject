package com.coek.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.coek.takeout.common.CustomException;
import com.coek.takeout.common.R;
import com.coek.takeout.common.ValidateCodeUtils;
import com.coek.takeout.domain.entity.User;
import com.coek.takeout.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author MaakCheukVing
 * @create 2022-06-30 20:49
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 发送验证码
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/sendMsg")
    public R sendMessage(@RequestBody  User user, HttpServletRequest request){

        String phone = user.getPhone();

        if (StringUtils.isEmpty(phone)){
            throw new CustomException("手机号不能为空");
        }

        String code = ValidateCodeUtils.generateValidateCode4String(4);
        log.info("用户登录验证码为:{}",code);
        // 存入session
        request.getSession().setAttribute(phone,code);
        return R.success("发送成功");
    }

    /**
     * 用户登录
     * @param map
     * @param request
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody Map<String,Object> map,HttpServletRequest request){

        String phone = map.get("phone").toString();
        String code = map.get("code").toString();
        log.info("获取的手机号：{},验证码：{}",phone,code);

        String codeInSession = (String) request.getSession().getAttribute(phone);
        if (!codeInSession.equals(code)){
            throw new CustomException("验证码错误");
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone,phone);
        User user = userService.getOne(wrapper);

        // 新用户
        if (ObjectUtils.isEmpty(user)){
            log.info("新用户注册");
            // 为空
            user=new User();
            user.setPhone(phone);
            // 添加
            userService.save(user);
        }

        // 老用户
        request.getSession().setAttribute("user",user);
        return R.success(user);
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @PostMapping("/loginout")
    public R logout(HttpServletRequest request){

        log.info("用户退出登录");
        request.getSession().removeAttribute("user");

        return R.success("成功退出");
    }
}