package com.coek.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coek.takeout.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MaakCoekVine
 * @create 2022-06-28 16:10
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
