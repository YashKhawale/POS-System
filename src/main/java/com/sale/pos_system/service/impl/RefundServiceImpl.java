package com.sale.pos_system.service.impl;

import com.sale.pos_system.mapper.RefundMapper;
import com.sale.pos_system.model.Branch;
import com.sale.pos_system.model.Order;
import com.sale.pos_system.model.Refund;
import com.sale.pos_system.model.User;
import com.sale.pos_system.payload.dto.RefundDto;
import com.sale.pos_system.repository.OrderRepository;
import com.sale.pos_system.repository.RefundRepository;
import com.sale.pos_system.service.RefundService;
import com.sale.pos_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final RefundRepository refundRepository;

    @Override
    public RefundDto createRefund(RefundDto refund) throws Exception {
        User cashier = userService.getCurrentUser();
        Order order = orderRepository.findById(refund.getOrderId())
                .orElseThrow(() -> new Exception("Order not found"));

        Branch branch = order.getBranch();

        Refund createdRefund = Refund.builder()
                .order(order)
                .cashier(cashier)
                .branch(branch)
                .reason(refund.getReason())
                .amount(refund.getAmount())
                .createdAt(refund.getCreatedAt())
                .build();
        Refund savedRefund = refundRepository.save(createdRefund);
        return RefundMapper.toDto(savedRefund);
    }

    @Override
    public List<RefundDto> getAllRefunds() throws Exception {
        return refundRepository.findAll().stream()
                .map(RefundMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RefundDto> getRefundByCashier(Long cashierId) throws Exception {
        return refundRepository.findByCashierId(cashierId).stream()
                .map(RefundMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RefundDto> getRefundByShiftReport(Long shiftReportId) throws Exception {
        return refundRepository.findByShiftReportId(shiftReportId).stream()
                .map(RefundMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RefundDto> getRefundByCashierAndDateRange(Long cashierId,
                                                          LocalDateTime startDate,
                                                          LocalDateTime endDate) throws Exception {
        return refundRepository.findByCashierIdAndCreatedAtBetween(cashierId, startDate, endDate).stream()
                .map(RefundMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RefundDto> getRefundByBranch(Long branchId) throws Exception {
        return refundRepository.findByBranchId(branchId).stream()
                .map(RefundMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public RefundDto getRefundById(Long refundId) throws Exception {
        return refundRepository.findById(refundId).map(RefundMapper::toDto)
                .orElseThrow(() -> new Exception("Refund not found"));
    }

    @Override
    public void deleteRefund(Long refundId) throws Exception {
        this.getRefundById(refundId);
        refundRepository.deleteById(refundId);
    }
}
