package com.sale.pos_system.service.impl;

import com.sale.pos_system.domain.UserRole;
import com.sale.pos_system.exceptions.UserException;
import com.sale.pos_system.mapper.CategoryMapper;
import com.sale.pos_system.model.Category;
import com.sale.pos_system.model.Store;
import com.sale.pos_system.model.User;
import com.sale.pos_system.payload.dto.CategoryDto;
import com.sale.pos_system.repository.CategoryRepository;
import com.sale.pos_system.repository.StoreRepository;
import com.sale.pos_system.service.CategoryService;
import com.sale.pos_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public CategoryDto createCategory(CategoryDto dto) throws Exception {
        User user = userService.getCurrentUser();
        Store store = storeRepository.findById(dto.getStoreId()).orElseThrow(
                () -> new Exception("Store not found")
        );
        Category category = Category.builder()
                .store(store)
                .name(dto.getName())
                .build();
        checkAuthority(user, category.getStore());
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDto> getCategoriesByStore(Long storeId) {
        List<Category> categories = categoryRepository.findByStoreId(storeId);
        return categories.stream().map(CategoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto dto) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new Exception("category doesn't exist")
        );
        User user = userService.getCurrentUser();
        category.setName(dto.getName());
        checkAuthority(user, category.getStore());

        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new Exception("category doesn't exist")
        );
        User user = userService.getCurrentUser();
        checkAuthority(user, category.getStore());
        categoryRepository.delete(category);
    }

    private void checkAuthority(User user, Store store) throws Exception {
        boolean isAdmin = user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
        boolean isManager = user.getRole().equals(UserRole.ROLE_BRANCH_MANAGER);
        boolean isSameStore = user.equals(store.getStoreAdmin());

        if (!(isAdmin && isSameStore) && !isManager) {
            throw new Exception("You dont have permission to manage this category");
        }
    }
}
