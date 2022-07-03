package com.coek.takeout.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * @author MaakCheukVing
 * @create 2022-06-26 15:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Employee extends BaseEntity implements Serializable {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String phone;
    private String sex;
    private String idNumber; // 身份证
    private Integer status;


}