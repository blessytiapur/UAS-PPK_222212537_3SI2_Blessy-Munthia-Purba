package com.polstatstis.labkom.controller;

import com.polstatstis.labkom.dto.KomputerDto;
import com.polstatstis.labkom.service.KomputerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/komputer")
public class KomputerController {

    @Autowired
    private KomputerService komputerService;

    public KomputerController() {
    }

    @Operation(summary = "Tambah data komputer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Data komputer berhasil ditambahkan",
                    content = {@Content(mediaType = "application/json")})
    })
    @PostMapping
    public ResponseEntity<String> tambahKomputer(@RequestBody KomputerDto komputerDto) {
        komputerService.tambahKomputer(komputerDto);
        return ResponseEntity.ok("Data komputer berhasil ditambahkan");
    }

    @Operation(summary = "Dapatkan semua data komputer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Berhasil mendapatkan data komputer",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping
    public ResponseEntity<List<KomputerDto>> getKomputer() {
        List<KomputerDto> komputers = komputerService.getKomputer();
        return ResponseEntity.ok(komputers);
    }

    @Operation(summary = "Dapatkan data komputer berdasarkan ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Berhasil mendapatkan data komputer",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Komputer tidak ditemukan",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getKomputerById(@PathVariable Long id) {
        try {
            KomputerDto komputerDto = komputerService.getKomputerById(id);
            return ResponseEntity.ok(komputerDto);
        } catch (RuntimeException var3) {
            return ResponseEntity.status(404).body("Komputer dengan ID " + id + " tidak ditemukan");
        }
    }

    @Operation(summary = "Cari komputer berdasarkan keyword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Berhasil mendapatkan data komputer",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Keyword tidak valid",
                    content = @Content(mediaType = "application/json"))})
    @GetMapping("/cari")
    public ResponseEntity<?> cariKomputer(@RequestParam String keyword) {
        try {
            List<KomputerDto> foundKomputer = komputerService.cariKomputer(keyword);
            return ResponseEntity.ok(foundKomputer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Kesalahan: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Terjadi kesalahan: " + e.getMessage());
        }
    }

    @Operation(summary = "Edit data komputer berdasarkan ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Data komputer berhasil diperbarui",
                    content = {@Content(mediaType = "application/json")})
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> editKomputer(
            @PathVariable Long id,
            @RequestBody KomputerDto komputerDto) {
        komputerService.editKomputer(id, komputerDto);
        return ResponseEntity.ok("Data komputer berhasil diperbarui");
    }

}
