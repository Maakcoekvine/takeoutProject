package com.coek.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coek.takeout.domain.entity.Setmeal;
import com.coek.takeout.domain.entity.SetmealDish;
import com.coek.takeout.dto.SetmealDto;
import com.coek.takeout.mapper.SetmealMapper;
import com.coek.takeout.service.SetmealDishService;
import com.coek.takeout.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MaakCheukVing
 * @create 2022-06-28 20:05
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    @Transactional
    @Override
    public void saveSetmealWithSetmealDish(SetmealDto setmealDto) {

        // 添加套餐
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        // 添加套餐下的菜品
        setmealDishService.saveBatch(setmealDishes);
    }
}