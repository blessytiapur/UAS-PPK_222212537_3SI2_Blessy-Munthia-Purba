package com.blessy.laboratoriumkomputer.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.blessy.laboratoriumkomputer.R;
import com.blessy.laboratoriumkomputer.model.Komputer;
import com.blessy.laboratoriumkomputer.api.ApiClient;
import com.blessy.laboratoriumkomputer.api.ApiService;
import com.blessy.laboratoriumkomputer.utils.SharedPrefs;
import java.io.IOException;
import java.time.LocalDate;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahKomputerActivity extends AppCompatActivity {
    private static final String TAG = "TambahKomputerActivity";

    private EditText ruangKomputerEditText, ipAddressEditText, tanggalBeliEditText, garansiEditText, pemeliharaanTerakhirEditText, kondisiEditText;
    private Button saveButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_komputer);

        // Inisialisasi input field
        ruangKomputerEditText = findViewById(R.id.ruang_komputer);
        ipAddressEditText = findViewById(R.id.ip_address);
        tanggalBeliEditText = findViewById(R.id.tanggal_beli);
        garansiEditText = findViewById(R.id.garansi);
        pemeliharaanTerakhirEditText = findViewById(R.id.pemeliharaan_terakhir);
        kondisiEditText = findViewById(R.id.kondisi);

        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(v -> tambahKomputer());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void tambahKomputer() {
        if (!validateInputs()) {
            return;
        }

        Komputer komputer = new Komputer();
        komputer.setRuangKomputer(ruangKomputerEditText.getText().toString().trim());
        komputer.setIpAddress(ipAddressEditText.getText().toString().trim());

        // Parsing tanggal beli dan pemeliharaan terakhir
        try {
            LocalDate tanggalBeli = LocalDate.parse(tanggalBeliEditText.getText().toString().trim());
            komputer.setTanggalBeli(tanggalBeli);
        } catch (Exception e) {
            Toast.makeText(this, "Tanggal beli tidak valid!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            LocalDate pemeliharaanTerakhir = LocalDate.parse(pemeliharaanTerakhirEditText.getText().toString().trim());
            komputer.setPemeliharaanTerakhir(pemeliharaanTerakhir);
        } catch (Exception e) {
            Toast.makeText(this, "Tanggal pemeliharaan terakhir tidak valid!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parsing garansi sebagai Boolean
        String garansiText = garansiEditText.getText().toString().trim();
        komputer.setGaransi("MASIH".equalsIgnoreCase(garansiText)); // Asumsikan input "MASIH" atau "TIDAK"

        komputer.setKondisi(kondisiEditText.getText().toString().trim());

        SharedPrefs sharedPrefs = new SharedPrefs(this);
        String token = sharedPrefs.getToken();

        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token tidak ditemukan. Harap login terlebih dahulu.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Kirim data komputer dengan Bearer token untuk otentikasi
        Call<ResponseBody> call = apiService.tambahKomputer("Bearer " + token, komputer);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String rawResponse = response.body().string();
                        Log.d("RAW_RESPONSE", rawResponse); // Log respons mentah
                        // Anda bisa mengolah respons lebih lanjut jika perlu
                        Toast.makeText(TambahKomputerActivity.this, "Komputer berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("ERROR", "Server error: " + response.code());
                        Toast.makeText(TambahKomputerActivity.this, "Gagal menambahkan komputer. Coba lagi.", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    Log.e("PARSING_ERROR", "Error parsing response: " + e.getMessage());
                    Toast.makeText(TambahKomputerActivity.this, "Terjadi kesalahan saat memproses respons.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API_FAILURE", "onFailure: " + t.getMessage());
                Toast.makeText(TambahKomputerActivity.this, "Gagal menghubungi server. Coba lagi.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInputs() {
        if (ruangKomputerEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ruang komputer tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ipAddressEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "IP Address tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tanggalBeliEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Tanggal beli tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pemeliharaanTerakhirEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Tanggal pemeliharaan terakhir tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (garansiEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Garansi tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (kondisiEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Kondisi komputer tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
