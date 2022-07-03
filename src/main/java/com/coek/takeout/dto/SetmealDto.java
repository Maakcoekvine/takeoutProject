package com.coek.takeout.dto;

;
import com.coek.takeout.domain.entity.Setmeal;
import com.coek.takeout.domain.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
