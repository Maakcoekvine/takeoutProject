package com.coek.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coek.takeout.common.R;
import com.coek.takeout.domain.entity.Category;
import com.coek.takeout.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MaakCheukVing
 * @create 2022-06-28 16:13
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品&套餐分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public R save(@RequestBody Category category) {
        log.info("需要添加的分类:{}", category);

        categoryService.save(category);
        return R.success("添加分类成功");
    }

    /**
     * 菜品分类分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R categoryInfo(Integer page, Integer pageSize) {

        log.info("查询菜品分类,page={},pageSize={}", page, pageSize);

        // 分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);

        // 排序
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Category::getSort);

        categoryService.page(pageInfo, wrapper);
        return R.success(pageInfo);
    }

    /**
     * 删除品分类/套餐分类
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R delete(Long ids) {

        log.info("需要删除的分类id为:{}", ids);
        categoryService.remove(ids);
        return R.success("删除分类成功");
    }

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    @PutMapping()
    public R editCategory(@RequestBody Category category) {
        log.info("修改分类：{}", category.toString());
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    /**
     * 菜品分类列表
     *
     * @param type
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> getCategoryList(Integer type) {

        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(type!=null,Category::getType, type);
        //添加排序条件
        wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> categoryList = categoryService.list(wrapper);
        return R.success(categoryList);
    }
}