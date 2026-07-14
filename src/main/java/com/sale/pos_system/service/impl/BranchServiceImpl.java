package com.sale.pos_system.service.impl;

import com.sale.pos_system.exceptions.UserException;
import com.sale.pos_system.mapper.BranchMapper;
import com.sale.pos_system.model.Branch;
import com.sale.pos_system.model.Store;
import com.sale.pos_system.model.User;
import com.sale.pos_system.payload.dto.BranchDto;
import com.sale.pos_system.repository.BranchRepository;
import com.sale.pos_system.repository.StoreRepository;
import com.sale.pos_system.service.BranchService;
import com.sale.pos_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final UserService userService;


    @Override
    public BranchDto createBranch(BranchDto branchDto) throws UserException {
        User currentUser = userService.getCurrentUser();
        Store store = storeRepository.findByStoreAdminId(currentUser.getId());

        Branch branch = BranchMapper.toEntity(branchDto, store);
        Branch savedBranch = branchRepository.save(branch);
        return BranchMapper.toDto(savedBranch);
    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto branchDto) throws Exception {
        Branch existingBranch = branchRepository.findById(id)
                .orElseThrow(() -> new Exception("Branch not found"));
        existingBranch.setName(branchDto.getName());
        existingBranch.setAddress(branchDto.getAddress());
        existingBranch.setEmail(branchDto.getEmail());
        existingBranch.setPhone(branchDto.getPhone());
        existingBranch.setWorkingDays(branchDto.getWorkingDays());
        existingBranch.setOpenTime(branchDto.getOpenTime());
        existingBranch.setCloseTime(branchDto.getCloseTime());
        existingBranch.setUpdatedAt(LocalDateTime.now());

        Branch updatedBranch = branchRepository.save(existingBranch);
        return BranchMapper.toDto(updatedBranch);
    }

    @Override
    public void deleteBranch(Long id) throws Exception {
        Branch existingBranch = branchRepository.findById(id)
                .orElseThrow(() -> new Exception("Branch not found"));
        branchRepository.delete(existingBranch);
    }

    @Override
    public List<BranchDto> getAllBranchesByStoreId(Long storeId) {
        List<Branch> branches = branchRepository.findByStoreId(storeId);
        return branches.stream().map(BranchMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BranchDto getBranchById(Long id) throws Exception {
        Branch existingBranch = branchRepository.findById(id)
                .orElseThrow(() -> new Exception("Branch not found"));
        return BranchMapper.toDto(existingBranch);
    }
}
