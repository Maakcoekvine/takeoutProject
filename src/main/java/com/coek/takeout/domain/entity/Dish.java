package com.coek.takeout.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.security.DenyAll;
import java.io.Serializable;

/**
 * @author MaakCheukVing
 * @create 2022-06-28 19:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish extends BaseEntity implements Serializable {
    private Long id;
    private String name;
    private Long categoryId;
    private Double price;
    private String code;
    private String image;
    private String description;
    private Integer status;
    private Integer sort;
    private Integer isDeleted;

}