package com.sale.pos_system.model;

import com.sale.pos_system.domain.PaymentType;
import lombok.Data;

@Data
public class PaymentSummary {

    private PaymentType type;
    private Double totalAmount;
    private int transactionCount;
    private double percentage;

}
