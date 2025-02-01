package com.polstatstis.labkom.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * @author blessy
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KomputerDto {

    private Long id;

    @NotEmpty(message = "ruangKomputer wajib diisi!")
    private String ruangKomputer;  // Menyesuaikan dengan ruangKomputer di Komputer entity

    @NotEmpty(message = "ipAddress wajib diisi!")
    private String ipAddress;  // Menyesuaikan dengan ipAddress di Komputer entity

    @NotNull(message = "tanggalBeli wajib diisi!")
    private LocalDate tanggalBeli;  // Menyesuaikan dengan tanggalBeli di Komputer entity

    @NotNull(message = "garansi harus diisi!")
    private Boolean garansi;  // Menyesuaikan dengan garansi di Komputer entity

    @NotNull(message = "pemeliharaanTerakhir wajib diisi!")
    private LocalDate pemeliharaanTerakhir;  // Menyesuaikan dengan pemeliharaanTerakhir di Komputer entity

    @NotEmpty(message = "kondisi wajib diisi!")
    private String kondisi;  // Menyesuaikan dengan kondisi di Komputer entity
}
