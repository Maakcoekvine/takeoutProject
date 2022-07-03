package com.coek.takeout.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author MaakCheukVing
 * @create 2022-07-01 10:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressBook extends BaseEntity implements Serializable {
    private Long id;
    private Long userId;
    private String consignee;
    private Integer sex;
    private String phone;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String districtCode;
    private String districtName;
    private String detail;
    private String label;
    private Integer isDefault;
    private Integer isDeleted;

}