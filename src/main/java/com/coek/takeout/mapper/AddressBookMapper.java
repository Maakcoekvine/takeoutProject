package com.coek.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coek.takeout.domain.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MaakCheukVing
 * @create 2022-07-01 10:35
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}