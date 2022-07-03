package com.coek.takeout.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author MaakCheukVing
 * @create 2022-06-30 20:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Long id;
    private String name;
    private String phone;
    private String sex;
    private String id_number;
    private String avatar;
    private Integer status;

}