package com.coek.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coek.takeout.domain.entity.AddressBook;
import com.coek.takeout.mapper.AddressBookMapper;
import com.coek.takeout.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author MaakCheukVing
 * @create 2022-07-01 10:36
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}