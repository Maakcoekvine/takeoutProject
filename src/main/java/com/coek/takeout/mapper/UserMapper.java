package com.coek.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coek.takeout.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MaakCoekVine
 * @create 2022-06-30 21:18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
