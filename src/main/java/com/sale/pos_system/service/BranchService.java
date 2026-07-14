package com.sale.pos_system.service;

import com.sale.pos_system.exceptions.UserException;
import com.sale.pos_system.model.User;
import com.sale.pos_system.payload.dto.BranchDto;

import java.util.List;

public interface BranchService {

    BranchDto createBranch(BranchDto branchDto) throws UserException;
    BranchDto updateBranch(Long id, BranchDto branchDto) throws Exception;
    void deleteBranch(Long id) throws Exception;
    List<BranchDto> getAllBranchesByStoreId(Long storeId);
    BranchDto getBranchById(Long id) throws Exception;
}
