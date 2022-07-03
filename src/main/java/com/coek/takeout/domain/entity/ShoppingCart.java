package com.coek.takeout.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author MaakCheukVing
 * @create 2022-07-01 17:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart implements Serializable {
    private Long id;
    private String name;
    private String image;
    private Long userId;
    private Long dishId;
    private Long setmealId;
    private String dishFlavor;
    private Integer number;
    private Double amount;
    private LocalDateTime create_time;

}