package com.coek.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.coek.takeout.common.BaseContext;
import com.coek.takeout.common.R;
import com.coek.takeout.domain.entity.AddressBook;
import com.coek.takeout.domain.entity.User;
import com.coek.takeout.service.AddressBookService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author MaakCheukVing
 * @create 2022-07-01 10:37
 */
@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;


    /**
     * 获取当前用户所有收货地址
     * @param request
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressBook>> getAddressList(HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        log.info("当前用户手机号为:{}",user.getPhone());

        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId,user.getId());
        wrapper.eq(AddressBook::getIsDeleted,0);
        wrapper.orderByDesc(AddressBook::getUpdateTime);

        List<AddressBook> addressBookList = addressBookService.list(wrapper);
        return R.success(addressBookList);
    }

    /**
     * 设置默认收货地址
     * @param addressBook
     * @return
     */
    @Transactional
    @PutMapping("/default")
    public R setDefault(@RequestBody AddressBook addressBook){
        log.info("设置默认收货地址id:{}",addressBook.getId());
        LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();
        // 先将所有地址设为非默认
        updateWrapper.set(AddressBook::getIsDefault,0);
        addressBookService.update(updateWrapper);

        // 设置默认收货地址
        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);

        return R.success("设置成功");
    }

    /**
     * 查询地址详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> addressBookDetail(@PathVariable Long id){

        log.info("需要查询的地址id为：{}",id);

        AddressBook addressBook = addressBookService.getById(id);
        return R.success(addressBook);
    }

    /**
     * 更新地址
     * @param addressBook
     * @return
     */
    @PutMapping
    @Transactional
    public R editAddress(@RequestBody AddressBook addressBook){

        log.info("更新的地址信息：{}",addressBook);
        addressBookService.updateById(addressBook);
        return R.success("更新成功");
    }

    /**
     * 删除地址
     * @param ids
     * @return
     */
    @Transactional
    @DeleteMapping
    public R delAddress(Long ids){

        log.info("需要删除的收货地址id:{}",ids);
        AddressBook addressBook = addressBookService.getById(ids);

        // 删除的非默认地址
        LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(AddressBook::getIsDeleted,1);
        updateWrapper.eq(AddressBook::getId,ids);
        addressBookService.update(updateWrapper);

        // 删除的是默认地址
        if (addressBook.getIsDefault()==1){
            // select  * from table where default=0 and delete=0 orderBy updateTime limit 1
            LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AddressBook::getIsDefault,0);
            queryWrapper.eq(AddressBook::getIsDeleted,0);
            queryWrapper.orderByDesc(AddressBook::getUpdateTime);
            queryWrapper.last("limit 1");
            AddressBook book = addressBookService.getOne(queryWrapper);

            // 默认地址为最新更新的地址
            LambdaUpdateWrapper<AddressBook> updateWrapper1 = new LambdaUpdateWrapper<>();
            updateWrapper1.set(AddressBook::getIsDefault,1);
            updateWrapper1.eq(AddressBook::getId,book.getId());
            addressBookService.update(updateWrapper1);
        }

        return R.success("删除地址成功!");
    }


    @GetMapping("/default")
    public R<AddressBook> defaultAddress(){

        Long userId = BaseContext.getUserId();
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId,userId);
        queryWrapper.eq(AddressBook::getIsDefault,1);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);
        return R.success(addressBook);
    }
}