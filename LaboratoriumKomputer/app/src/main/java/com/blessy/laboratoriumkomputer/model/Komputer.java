package com.blessy.laboratoriumkomputer.model;

import java.time.LocalDate;

public class Komputer {

    private Long id;
    private String ruangKomputer;
    private String ipAddress;
    private LocalDate tanggalBeli;
    private Boolean garansi; // "MASIH" or "TIDAK"
    private LocalDate pemeliharaanTerakhir; // Tanggal terakhir pemeliharaan
    private String kondisi; // "Baik", "PERLU PEMELIHARAAN", "RUSAK"

    // Konstruktor kosong
    public Komputer() {
    }

    public Komputer(Long id, String ruangKomputer, String ipAddress, LocalDate tanggalBeli,
                    Boolean garansi, LocalDate pemeliharaanTerakhir, String kondisi) {
        this.id = id;
        this.ruangKomputer = ruangKomputer;
        this.ipAddress = ipAddress;
        this.tanggalBeli = tanggalBeli;
        this.garansi = garansi;
        this.pemeliharaanTerakhir = pemeliharaanTerakhir;
        this.kondisi = kondisi;
    }

    // Getter dan Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuangKomputer() {
        return ruangKomputer;
    }

    public void setRuangKomputer(String ruangKomputer) {
        this.ruangKomputer = ruangKomputer;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDate getTanggalBeli() {
        return tanggalBeli;
    }

    public void setTanggalBeli(LocalDate tanggalBeli) {
        this.tanggalBeli = tanggalBeli;
    }

    public Boolean getGaransi() {
        return garansi;
    }

    public void setGaransi(Boolean garansi) {
        this.garansi = garansi;
    }

    public LocalDate getPemeliharaanTerakhir() {
        return pemeliharaanTerakhir;
    }

    public void setPemeliharaanTerakhir(LocalDate pemeliharaanTerakhir) {
        this.pemeliharaanTerakhir = pemeliharaanTerakhir;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }
}
