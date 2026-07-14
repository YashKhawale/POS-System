package com.sale.pos_system.service;

import com.sale.pos_system.payload.dto.ShiftReportDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ShiftReportService {

    ShiftReportDto startShift() throws Exception;

    ShiftReportDto endShift(Long shiftReportId, LocalDateTime shiftEnd) throws Exception;

    ShiftReportDto getShiftReportById(Long id) throws Exception;

    List<ShiftReportDto> getAllShiftReports();

    List<ShiftReportDto> getShiftReportsByCashierId(Long cashierId);

    List<ShiftReportDto> getShiftReportsByBranchId(Long branchId);

    ShiftReportDto getCurrentShiftProgress(Long cashierId) throws Exception;

    ShiftReportDto getShiftByCashierAndDate(Long cashierId, LocalDateTime date) throws Exception;

}
