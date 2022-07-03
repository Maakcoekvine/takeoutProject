package com.coek.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coek.takeout.common.R;
import com.coek.takeout.domain.entity.Category;
import com.coek.takeout.domain.entity.Dish;
import com.coek.takeout.domain.entity.DishFlavor;
import com.coek.takeout.dto.DishDto;
import com.coek.takeout.service.CategoryService;
import com.coek.takeout.service.DishFlavorService;
import com.coek.takeout.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MaakCheukVing
 * @create 2022-06-28 22:58
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 菜品分类分页列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R dishInfo(Integer page, Integer pageSize,String name) {
        log.info("菜品管理.page={},pageSize={}", page, pageSize);

        // 分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage=new Page<>();
        // 查询条件
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(name),Dish::getName,name);
        // 是否删除 (逻辑 isDelete=1)
        wrapper.eq(Dish::getIsDeleted, 0);
        wrapper.orderByDesc(Dish::getUpdateTime);
        // 查询
        dishService.page(pageInfo, wrapper);

        // 拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> dtoList=records.stream().map((item)->{

            DishDto dishDto = new DishDto();
            // 拷贝
            BeanUtils.copyProperties(item,dishDto);

            //查询菜品分类名
            Category category = categoryService.getById(item.getCategoryId());

            if (!ObjectUtils.isEmpty(category)){
                dishDto.setCategoryName(category.getName());
            }

            return dishDto;

        }).collect(Collectors.toList());

        dishDtoPage.setRecords(dtoList);
        return R.success(dishDtoPage);
    }

    /**
     * 逻辑删除菜品(可批量)
     *
     * @param ids
     * @return
     */
    @DeleteMapping()
    public R removeDish(Long[] ids) {
        log.info("需要删除的菜品id个数:{}", ids.length);
        LambdaUpdateWrapper<Dish> updateWrapper = new LambdaUpdateWrapper<>();
        // 逻辑删除
        updateWrapper.set(Dish::getIsDeleted, 1);

        //批量更新
        updateWrapper.in(Dish::getId, ids);

        dishService.update(updateWrapper);
        return R.success("删除成功");
    }

    /**
     * 批量起售/停售菜品
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R updateStatus(@PathVariable("status")Integer status,Long[] ids) {

        log.info("当前菜品的status={}",status);

        LambdaUpdateWrapper<Dish> wrapper = new LambdaUpdateWrapper<>();
        // status为0，则将收到的菜品的status 设置为0
        wrapper.set(Dish::getStatus,status);
        wrapper.in(Dish::getId,ids);
        dishService.update(wrapper);
        return R.success("设置成功");
    }

    /**
     * 添加菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R saveDish(@RequestBody DishDto dishDto){
        log.info("添加菜品:{}",dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("添加成功");
    }

    /**
     * 获取菜品信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R dishInfo(@PathVariable("id") Long id){

        log.info("需要查询的菜品id={}",id);
        DishDto dishDto = dishService.getInfoWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R editDish(@RequestBody DishDto dishDto){
        log.info("修改菜品");
        dishService.editDishWithFlavor(dishDto);
        return R.success("Ok");
    }

    /**
     * 根据分类id查询菜品列表
     * @param dish
     * @return
     */
    /*@GetMapping("/list")
    public R<List<Dish>> getDishListByCateId(Dish dish){
        log.info("根据分类查询菜品列表,{}",dish);
        List<Dish> dishList=null;
        if (dish.getCategoryId()!=null){
            LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Dish::getCategoryId,dish.getCategoryId());
            wrapper.eq(Dish::getStatus,1);
            wrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
            dishList = dishService.list(wrapper);
        }
        return R.success(dishList);
    }*/
    @GetMapping("/list")
    public R<List<DishDto>> getDishListByCateId(Dish dish){
        log.info("根据分类查询菜品列表,{}",dish);
        List<Dish> dishList=null;
        if (dish.getCategoryId()!=null){
            LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Dish::getCategoryId,dish.getCategoryId());
            wrapper.eq(Dish::getStatus,1);
            wrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
            dishList = dishService.list(wrapper);
        }
        List<DishDto> dtoList=dishList.stream().map((item)->{

            DishDto dishDto = new DishDto();
            // 拷贝
            BeanUtils.copyProperties(item,dishDto);

            //查询菜品分类名
            Category category = categoryService.getById(item.getCategoryId());

            if (!ObjectUtils.isEmpty(category)){
                dishDto.setCategoryName(category.getName());
            }

            // 当前菜品的id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId,dishId);
            List<DishFlavor> dishFlavors = dishFlavorService.list(queryWrapper);
            dishDto.setFlavors(dishFlavors);

            return dishDto;

        }).collect(Collectors.toList());



        return R.success(dtoList);
    }
}