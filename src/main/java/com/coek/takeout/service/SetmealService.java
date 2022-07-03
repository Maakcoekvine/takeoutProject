package com.coek.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coek.takeout.domain.entity.Setmeal;
import com.coek.takeout.dto.SetmealDto;

/**
 * @author MaakCheukVing
 * @create 2022-06-28 19:58
 */
public interface SetmealService extends IService<Setmeal> {

    /**
     * 添加套餐
     * @param setmealDto
     */
    void saveSetmealWithSetmealDish(SetmealDto setmealDto);
}