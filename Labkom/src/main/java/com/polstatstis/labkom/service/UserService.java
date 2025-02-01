package com.polstatstis.labkom.service;

import com.polstatstis.labkom.dto.UserDto;

public interface UserService {
    void register(UserDto userDto);

    boolean authenticate(UserDto userDto);

    UserDto getUserProfile(String username);

    void editUserProfile(String username, UserDto userDto);

    String getUserRole(String username);
}
