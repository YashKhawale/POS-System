package com.sale.pos_system.service.impl;

import com.sale.pos_system.domain.PaymentType;
import com.sale.pos_system.mapper.ShiftReportMapper;
import com.sale.pos_system.model.*;
import com.sale.pos_system.payload.dto.ShiftReportDto;
import com.sale.pos_system.repository.*;
import com.sale.pos_system.service.ShiftReportService;
import com.sale.pos_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftReportServiceImpl implements ShiftReportService {

    private final ShiftReportRepository shiftReportRepository;
    private final UserService userService;
    private final BranchRepository branchRepository;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public ShiftReportDto startShift() throws Exception {
        User currentUser = userService.getCurrentUser();
        LocalDateTime shiftStart = LocalDateTime.now();

        LocalDateTime startOfDay = shiftStart.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = shiftStart.withHour(23).withMinute(59).withSecond(59);

        Optional<ShiftReport> existing = shiftReportRepository.findByCashierAndShiftStartBetween(
                currentUser, startOfDay, endOfDay
        );

        if (existing.isPresent()) {
            throw new Exception("shift already started today");
        }

        Branch branch = currentUser.getBranch();

        ShiftReport newShiftReport = ShiftReport.builder()
                .cashier(currentUser)
                .shiftStart(startOfDay)
                .branch(branch)
                .build();

        ShiftReport savedShiftReport = shiftReportRepository.save(newShiftReport);

        return ShiftReportMapper.toDto(savedShiftReport);
    }

    @Override
    public ShiftReportDto endShift(Long shiftReportId, LocalDateTime shiftEnd) throws Exception {
        User currentUser = userService.getCurrentUser();

        ShiftReport shiftReport = shiftReportRepository.findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(currentUser)
                .orElseThrow(() -> new Exception("Shift not found"));

        shiftReport.setShiftEnd(shiftEnd);

        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                currentUser.getId(), shiftReport.getShiftStart(), shiftReport.getShiftEnd()
        );

        double totalRefunds = refunds.stream()
                .mapToDouble(refund -> refund.getAmount() != null ? refund.getAmount() : 0.0).sum();

        List<Order> orders = orderRepository.findByCashierAndCreatedAtBetween(
                currentUser, shiftReport.getShiftStart(), shiftReport.getShiftEnd()
        );

        double totalSales = orders.stream()
                .mapToDouble(Order::getTotalAmount).sum();

        int totalOrders = orders.size();

        double netSales = totalSales - totalRefunds;

        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSales(netSales);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders, totalSales));
        shiftReport.setRefunds(refunds);

        ShiftReport savedReport = shiftReportRepository.save(shiftReport);

        return ShiftReportMapper.toDto(savedReport);
    }


    @Override
    public ShiftReportDto getShiftReportById(Long id) throws Exception {
        return shiftReportRepository.findById(id)
                .map(ShiftReportMapper::toDto)
                .orElseThrow(() -> new Exception("shift report not found with given id: " + id));
    }

    @Override
    public List<ShiftReportDto> getAllShiftReports() {
        List<ShiftReport> reports = shiftReportRepository.findAll();
        return reports.stream()
                .map(ShiftReportMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDto> getShiftReportsByCashierId(Long cashierId) {
        List<ShiftReport> reports = shiftReportRepository.findByCashierId(cashierId);
        return reports.stream()
                .map(ShiftReportMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ShiftReportDto> getShiftReportsByBranchId(Long branchId) {
        List<ShiftReport> reports = shiftReportRepository.findByBranchId(branchId);
        return reports.stream()
                .map(ShiftReportMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ShiftReportDto getCurrentShiftProgress(Long cashierId) throws Exception {
        User currentUser = userService.getCurrentUser();

        ShiftReport shiftReport = shiftReportRepository.findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(currentUser)
                .orElseThrow(() -> new Exception("no active shift found for cashier"));

        LocalDateTime now = LocalDateTime.now();

        List<Order> orders = orderRepository.findByCashierAndCreatedAtBetween(
                currentUser, shiftReport.getShiftStart(), now
        );

        List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(
                currentUser.getId(), shiftReport.getShiftStart(), now
        );

        double totalRefunds = refunds.stream()
                .mapToDouble(refund -> refund.getAmount() != null ? refund.getAmount() : 0.0).sum();

        double totalSales = orders.stream()
                .mapToDouble(Order::getTotalAmount).sum();

        int totalOrders = orders.size();

        double netSales = totalSales - totalRefunds;

        shiftReport.setTotalRefunds(totalRefunds);
        shiftReport.setTotalSales(totalSales);
        shiftReport.setTotalOrders(totalOrders);
        shiftReport.setNetSales(netSales);
        shiftReport.setRecentOrders(getRecentOrders(orders));
        shiftReport.setTopSellingProducts(getTopSellingProducts(orders));
        shiftReport.setPaymentSummaries(getPaymentSummaries(orders, totalSales));
        shiftReport.setRefunds(refunds);

        ShiftReport savedReport = shiftReportRepository.save(shiftReport);

        return ShiftReportMapper.toDto(savedReport);
    }

    @Override
    public ShiftReportDto getShiftByCashierAndDate(Long cashierId, LocalDateTime date) throws Exception {
        User cashier = userRepository.findById(cashierId)
                .orElseThrow(() -> new Exception("cashier not found with id: " + cashierId));

        LocalDateTime start = date.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = date.withHour(23).withMinute(59).withSecond(59);

        ShiftReport report = shiftReportRepository.findByCashierAndShiftStartBetween(cashier, start, end)
                .orElseThrow(() -> new Exception("shift report not found for date"));

        return ShiftReportMapper.toDto(report);
    }

    // ---------------------------- Helper Methods ------------------------------

    private List<Order> getRecentOrders(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getCreatedAt).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    private List<Product> getTopSellingProducts(List<Order> orders) {
        Map<Product, Integer> productSalesMap = new HashMap<>();

        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                productSalesMap.put(
                        product, productSalesMap.getOrDefault(product, 0) + item.getQuantity());
            }
        }

        return productSalesMap.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<PaymentSummary> getPaymentSummaries(List<Order> orders, double totalSales) {
        // CASH - order 1 (amount = 2000), order 2 (amount = 2000) => 4000
        // CARD - order 3 => 2000
        // UPI - order 4 (amount 2000), order 5 (amount = 2000) => 4000

        // cash = 40%
        // card = 20%
        // upi = 40%


        Map<PaymentType, List<Order>> grouped = orders.stream()
                .collect(Collectors.groupingBy(order -> order.getPaymentType() != null ?
                        order.getPaymentType() : PaymentType.CASH));

        List<PaymentSummary> summaries = new ArrayList<>();

        for(Map.Entry<PaymentType, List<Order>> entry : grouped.entrySet()) {
            double amount = entry.getValue().stream()
                    .mapToDouble(Order::getTotalAmount).sum();
            int transactions = entry.getValue().size();
            double percentage = (amount / totalSales) * 100;

            PaymentSummary paymentSummary = new PaymentSummary();
            paymentSummary.setType(entry.getKey());
            paymentSummary.setTotalAmount(amount);
            paymentSummary.setTransactionCount(transactions);
            paymentSummary.setPercentage(percentage);
            summaries.add(paymentSummary);
        }

        return summaries;
    }
}
