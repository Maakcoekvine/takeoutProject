package com.coek.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coek.takeout.domain.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MaakCoekVine
 * @create 2022-06-28 19:56
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
