package com.coek.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coek.takeout.domain.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author MaakCoekVine
 * @create 2022-06-26 15:27
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
