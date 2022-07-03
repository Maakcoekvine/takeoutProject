package com.coek.takeout.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

/**
 * @author MaakCheukVing
 * @create 2022-06-28 16:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity{
    private Long id;
    private Integer type;
    private String name;
    private Integer sort;


}