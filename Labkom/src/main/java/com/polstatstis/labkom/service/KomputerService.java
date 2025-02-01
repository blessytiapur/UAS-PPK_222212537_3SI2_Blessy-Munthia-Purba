package com.polstatstis.labkom.service;

import com.polstatstis.labkom.dto.KomputerDto;
import java.util.List;

public interface KomputerService {
    void tambahKomputer(KomputerDto komputerDto);

    List<KomputerDto> getKomputer();

    KomputerDto getKomputerById(Long id);

    List<KomputerDto> cariKomputer(String keyword);

    KomputerDto editKomputer(Long id, KomputerDto komputerDto);

}
