package com.sale.pos_system.payload.dto;

import com.sale.pos_system.domain.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundDto {

    private Long id;

    private OrderDto order;
    private Long orderId;

    private String reason;

    private Double amount;

//    private ShiftReport shiftReport;
    private Long shiftReportId;

    private UserDto cashier;
    private String cashierName;

    private BranchDto branch;
    private Long branchId;

    private PaymentType paymentType;

    private LocalDateTime createdAt;

}
