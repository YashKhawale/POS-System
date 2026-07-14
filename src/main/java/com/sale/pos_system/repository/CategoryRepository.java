package com.sale.pos_system.repository;

import com.sale.pos_system.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByStoreId(Long storeId);

}
