package com.coek.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coek.takeout.common.CustomException;
import com.coek.takeout.domain.entity.Category;
import com.coek.takeout.domain.entity.Dish;
import com.coek.takeout.domain.entity.Setmeal;
import com.coek.takeout.mapper.CategoryMapper;
import com.coek.takeout.service.CategoryService;
import com.coek.takeout.service.DishService;
import com.coek.takeout.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author MaakCheukVing
 * @create 2022-06-28 16:11
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    /**
     * 删除商品分类
     * @param cId
     */
    @Override
    public void remove(Long cId) {
        // 添加查询条件，根据分类id进行查询
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,cId);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        // 当前分类是否关联了菜品，如果关联，抛异常
        if (count1>0){
            // 抛出异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        // 当前分类是否关联了菜品，如果关联，抛异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,cId);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2>0){
            // 抛出异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }

        // 正常删除
        super.removeById(cId);
    }
}