package com.polstatstis.labkom.service;

import com.polstatstis.labkom.dto.UserDto;
import com.polstatstis.labkom.entity.User;
import com.polstatstis.labkom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .build();
        userRepository.save(user);
    }

    @Override
    public boolean authenticate(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());
        return user != null && passwordEncoder.matches(userDto.getPassword(), user.getPassword());
    }

    @Override
    public UserDto getUserProfile(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return new UserDto();
    }

    @Override
    public void editUserProfile(String username, UserDto userDto) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public String getUserRole(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user.getRole(); // Ambil role dari entitas User
    }
}



