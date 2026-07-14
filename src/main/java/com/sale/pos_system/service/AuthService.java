package com.sale.pos_system.service;

import com.sale.pos_system.exceptions.UserException;
import com.sale.pos_system.payload.dto.UserDto;
import com.sale.pos_system.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse signup(UserDto userDto) throws UserException;
    AuthResponse login(UserDto userDto) throws UserException;
}
