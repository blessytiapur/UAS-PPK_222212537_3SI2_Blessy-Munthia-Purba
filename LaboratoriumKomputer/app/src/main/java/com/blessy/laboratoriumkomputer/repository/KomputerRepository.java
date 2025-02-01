package com.blessy.laboratoriumkomputer.repository;

import com.blessy.laboratoriumkomputer.model.Komputer;
import com.blessy.laboratoriumkomputer.api.ApiClient;
import com.blessy.laboratoriumkomputer.api.ApiService;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class KomputerRepository {
    private final ApiService apiService;

    public KomputerRepository() {
        // Mendapatkan instance ApiService dari ApiClient
        this.apiService = ApiClient.getApiService();  // Gunakan getApiService()
    }

    // Method untuk mengambil data komputer dengan header token
    public Call<List<Komputer>> getKomputer(String token) {
        return apiService.getKomputer("Bearer " + token);  // Pass token sebagai header Authorization
    }

    // Method untuk menambah data komputer dengan header token
    public Call<ResponseBody> tambahKomputer(String token, Komputer komputer) {
        return apiService.tambahKomputer("Bearer " + token, komputer);  // Pass token sebagai header Authorization
    }

    // Method untuk mencari komputer dengan keyword dan token
    public Call<List<Komputer>> cariKomputers (String token, String keyword) {
        return apiService.cariKomputer("Bearer " + token, keyword);  // Pass token sebagai header Authorization
    }

    // Method untuk memperbarui data komputer dengan ID dan header token
    public Call<String> editKomputer(String token, Long id, Komputer komputer) {
        return apiService.editKomputer("Bearer " + token, id, komputer);  // Gunakan endpoint updateAlumni sesuai API service
    }

}
