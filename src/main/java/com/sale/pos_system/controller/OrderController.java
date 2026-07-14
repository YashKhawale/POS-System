package com.sale.pos_system.controller;

import com.sale.pos_system.domain.OrderStatus;
import com.sale.pos_system.domain.PaymentType;
import com.sale.pos_system.payload.dto.OrderDto;
import com.sale.pos_system.payload.response.ApiResponse;
import com.sale.pos_system.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) throws Exception {
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long orderId) throws Exception {
        orderService.deleteOrder(orderId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Order deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<OrderDto>> getOrdersByBranchId(@PathVariable Long branchId,
                                                            @RequestParam(required = false) Long customerId,
                                                            @RequestParam(required = false) Long cashierId,
                                                            @RequestParam(required = false) PaymentType paymentType,
                                                            @RequestParam(required = false) OrderStatus status)
            throws Exception {
        return ResponseEntity.ok(orderService.getOrdersByBranch(branchId, customerId, cashierId, paymentType, status));
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<OrderDto>> getOrdersByCashierId(@PathVariable Long cashierId) throws Exception {
        return ResponseEntity.ok(orderService.getOrderByCashier(cashierId));
    }

    @GetMapping("/today/branch/{branchId}")
    public ResponseEntity<List<OrderDto>> getTodayOrders(@PathVariable Long branchId) throws Exception {
        return ResponseEntity.ok(orderService.getTodayOrdersByBranch(branchId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDto>> getCustomerOrders(@PathVariable Long customerId) throws Exception {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }

    @GetMapping("/recent/{branchId}")
    public ResponseEntity<List<OrderDto>> getTop5RecentOrders(@PathVariable Long branchId) throws Exception {
        return ResponseEntity.ok(orderService.getTop5RecentOrdersByBranchId(branchId));
    }

}
