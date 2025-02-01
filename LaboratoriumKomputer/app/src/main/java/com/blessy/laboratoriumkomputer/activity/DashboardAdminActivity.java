package com.blessy.laboratoriumkomputer.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blessy.laboratoriumkomputer.R;
import com.blessy.laboratoriumkomputer.adapter.KomputerAdapter;
import com.blessy.laboratoriumkomputer.model.Komputer;
import com.blessy.laboratoriumkomputer.api.ApiClient;
import com.blessy.laboratoriumkomputer.api.ApiService;
import com.blessy.laboratoriumkomputer.utils.SharedPrefs;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardAdminActivity extends AppCompatActivity {

    private SharedPrefs sharedPrefs;
    private RecyclerView recyclerView;
    private KomputerAdapter komputerAdapter;
    private EditText cariKomputerEditText;  // EditText untuk pencarian

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        sharedPrefs = new SharedPrefs(this);

        // Inisialisasi RecyclerView dan Adapter
        recyclerView = findViewById(R.id.recyclerViewKomputer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        komputerAdapter = new KomputerAdapter(this);
        recyclerView.setAdapter(komputerAdapter);

        // EditText untuk pencarian
        cariKomputerEditText = findViewById(R.id.cari_komputer_edit_text);

        // Ambil token dari SharedPrefs
        String token = sharedPrefs.getToken();
        if (token != null) {
            getKomputer(token);
        } else {
            Toast.makeText(this, "Anda perlu login terlebih dahulu", Toast.LENGTH_SHORT).show();
        }

        // Menambahkan listener untuk tombol "Tambah Komputer"
        Button tambahKomputerButton = findViewById(R.id.tambah_komputer_button);
        tambahKomputerButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardAdminActivity.this, TambahKomputerActivity.class);
            startActivity(intent);
        });

        // Menambahkan TextWatcher untuk pencarian real-time
        cariKomputerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() > 2) {  // Mulai pencarian setelah 3 karakter
                    cariKomputer(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void getKomputer(String token) {
        ApiService apiService = ApiClient.getApiService();
        apiService.getKomputer("Bearer " + token).enqueue(new Callback<List<Komputer>>() {
            @Override
            public void onResponse(Call<List<Komputer>> call, Response<List<Komputer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Komputer> komputerList = response.body();
                    komputerAdapter.setKomputerList(komputerList);
                    Toast.makeText(DashboardAdminActivity.this, "Data komputer berhasil dimuat", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DashboardAdminActivity.this, "Gagal memuat data komputer", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Komputer>> call, Throwable t) {
                Toast.makeText(DashboardAdminActivity.this, "Terjadi kesalahan saat memuat data komputer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Fungsi untuk mencari komputer
    private void cariKomputer(String keyword) {
        ApiService apiService = ApiClient.getApiService();
        String token = sharedPrefs.getToken();

        if (token != null) {
            apiService.cariKomputer("Bearer " + token, keyword).enqueue(new Callback<List<Komputer>>() {
                @Override
                public void onResponse(Call<List<Komputer>> call, Response<List<Komputer>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Komputer> komputerList = response.body();
                        komputerAdapter.setKomputerList(komputerList);
                    } else {
                        Log.e("CariKomputer", "Response gagal: " + response.code() + " - " + response.message());
                        try {
                            Log.e("CariKomputer", "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(DashboardAdminActivity.this, "Pencarian gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Komputer>> call, Throwable t) {
                    Toast.makeText(DashboardAdminActivity.this, "Terjadi kesalahan saat pencarian komputer", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(DashboardAdminActivity.this, "Token tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }
}
