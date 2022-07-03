package com.coek.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coek.takeout.common.R;
import com.coek.takeout.domain.entity.Dish;
import com.coek.takeout.dto.DishDto;

/**
 * @author MaakCheukVing
 * @create 2022-06-28 19:57
 */
public interface DishService extends IService<Dish> {
    /**
     * 添加菜品
     * @param dishDto
     */
    void saveWithFlavor(DishDto dishDto);

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    DishDto getInfoWithFlavor(Long id);

    /**
     * 修改菜品和口味
     * @param dishDto
     */
    void editDishWithFlavor(DishDto dishDto);
}