package com.sale.pos_system.controller;

import com.sale.pos_system.domain.UserRole;
import com.sale.pos_system.mapper.UserMapper;
import com.sale.pos_system.model.User;
import com.sale.pos_system.payload.dto.UserDto;
import com.sale.pos_system.payload.response.ApiResponse;
import com.sale.pos_system.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/store/{storeId}")
    public ResponseEntity<UserDto> createStoreEmployee(@PathVariable Long storeId,
                                                       @RequestBody UserDto userDto) throws Exception {
        UserDto employee = employeeService.createStoreEmployee(userDto, storeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/branch/{branchId}")
    public ResponseEntity<UserDto> createBranchEmployee(@PathVariable Long branchId,
                                                       @RequestBody UserDto userDto) throws Exception {
        UserDto employee = employeeService.createBranchEmployee(userDto, branchId);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateEmployee(@PathVariable Long id,
                                                  @RequestBody UserDto userDto) throws Exception {
        User employee = employeeService.updateEmployee(id, userDto);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable Long id) throws Exception {
        employeeService.deleteEmployee(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Employee deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<UserDto>> findStoreEmployees(@PathVariable Long storeId,
                                               @RequestParam(required = false) UserRole userRole) throws Exception {
        List<UserDto> employees = employeeService.findStoreEmployees(storeId, userRole);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<UserDto>> findBranchEmployees(@PathVariable Long branchId,
                                                         @RequestParam(required = false) UserRole userRole) throws Exception {
        List<UserDto> employees = employeeService.findBranchEmployees(branchId, userRole);
        return ResponseEntity.ok(employees);
    }

}
