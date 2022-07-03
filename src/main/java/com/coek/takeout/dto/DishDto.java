package com.coek.takeout.dto;

import com.coek.takeout.domain.entity.Dish;
import com.coek.takeout.domain.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
