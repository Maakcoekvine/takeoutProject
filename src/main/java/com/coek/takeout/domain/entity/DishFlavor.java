package com.coek.takeout.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author MaakCheukVing
 * @create 2022-06-29 12:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishFlavor extends BaseEntity implements Serializable {
    private Long id;
    private Long dishId;
    private String name;
    private String value;
    private Integer isDeleted;

}