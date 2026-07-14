package com.sale.pos_system.mapper;

import com.sale.pos_system.model.Category;
import com.sale.pos_system.model.Product;
import com.sale.pos_system.model.Store;
import com.sale.pos_system.payload.dto.ProductDto;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .mrp(product.getMrp())
                .category(CategoryMapper.toDto(product.getCategory()))
                .sellingPrice(product.getSellingPrice())
                .brand(product.getBrand())
                .storeId(product.getStore() != null ? product.getStore().getId() : null)
                .image(product.getImage())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static Product toEntity(ProductDto productDto, Store store, Category category) {
        return Product.builder()
                .name(productDto.getName())
                .store(store)
                .category(category)
                .sku(productDto.getSku())
                .description(productDto.getDescription())
                .mrp(productDto.getMrp())
                .sellingPrice(productDto.getSellingPrice())
                .brand(productDto.getBrand())
                .build();
    }
}
