package com.coek.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coek.takeout.domain.entity.Dish;
import com.coek.takeout.domain.entity.DishFlavor;
import com.coek.takeout.dto.DishDto;
import com.coek.takeout.mapper.DishMapper;
import com.coek.takeout.service.DishFlavorService;
import com.coek.takeout.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author MaakCheukVing
 * @create 2022-06-28 20:03
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    /**
     * 添加菜品
     * @param dishDto
     */
    @Transactional
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);

        // 获取菜品id
        Long dishId = dishDto.getId();
        // 为每个DishFlavor 添加dishId
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors=flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @Override
    public DishDto getInfoWithFlavor(Long id) {

        // 根据id 查询Dish表
        Dish dish = this.getById(id);

        // 拷贝
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);

        // 根据id 查询DishFlavor表
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> dishFlavors = dishFlavorService.list(wrapper);

        dishDto.setFlavors(dishFlavors);
        return dishDto;
    }

    /**
     * 修改菜品和口味
     * @param dishDto
     */
    @Transactional
    @Override
    public void editDishWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors=flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
//        LambdaUpdateWrapper<DishFlavor> wrapper = new LambdaUpdateWrapper<>();
//        wrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.updateBatchById(flavors);
    }
}