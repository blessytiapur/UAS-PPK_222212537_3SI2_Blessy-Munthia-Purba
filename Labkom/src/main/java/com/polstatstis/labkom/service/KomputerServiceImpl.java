package com.polstatstis.labkom.service;

import com.polstatstis.labkom.dto.KomputerDto;
import com.polstatstis.labkom.entity.Komputer;
import com.polstatstis.labkom.mapper.KomputerMapper;
import com.polstatstis.labkom.repository.KomputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KomputerServiceImpl implements KomputerService {

    @Autowired
    private KomputerRepository komputerRepository;

    @Override
    public void tambahKomputer(KomputerDto komputerDto) {
        Komputer komputer = Komputer.builder()
                .ruangKomputer(komputerDto.getRuangKomputer())
                .ipAddress(komputerDto.getIpAddress()) // Menambahkan ipAddress
                .tanggalBeli(komputerDto.getTanggalBeli()) // Menambahkan tanggalBeli
                .garansi(komputerDto.getGaransi()) // Menambahkan garansi
                .pemeliharaanTerakhir(komputerDto.getPemeliharaanTerakhir()) // Menambahkan pemeliharaanTerakhir
                .kondisi(komputerDto.getKondisi()) // Menambahkan kondisi
                .build();
        komputerRepository.save(komputer);
    }

    @Override
    public List<KomputerDto> getKomputer() {
        List<Komputer> komputers = komputerRepository.findAll();
        return komputers.stream()
                .map(KomputerMapper::mapToKomputerDto)
                .collect(Collectors.toList());
    }

    @Override
    public KomputerDto getKomputerById(Long id) {
        Komputer komputer = komputerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Komputer dengan ID " + id + " tidak ditemukan"));
        return KomputerMapper.mapToKomputerDto(komputer);
    }

    @Override
    public KomputerDto editKomputer(Long id, KomputerDto komputerDto) {
        Komputer existingKomputer = komputerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Komputer dengan ID " + id + " tidak ditemukan"));

        existingKomputer.setRuangKomputer(komputerDto.getRuangKomputer());
        existingKomputer.setIpAddress(komputerDto.getIpAddress()); // Menambahkan ipAddress
        existingKomputer.setTanggalBeli(komputerDto.getTanggalBeli()); // Menambahkan tanggalBeli
        existingKomputer.setGaransi(komputerDto.getGaransi()); // Menambahkan garansi
        existingKomputer.setPemeliharaanTerakhir(komputerDto.getPemeliharaanTerakhir()); // Menambahkan pemeliharaanTerakhir
        existingKomputer.setKondisi(komputerDto.getKondisi()); // Menambahkan kondisi

        Komputer editKomputer = komputerRepository.save(existingKomputer);
        return KomputerMapper.mapToKomputerDto(editKomputer);
    }

    @Override
    public List<KomputerDto> cariKomputer(String keyword) {
        List<Komputer> komputers = komputerRepository.cariKomputerDenganKeyword(keyword);

        if (komputers.isEmpty()) {
            throw new IllegalArgumentException("Tidak ada komputer yang ditemukan dengan keyword: " + keyword);
        }

        return komputers.stream()
                .map(KomputerMapper::mapToKomputerDto)
                .collect(Collectors.toList());
    }
}
