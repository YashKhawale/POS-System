package com.sale.pos_system.service;

import com.sale.pos_system.exceptions.UserException;
import com.sale.pos_system.model.User;

import java.util.List;

public interface UserService {

    User getUserFromJwtToken(String token) throws UserException;
    User getCurrentUser() throws UserException;
    User getUserByEmail(String email) throws UserException;
    User getUserById(Long id);
    List<User> getAllUsers();
}
