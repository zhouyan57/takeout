package com.sky.service;

import com.sky.dto.DishDTO;

/**
 * ClassName: DishService
 * Package: com.sky.service
 * Description:
 *
 * @Author: Jane
 * @Create: 2023/7/15 - 21:00
 * @version: v1.0
 */
public interface DishService {

    /**
     * 新增菜品和对应的口味
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);
}
