package com.coek.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.coek.takeout.common.R;
import com.coek.takeout.domain.entity.ShoppingCart;
import com.coek.takeout.domain.entity.User;
import com.coek.takeout.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MaakCheukVing
 * @create 2022-07-01 17:57
 */
@RestController
@RequestMapping("shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 购物车列表
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> getList(HttpServletRequest request) {
        log.info("查找购物车列表");
        User user = (User) request.getSession().getAttribute("user");
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, user.getId());
        wrapper.orderByDesc(ShoppingCart::getCreate_time);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(wrapper);
        return R.success(shoppingCarts);
    }

    /**
     * 加入购物车
     *
     * @param cart
     * @param request
     * @return
     */
    @PostMapping("/add")
    public R addShoppingCart(@RequestBody ShoppingCart cart, HttpServletRequest request) {

        log.info("加入购物车");
        // 当前用户
        User user = (User) request.getSession().getAttribute("user");
        cart.setUserId(user.getId());

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, user.getId());

        if (cart.getDishId() != null) {
            // 加入的是菜品
            queryWrapper.eq(ShoppingCart::getDishId, cart.getDishId());
        } else {
            // 加入的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId, cart.getSetmealId());
        }

        // select * from cart where userId= and dishId=
        ShoppingCart shoppingCart = shoppingCartService.getOne(queryWrapper);
        if (ObjectUtils.isEmpty(shoppingCart)) {
            // 新添加的商品在购物车中不存在
            cart.setNumber(1);
            cart.setCreate_time(LocalDateTime.now());
            shoppingCartService.save(cart);
            shoppingCart = cart;
        } else {
            // 商品已经存在购物车中
            // 在原有数量上加1
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCartService.updateById(shoppingCart);
        }

        return R.success(shoppingCart);
    }

    /**
     * 清空购物车
     *
     * @param request
     * @return
     */
    @DeleteMapping("/clean")
    public R delCart(HttpServletRequest request) {
        log.info("清空购物车");
        User user = (User) request.getSession().getAttribute("user");

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, user.getId());

        shoppingCartService.remove(queryWrapper);

        return R.success("清空成功");
    }
}