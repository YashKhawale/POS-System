package com.sale.pos_system.mapper;

import com.sale.pos_system.model.Store;
import com.sale.pos_system.model.User;
import com.sale.pos_system.payload.dto.StoreDto;

public class StoreMapper {

    public static StoreDto toDto(Store store) {
        StoreDto storeDto = new StoreDto();
        storeDto.setId(store.getId());
        storeDto.setStoreAdmin(UserMapper.toDTO(store.getStoreAdmin()));
        storeDto.setStoreType(store.getStoreType());
        storeDto.setBrand(store.getBrand());
        storeDto.setDescription(store.getDescription());
        storeDto.setContact(store.getContact());
        storeDto.setCreatedAt(store.getCreatedAt());
        storeDto.setUpdatedAt(store.getUpdatedAt());
        storeDto.setStatus(store.getStatus());
        return storeDto;
    }

    public static Store toEntity(StoreDto storeDto, User storeAdmin) {
        Store store = new Store();
        store.setId(storeDto.getId());
        store.setBrand(storeDto.getBrand());
        store.setDescription(storeDto.getDescription());
        store.setStoreAdmin(storeAdmin);
        store.setContact(storeDto.getContact());
        store.setCreatedAt(storeDto.getCreatedAt());
        store.setUpdatedAt(storeDto.getUpdatedAt());
        return store;
    }
}
