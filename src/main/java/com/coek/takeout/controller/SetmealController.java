package com.coek.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coek.takeout.common.CustomException;
import com.coek.takeout.common.R;
import com.coek.takeout.domain.entity.Category;
import com.coek.takeout.domain.entity.Dish;
import com.coek.takeout.domain.entity.Setmeal;
import com.coek.takeout.domain.entity.SetmealDish;
import com.coek.takeout.dto.SetmealDto;
import com.coek.takeout.service.CategoryService;
import com.coek.takeout.service.SetmealDishService;
import com.coek.takeout.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author MaakCheukVing
 * @create 2022-06-30 15:26
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 添加套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){

        log.info("添加套餐");
        setmealService.saveSetmealWithSetmealDish(setmealDto);
        return R.success("添加成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> setmealPageList(Integer page,Integer pageSize,String name){
        log.info("套餐分页查询。page={},pageSize={}",page,pageSize);
        // 分页器
        Page<Setmeal> pageInfo=new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage=new Page<>();
        // 查询条件
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        // 是否存在
        wrapper.eq(Setmeal::getIsDeleted,0);

        // 模糊查询
        wrapper.like(!StringUtils.isEmpty(name),Setmeal::getName,name);
        // 分页查询
        setmealService.page(pageInfo,wrapper);

        // 拷贝
        BeanUtils.copyProperties(pageInfo,setmealDtoPage,"records");

        List<Setmeal> setmealList = pageInfo.getRecords();

        List<SetmealDto> setmealDtoList=setmealList.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();

            // 拷贝
            BeanUtils.copyProperties(item,setmealDto);

            // 查询分类名
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);

            setmealDto.setCategoryName(category.getName());

            return setmealDto;
        }).collect(Collectors.toList());

        // 存入setmealDtoPage
        setmealDtoPage.setRecords(setmealDtoList);
        return R.success(setmealDtoPage);
    }

    /**
     * 删除和批量删除
     * @param ids
     * @return
     */
    @Transactional
    @DeleteMapping
    public R delete(Long[] ids){

        log.info("批量删除套餐{}",ids);
        // select count(*) from dish where status =1 and id in
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus,1);
        setmealLambdaQueryWrapper.in(Setmeal::getId,ids);

        int count = setmealService.count(setmealLambdaQueryWrapper);
        if (count>0){
            throw new CustomException("当前套餐正在售卖,请停售再删除");
        }

        //删除
        LambdaUpdateWrapper<Setmeal> setmealLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        setmealLambdaUpdateWrapper.set(Setmeal::getIsDeleted,1);
        setmealLambdaUpdateWrapper.in(Setmeal::getId,ids);
        setmealService.update(setmealLambdaUpdateWrapper);


        // 删除相关表中字段
        LambdaUpdateWrapper<SetmealDish> setmealDishLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        setmealDishLambdaUpdateWrapper.set(SetmealDish::getIsDeleted,1);
        setmealDishLambdaUpdateWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.update(setmealDishLambdaUpdateWrapper);
        return R.success("删除成功！");
    }

    /**
     * 批量起售
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R setStatus(@PathVariable("status")Integer status,Long[] ids){

        log.info("批量操作套餐起售");
        LambdaUpdateWrapper<Setmeal> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Setmeal::getStatus,status);
        wrapper.in(Setmeal::getId,ids);
        setmealService.update(wrapper);
        return R.success("操作成功！");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> setmealList(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(Setmeal::getStatus,setmeal.getStatus());
        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);

    }
}