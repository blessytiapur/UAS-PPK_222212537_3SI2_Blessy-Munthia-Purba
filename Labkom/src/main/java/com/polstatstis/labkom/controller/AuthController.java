package com.polstatstis.labkom.controller;

/**
 * @author blessy
 */

import com.polstatstis.labkom.dto.JwtResponse;
import com.polstatstis.labkom.dto.UserDto;
import com.polstatstis.labkom.security.JwtUtils;
import com.polstatstis.labkom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;

    @PostMapping({"/login"})
    public ResponseEntity<JwtResponse> login(@RequestBody UserDto userDto) {
        if (userService.authenticate(userDto)) {
            String role = userService.getUserRole(userDto.getUsername());
            String token = jwtUtils.generateJwtToken(userDto.getUsername(), role);
            return ResponseEntity.ok(new JwtResponse(token, role));
        } else {
            return ResponseEntity.status(401).body((JwtResponse) null);
        }
    }

    @PostMapping({"/register"})
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        userService.register(userDto);
        return ResponseEntity.ok("Registrasi berhasil! Silahkan login ulang dengan Akun yang sudah terdaftar!");
    }

    @GetMapping({"/profile"})
    public ResponseEntity<UserDto> getProfile(Authentication authentication) {
        String username = authentication.getName();
        UserDto userProfile = userService.getUserProfile(username);
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping({"/profile/edit"})
    public ResponseEntity<String> editProfile(@RequestBody UserDto userDto, Authentication authentication) {
        String username = authentication.getName();
        userService.editUserProfile(username, userDto);
        return ResponseEntity.ok("Profil berhasil diedit!");
    }

}
