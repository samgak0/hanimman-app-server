package org.devkirby.app.hanimman.services;

import java.util.List;
import java.util.Optional;

import org.devkirby.app.hanimman.dto.UserDTO;

public interface UserService {
    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Integer id);

    boolean updateTokenByAndroidId(String androidId, String newToken);

    UserDTO save(String username, String androidId, String token);

}