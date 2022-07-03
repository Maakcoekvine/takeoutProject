package com.coek.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coek.takeout.domain.entity.Category;

/**
 * @author MaakCoekVine
 * @create 2022-06-28 16:11
 */
public interface CategoryService extends IService<Category> {
     void remove(Long cId);
}
