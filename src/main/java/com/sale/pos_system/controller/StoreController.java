package com.sale.pos_system.controller;

import com.sale.pos_system.domain.StoreStatus;
import com.sale.pos_system.exceptions.UserException;
import com.sale.pos_system.mapper.StoreMapper;
import com.sale.pos_system.model.User;
import com.sale.pos_system.payload.dto.StoreDto;
import com.sale.pos_system.payload.response.ApiResponse;
import com.sale.pos_system.service.StoreService;
import com.sale.pos_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<StoreDto> createStore(@RequestBody StoreDto storeDto,
                                                @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(storeService.createStore(storeDto, user));
    }

    @GetMapping()
    public ResponseEntity<List<StoreDto>> getAllStore(@RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(storeService.getAllStore());
    }

    @GetMapping("/admin")
    public ResponseEntity<StoreDto> getStoreByAdmin(@RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(StoreMapper.toDto(storeService.getStoreByAdmin()));
    }

    @GetMapping("/employee")
    public ResponseEntity<StoreDto> getStoreByEmployee(@RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(storeService.getStoreByEmployee());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDto> updateStore(@PathVariable Long id,
                                                @RequestBody StoreDto storeDto) throws Exception {
        return ResponseEntity.ok(storeService.updateStore(id, storeDto));
    }

    @PutMapping("/{id}/moderate")
    public ResponseEntity<StoreDto> moderateStore(@PathVariable Long id,
                                                @RequestParam StoreStatus storeStatus) throws Exception {
        return ResponseEntity.ok(storeService.moderateStore(id, storeStatus));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable Long id,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStore(@PathVariable Long id) throws Exception {
        storeService.deleteStore(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Successfully deleted store");
        return ResponseEntity.ok(apiResponse);
    }
}
