package com.coek.takeout.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author MaakCheukVing
 * @create 2022-06-28 19:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setmeal extends BaseEntity implements Serializable {
    private Long id;
    private Long  categoryId;
    private String name;
    private Double price;
    private Integer status;
    private String code;
    private String description;
    private String image;
    private Integer isDeleted;

}