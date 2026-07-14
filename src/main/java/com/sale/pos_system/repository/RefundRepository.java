package com.sale.pos_system.repository;

import com.sale.pos_system.model.Refund;
import com.sale.pos_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, Long> {

    List<Refund> findByCashierIdAndCreatedAtBetween(Long cashierId,
                                                  LocalDateTime from,
                                                  LocalDateTime to);

    List<Refund> findByCashierId(Long cashierId);

    List<Refund> findByShiftReportId(Long shiftReportId);

    List<Refund> findByBranchId(Long branchId);

}
