package com.polstatstis.labkom.dto;

/**
 * @author blessy
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String password;

    private String role; // "MAHASISWA", "ADMIN", "PENGAMAT"
}
