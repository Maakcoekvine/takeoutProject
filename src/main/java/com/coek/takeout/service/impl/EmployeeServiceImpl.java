package com.coek.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coek.takeout.domain.entity.Employee;
import com.coek.takeout.mapper.EmployeeMapper;
import com.coek.takeout.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author MaakCheukVing
 * @create 2022-06-26 15:28
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}