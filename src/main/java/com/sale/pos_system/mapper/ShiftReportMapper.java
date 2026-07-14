package com.sale.pos_system.mapper;

import com.sale.pos_system.model.Order;
import com.sale.pos_system.model.Product;
import com.sale.pos_system.model.Refund;
import com.sale.pos_system.model.ShiftReport;
import com.sale.pos_system.payload.dto.OrderDto;
import com.sale.pos_system.payload.dto.ProductDto;
import com.sale.pos_system.payload.dto.RefundDto;
import com.sale.pos_system.payload.dto.ShiftReportDto;

import java.util.List;
import java.util.stream.Collectors;

public class ShiftReportMapper {

    public static ShiftReportDto toDto(ShiftReport entity) {
        return ShiftReportDto.builder()
                .id(entity.getId())
                .shiftStart(entity.getShiftStart())
                .shiftEnd(entity.getShiftEnd())
                .totalOrders(entity.getTotalOrders())
                .totalRefunds(entity.getTotalRefunds())
                .totalSales(entity.getTotalSales())
                .netSales(entity.getNetSales())
                .cashier(UserMapper.toDTO(entity.getCashier()))
                .cashierId(entity.getCashier().getId())
                .branchId(entity.getBranch().getId())
                .recentOrders(mapOrders(entity.getRecentOrders()))
                .topSellingProducts(mapProducts(entity.getTopSellingProducts()))
                .refunds(mapRefunds(entity.getRefunds()))
                .paymentSummaries(entity.getPaymentSummaries())
                .build();
    }

    private static List<OrderDto> mapOrders(List<Order> recentOrders) {
        if (recentOrders == null || recentOrders.isEmpty()) return null;

        return recentOrders.stream().map(OrderMapper::toDto).collect(Collectors.toList());
    }

    private static List<ProductDto> mapProducts(List<Product> topSellingProducts) {
        if (topSellingProducts == null || topSellingProducts.isEmpty()) return null;

        return topSellingProducts.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    private static List<RefundDto> mapRefunds(List<Refund> refunds) {
        if (refunds == null || refunds.isEmpty()) return  null;

        return refunds.stream().map(RefundMapper::toDto).collect(Collectors.toList());
    }


}
