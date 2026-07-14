package com.sale.pos_system.service.impl;

import com.sale.pos_system.mapper.InventoryMapper;
import com.sale.pos_system.model.Branch;
import com.sale.pos_system.model.Inventory;
import com.sale.pos_system.model.Product;
import com.sale.pos_system.payload.dto.InventoryDto;
import com.sale.pos_system.repository.BranchRepository;
import com.sale.pos_system.repository.InventoryRepository;
import com.sale.pos_system.repository.ProductRepository;
import com.sale.pos_system.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    @Override
    public InventoryDto createInventory(InventoryDto inventoryDto) throws Exception {
        Branch branch = branchRepository.findById(inventoryDto.getBranchId())
                .orElseThrow(() -> new Exception("Branch not found"));
        Product product = productRepository.findById(inventoryDto.getProductId())
                .orElseThrow(() -> new Exception("Product not found"));
        Inventory inventory = InventoryMapper.toEntity(inventoryDto, branch, product);
        Inventory savedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toDto(savedInventory);
    }

    @Override
    public InventoryDto updateInventory(Long id, InventoryDto inventoryDto) throws Exception {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Inventory not found"));
        inventory.setQuantity(inventoryDto.getQuantity());
        Inventory updatedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toDto(updatedInventory);
    }

    @Override
    public void deleteInventory(Long id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Inventory not found"));
        inventoryRepository.delete(inventory);
    }

    @Override
    public InventoryDto getInventoryById(Long id) throws Exception {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Inventory not found"));
        return InventoryMapper.toDto(inventory);
    }

    @Override
    public InventoryDto getInventoryByProductIdAndBranchId(Long productId, Long branchId) {
        Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId, branchId);
        return InventoryMapper.toDto(inventory);
    }

    @Override
    public List<InventoryDto> getAllInventoryByBranchId(Long branchId) {
        List<Inventory> inventories = inventoryRepository.findByBranchId(branchId);
        return inventories.stream().map(InventoryMapper::toDto).collect(Collectors.toList());
    }
}
