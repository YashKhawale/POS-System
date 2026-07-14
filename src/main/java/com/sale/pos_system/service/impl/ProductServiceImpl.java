package com.sale.pos_system.service.impl;

import com.sale.pos_system.mapper.ProductMapper;
import com.sale.pos_system.model.Category;
import com.sale.pos_system.model.Product;
import com.sale.pos_system.model.Store;
import com.sale.pos_system.model.User;
import com.sale.pos_system.payload.dto.ProductDto;
import com.sale.pos_system.repository.CategoryRepository;
import com.sale.pos_system.repository.ProductRepository;
import com.sale.pos_system.repository.StoreRepository;
import com.sale.pos_system.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto productDTO, User user) throws Exception {
        Store store = storeRepository.findById(productDTO.getStoreId())
                .orElseThrow(() -> new Exception("Store not found"));
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new Exception("Category not found"));
        Product product = ProductMapper.toEntity(productDTO, store, category);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDTO, User user) throws Exception {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Product not found"));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setSku(productDTO.getSku());
        product.setImage(productDTO.getImage());
        product.setMrp(productDTO.getMrp());
        product.setSellingPrice(productDTO.getSellingPrice());
        product.setBrand(productDTO.getBrand());
        product.setUpdatedAt(LocalDateTime.now());

        if(productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new Exception("Category not found"));
            product.setCategory(category);
        }

        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long id, User user) throws Exception {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> getProductByStoreId(Long storeId) {
        List<Product> products = productRepository.findByStoreId(storeId);
        return products.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchByKeyword(Long storeId, String keyword) {
        List<Product> products = productRepository.searchByKeyword(storeId, keyword);
        return products.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }
}
