package com.polstatstis.labkom.mapper;

/**
 * @author blessy
 */

import com.polstatstis.labkom.dto.KomputerDto;
import com.polstatstis.labkom.entity.Komputer;

public class KomputerMapper {

    // Mengonversi entitas Komputer menjadi KomputerDto
    public static KomputerDto mapToKomputerDto(Komputer komputer) {
        return KomputerDto.builder()
                .id(komputer.getId()) // Menyalin id
                .ruangKomputer(komputer.getRuangKomputer()) // Menyalin ruang komputer
                .ipAddress(komputer.getIpAddress()) // Menyalin ipAddress
                .tanggalBeli(komputer.getTanggalBeli()) // Menyalin tanggalBeli
                .garansi(komputer.getGaransi()) // Menyalin garansi
                .pemeliharaanTerakhir(komputer.getPemeliharaanTerakhir()) // Menyalin pemeliharaanTerakhir
                .kondisi(komputer.getKondisi()) // Menyalin kondisi
                .build();
    }

    // Mengonversi KomputerDto menjadi entitas Komputer
    public static Komputer mapToKomputer(KomputerDto komputerDto) {
        return Komputer.builder()
                .id(komputerDto.getId()) // Menyalin id
                .ruangKomputer(komputerDto.getRuangKomputer()) // Menyalin ruang komputer
                .ipAddress(komputerDto.getIpAddress()) // Menyalin ipAddress
                .tanggalBeli(komputerDto.getTanggalBeli()) // Menyalin tanggalBeli
                .garansi(komputerDto.getGaransi()) // Menyalin garansi
                .pemeliharaanTerakhir(komputerDto.getPemeliharaanTerakhir()) // Menyalin pemeliharaanTerakhir
                .kondisi(komputerDto.getKondisi()) // Menyalin kondisi
                .build();
    }
}
