package com.sale.pos_system.controller;

import com.sale.pos_system.payload.dto.InventoryDto;
import com.sale.pos_system.payload.response.ApiResponse;
import com.sale.pos_system.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping()
    public ResponseEntity<InventoryDto> createInventory(@RequestBody InventoryDto inventoryDto) throws Exception {
        return ResponseEntity.ok(inventoryService.createInventory(inventoryDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDto> updateInventory(@RequestBody InventoryDto inventoryDto,
                                               @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(inventoryService.updateInventory(id, inventoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteInventory(@PathVariable Long id) throws Exception {
        inventoryService.deleteInventory(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Inventory deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDto> getInventoryById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @GetMapping("/branch/{branchId}/product/{productId}")
    public ResponseEntity<InventoryDto> getInventoryByProductAndBranchId(@PathVariable Long productId,
                                                                         @PathVariable Long branchId) throws Exception {
        return ResponseEntity.ok(inventoryService.getInventoryByProductIdAndBranchId(productId, branchId));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<InventoryDto>> getInventoryByBranchId(@PathVariable Long branchId) throws Exception {
        return ResponseEntity.ok(inventoryService.getAllInventoryByBranchId(branchId));
    }
}
