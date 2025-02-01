package com.polstatstis.labkom.repository;

/**
 * @author blessy
 */

import com.polstatstis.labkom.entity.Komputer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

public interface KomputerRepository extends JpaRepository<Komputer, Long> {

    // Query kustom untuk mencari komputer berdasarkan keyword (di ruang atau alamat IP)
    @Query("SELECT k FROM Komputer k WHERE k.ruangKomputer LIKE %?1% OR k.ipAddress LIKE %?1%")
    List<Komputer> cariKomputerDenganKeyword(String keyword);
}

