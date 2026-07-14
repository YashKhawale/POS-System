package com.sale.pos_system.controller;

import com.sale.pos_system.exceptions.UserException;
import com.sale.pos_system.mapper.UserMapper;
import com.sale.pos_system.model.User;
import com.sale.pos_system.payload.dto.UserDto;
import com.sale.pos_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new Exception("User not found");
        }
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
}
