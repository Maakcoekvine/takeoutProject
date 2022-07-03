package com.coek.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coek.takeout.domain.entity.BaseEntity;
import com.coek.takeout.domain.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MaakCheukVing
 * @create 2022-07-01 17:50
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}