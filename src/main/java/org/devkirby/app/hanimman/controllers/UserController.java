package org.devkirby.app.hanimman.controllers;

import java.util.List;
import java.util.Optional;

import org.devkirby.app.hanimman.dto.UserDTO;
import org.devkirby.app.hanimman.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateRequest request) {
        UserDTO savedUser = userService.save(request.username, request.androidId, request.token);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long userId) {
        Optional<UserDTO> userDTO = userService.getUserById(userId);
        return userDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers();
        return ResponseEntity.ok(userDTOs);
    }

    @PutMapping("/token")
    public ResponseEntity<String> updateToken(@RequestBody TokenUpdateRequest request) {
        boolean updated = userService.updateTokenByAndroidId(request.getAndroidId(), request.getNewToken());
        if (updated) {
            return ResponseEntity.ok("Token updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @Data
    public static class TokenUpdateRequest {
        private String androidId;
        private String newToken;
    }

    @Data
    public static class UserCreateRequest {
        private String username;
        private String androidId;
        private String token;
    }
}
