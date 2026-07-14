package com.sale.pos_system.controller;

import com.sale.pos_system.mapper.RefundMapper;
import com.sale.pos_system.payload.dto.RefundDto;
import com.sale.pos_system.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refunds")
public class RefundController {

    private final RefundService refundService;

    @PostMapping()
    public ResponseEntity<RefundDto> createRefund(@RequestBody RefundDto refundDto) throws Exception {
        RefundDto refund = refundService.createRefund(refundDto);
        return ResponseEntity.ok(refund);
    }

    @GetMapping()
    public ResponseEntity<List<RefundDto>> getAllRefund() throws Exception {
        List<RefundDto> refunds = refundService.getAllRefunds();
        return ResponseEntity.ok(refunds);
    }

    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<RefundDto>> getRefundByCashierId(@PathVariable Long cashierId) throws Exception {
        List<RefundDto> refund = refundService.getRefundByCashier(cashierId);
        return ResponseEntity.ok(refund);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<RefundDto>> getRefundByBranchId(@PathVariable Long branchId) throws Exception {
        List<RefundDto> refund = refundService.getRefundByBranch(branchId);
        return ResponseEntity.ok(refund);
    }

    @GetMapping("/shift/{shiftReportId}")
    public ResponseEntity<List<RefundDto>> getRefundByShiftId(@PathVariable Long shiftReportId) throws Exception {
        List<RefundDto> refund = refundService.getRefundByShiftReport(shiftReportId);
        return ResponseEntity.ok(refund);
    }

    @GetMapping("/cashier/{cashierId}/range")
    public ResponseEntity<List<RefundDto>> getRefundByCashierAndDateRange(
            @PathVariable Long cashierId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) throws Exception {
        List<RefundDto> refund = refundService.getRefundByCashierAndDateRange(cashierId, startDate, endDate);
        return ResponseEntity.ok(refund);
    }

    @GetMapping("/{refundId}")
    public ResponseEntity<RefundDto> getRefundById(@PathVariable Long refundId) throws Exception {
        RefundDto refund = refundService.getRefundById(refundId);
        return ResponseEntity.ok(refund);
    }

}
