package com.sale.pos_system.service;

import com.sale.pos_system.model.User;
import com.sale.pos_system.payload.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDTO, User user) throws Exception;
    ProductDto updateProduct(Long id, ProductDto productDTO, User user) throws Exception;
    void deleteProduct(Long id, User user) throws Exception;
    List<ProductDto> getProductByStoreId(Long storeId);
    List<ProductDto> searchByKeyword(Long storeId, String keyword);

}
