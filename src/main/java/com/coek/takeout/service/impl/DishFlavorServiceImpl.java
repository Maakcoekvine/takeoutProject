package com.coek.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coek.takeout.domain.entity.DishFlavor;
import com.coek.takeout.mapper.DishFlavorMapper;
import com.coek.takeout.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author MaakCheukVing
 * @create 2022-06-29 13:13
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}