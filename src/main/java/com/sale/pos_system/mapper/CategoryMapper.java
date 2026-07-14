package com.sale.pos_system.mapper;

import com.sale.pos_system.model.Category;
import com.sale.pos_system.payload.dto.CategoryDto;

public class CategoryMapper {
    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .storeId(category.getStore() != null ? category.getStore().getId() : null)
                .build();
    }
}
