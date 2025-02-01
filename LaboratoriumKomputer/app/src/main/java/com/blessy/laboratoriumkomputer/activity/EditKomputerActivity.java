package com.blessy.laboratoriumkomputer.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.blessy.laboratoriumkomputer.api.ApiClient;
import com.blessy.laboratoriumkomputer.api.ApiService;
import com.blessy.laboratoriumkomputer.model.Komputer;
import com.blessy.laboratoriumkomputer.utils.SharedPrefs;
import com.blessy.laboratoriumkomputer.R;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditKomputerActivity extends AppCompatActivity {

    private EditText editRuangKomputer, editIpAddress, editTanggalBeli, editGaransi, editPemeliharaanTerakhir, editKondisi;
    private Button editKomputerButton;
    private SharedPrefs sharedPrefs;
    private Long komputerId;
    private LocalDate tanggalBeli, pemeliharaanTerakhir;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_komputer);

        // Inisialisasi EditText
        editRuangKomputer = findViewById(R.id.edit_ruang_komputer);
        editIpAddress = findViewById(R.id.edit_ip_address);
        editTanggalBeli = findViewById(R.id.edit_tanggal_beli);
        editGaransi = findViewById(R.id.edit_garansi);
        editPemeliharaanTerakhir = findViewById(R.id.edit_pemeliharaan_terakhir);
        editKondisi = findViewById(R.id.edit_kondisi);

        editKomputerButton = findViewById(R.id.edit_komputer_button);
        sharedPrefs = new SharedPrefs(this);

        // Ambil ID komputer dari Intent
        long komputerId = getIntent().getLongExtra("komputer_id", -1); // Perbaikan 1 // Menggunakan Long untuk ID

        Log.d("EditKomputer", "Komputer ID yang diterima: " + komputerId);

        // Load data komputer berdasarkan ID
        loadKomputerDetails(komputerId);

        // Update data komputer saat tombol diklik
        editKomputerButton.setOnClickListener(v -> editKomputer());
    }

    // Method untuk load data komputer berdasarkan ID
    private void loadKomputerDetails(Long id) {
        String token = sharedPrefs.getToken();
        ApiService apiService = ApiClient.getApiService();
        apiService.getKomputerById("Bearer " + token, id).enqueue(new Callback<Komputer>() {

            @Override
            public void onResponse(Call<Komputer> call, Response<Komputer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Komputer komputer = response.body();

                    // Prefill data ke input form
                    editRuangKomputer.setText(komputer.getRuangKomputer());
                    editIpAddress.setText(komputer.getIpAddress());
                    editTanggalBeli.setText(komputer.getTanggalBeli().toString());
                    editGaransi.setText(komputer.getGaransi() ? "MASIH" : "TIDAK");
                    editPemeliharaanTerakhir.setText(komputer.getPemeliharaanTerakhir().toString());
                    editKondisi.setText(komputer.getKondisi());
                } else {
                    Log.e("LoadKomputer", "Gagal memuat data - Response Code: " + response.code());
                    Log.e("LoadKomputer", "Response Error Body: " + response.errorBody());

                    Toast.makeText(EditKomputerActivity.this, "Gagal memuat data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Komputer> call, Throwable t) {
                Log.e("EditKomputer", "Error: " + t.getMessage());
                Toast.makeText(EditKomputerActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method untuk edit data komputer
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void editKomputer() {
        String token = sharedPrefs.getToken();
        ApiService apiService = ApiClient.getApiService();
        Log.d("EditKomputer", "ID Komputer yang dikirim: " + komputerId);

        // Ambil nilai dari input
        String ruangKomputer = editRuangKomputer.getText().toString().trim();
        String ipAddress = editIpAddress.getText().toString().trim();
        String garansiText = editGaransi.getText().toString().trim();
        String tanggalBeliText = editTanggalBeli.getText().toString().trim();
        String kondisi = editKondisi.getText().toString().trim();
        String pemeliharaanTerakhirText = editPemeliharaanTerakhir.getText().toString().trim();

        // Validasi sebelum edit
        if (ruangKomputer.isEmpty() || ipAddress.isEmpty() || garansiText.isEmpty() ||
                tanggalBeliText.isEmpty() || pemeliharaanTerakhirText.isEmpty() || kondisi.isEmpty()) {
            Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_LONG).show();
            return;
        }

        // Validasi untuk garansi dan pemeliharaan terakhir
        Boolean garansi = "MASIH".equalsIgnoreCase(garansiText);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Perbaikan 2
            tanggalBeli = LocalDate.parse(tanggalBeliText, formatter);
            pemeliharaanTerakhir = LocalDate.parse(pemeliharaanTerakhirText, formatter);
        } catch (DateTimeParseException e) {
            Toast.makeText(this, "Format tanggal tidak valid!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buat objek Komputer yang diperbarui
        Komputer editKomputer = new Komputer(komputerId, ruangKomputer, ipAddress, tanggalBeli, garansi, pemeliharaanTerakhir, kondisi);

        apiService.editKomputer("Bearer " + token, komputerId, editKomputer).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_RESPONSE", response.body()); // Akan menampilkan: "Data komputer berhasil diperbarui"
                    Toast.makeText(EditKomputerActivity.this, "Komputer berhasil diperbarui!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("API_ERROR", "Gagal: " + response.errorBody());
                    Toast.makeText(EditKomputerActivity.this, "Gagal memperbarui komputer. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("API_ERROR", "Failure: " + t.getMessage());
                Toast.makeText(EditKomputerActivity.this, "Gagal menghubungi server. Coba lagi.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
