package com.polstatstis.labkom.security;

import com.polstatstis.labkom.entity.User;
import com.polstatstis.labkom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    // Tidak perlu autowire passwordEncoder di sini
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Mendefinisikan bean passwordEncoder
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/login").permitAll()  // Membuka register dan login untuk semua
                        .requestMatchers(HttpMethod.POST, "/komputer").hasAuthority("ADMIN") // Admin hanya yang bisa menambah komputer
                        .requestMatchers(HttpMethod.PUT, "/komputer/{id}").hasAuthority("ADMIN") // Admin yang bisa update data komputer
                        .requestMatchers(HttpMethod.GET, "/komputer/{id}").hasAnyAuthority("ADMIN", "MAHASISWA") // Admin dan mahasiswa bisa melihat data komputer
                        .requestMatchers(HttpMethod.GET, "/komputer/cari").hasAnyAuthority("ADMIN", "MAHASISWA") // Admin dan mahasiswa bisa mencari data komputer
                        .requestMatchers(HttpMethod.GET, "/komputer").hasAnyAuthority("ADMIN", "MAHASISWA") // Admin dan mahasiswa bisa melihat semua data komputer
                        .requestMatchers("/auth/profile/**").authenticated() // Harus login untuk mengakses profile
                        .anyRequest().authenticated() // Semua request lainnya harus terautentikasi
                )
                .addFilterBefore(new JwtAuthenticationFilter(this.jwtUtils), UsernamePasswordAuthenticationFilter.class); // Filter untuk JWT authentication

        return http.build();
    }

    // Fungsi untuk memverifikasi password menggunakan BCryptPasswordEncoder
    public boolean authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username); // Cari user berdasarkan username
        if (user != null) {
            return passwordEncoder().matches(rawPassword, user.getPassword()); // Verifikasi password yang dimasukkan
        }
        return false; // Jika user tidak ditemukan
    }
}
