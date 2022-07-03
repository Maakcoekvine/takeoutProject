package com.coek.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coek.takeout.domain.entity.User;
import com.coek.takeout.mapper.UserMapper;
import com.coek.takeout.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author MaakCheukVing
 * @create 2022-06-30 21:19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}