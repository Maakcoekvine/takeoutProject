package com.coek.takeout;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.coek.takeout.domain.entity.AddressBook;
import com.coek.takeout.service.AddressBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author MaakCheukVing
 * @create 2022-07-01 12:27
 */
@SpringBootTest
public class MyTest {

    @Autowired
    private AddressBookService addressBookService;
    @Test
    public void test01(){
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(AddressBook::getUpdateTime);
        wrapper.last("limit 1");
        AddressBook addressBook = addressBookService.getOne(wrapper);
        System.out.println(addressBook);
    }
}