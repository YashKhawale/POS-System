package com.sale.pos_system.payload.dto;

import com.sale.pos_system.domain.StoreStatus;
import com.sale.pos_system.model.StoreContact;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreDto {

    private Long id;

    private String brand;

    private UserDto storeAdmin;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String description;

    private String storeType;

    private StoreStatus status;

    private StoreContact contact;
}
