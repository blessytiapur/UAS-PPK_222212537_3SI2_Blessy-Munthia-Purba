package com.polstatstis.labkom.entity;

/**
 * @author blessy
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "komputer")
public class Komputer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ruangKomputer;

    @Column(nullable = false)
    private String ipAddress;

    @Column(nullable = false)
    private LocalDate tanggalBeli;

    @Column(nullable = false)
    private Boolean garansi; // "MASIH" or "TIDAK"

    @Column(nullable = false)
    private LocalDate pemeliharaanTerakhir; // "MASIH" or "TIDAK"

    @Column(nullable = false)
    private String kondisi; // "Baik" , "PERLU PEMELIHARAAN" , "RUSAK"

}