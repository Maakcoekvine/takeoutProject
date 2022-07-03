package com.coek.takeout.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author MaakCheukVing
 * @create 2022-06-28 19:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private Long createUser;
    @TableField(value = "update_user",fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}