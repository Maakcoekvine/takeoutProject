package com.coek.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coek.takeout.domain.entity.ShoppingCart;
import com.coek.takeout.mapper.ShoppingCartMapper;
import com.coek.takeout.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author MaakCheukVing
 * @create 2022-07-01 17:56
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper,ShoppingCart> implements ShoppingCartService {
}