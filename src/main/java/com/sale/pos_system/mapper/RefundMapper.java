package com.sale.pos_system.mapper;

import com.sale.pos_system.model.Refund;
import com.sale.pos_system.payload.dto.RefundDto;

public class RefundMapper {

    public static RefundDto toDto(Refund refund) {
        return RefundDto.builder()
                .id(refund.getId())
                .orderId(refund.getOrder().getId())
                .reason(refund.getReason())
                .amount(refund.getAmount())
                .cashierName(refund.getCashier().getFullName())
                .branchId(refund.getBranch().getId())
                .shiftReportId(refund.getShiftReport() != null ? refund.getShiftReport().getId() : null)
                .createdAt(refund.getCreatedAt())
                .build();
    }

}
