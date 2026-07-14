package com.sale.pos_system.service;

import com.sale.pos_system.domain.StoreStatus;
import com.sale.pos_system.exceptions.UserException;
import com.sale.pos_system.model.Store;
import com.sale.pos_system.model.User;
import com.sale.pos_system.payload.dto.StoreDto;

import java.util.List;

public interface StoreService {

    StoreDto createStore(StoreDto storeDto, User user);
    StoreDto getStoreById(Long id) throws Exception;
    List<StoreDto> getAllStore();
    Store getStoreByAdmin() throws UserException;
    StoreDto updateStore(Long id, StoreDto storeDto) throws Exception;
    void deleteStore(Long id) throws UserException;
    StoreDto getStoreByEmployee() throws UserException;

    StoreDto moderateStore(Long id, StoreStatus storeStatus) throws Exception;
}
