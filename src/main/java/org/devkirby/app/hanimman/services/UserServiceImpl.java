package org.devkirby.app.hanimman.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.devkirby.app.hanimman.dto.UserDTO;
import org.devkirby.app.hanimman.entities.User;
import org.devkirby.app.hanimman.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO save(String username, String androidId, String token) {
        User user = new User(null, username, androidId, token, LocalDateTime.now(), LocalDateTime.now());
        User saveduser = userRepository.save(user);
        return modelMapper.map(saveduser, UserDTO.class);
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> modelMapper.map(value, UserDTO.class));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

    @Override
    public boolean updateTokenByAndroidId(String androidId, String newToken) {
        Optional<User> userOptional = userRepository.findByAndroidId(androidId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setToken(newToken);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
