package com.sale.pos_system.repository;

import com.sale.pos_system.model.Store;
import com.sale.pos_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findByStore(Store store);

    List<User> findByBranchId(Long branchId);
}
