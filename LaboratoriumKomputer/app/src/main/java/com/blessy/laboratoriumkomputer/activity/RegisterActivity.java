package com.blessy.laboratoriumkomputer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.blessy.laboratoriumkomputer.R;
import com.blessy.laboratoriumkomputer.model.User;
import com.blessy.laboratoriumkomputer.api.ApiClient;
import com.blessy.laboratoriumkomputer.api.ApiService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private String selectedRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Bind views
        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Spinner roleSpinner = findViewById(R.id.role_spinner);
        Button btnRegister = findViewById(R.id.btn_register);

        // Set up spinner data
        String[] roles = {"Pilih Role", "ADMIN", "Mahasiswa", "Pengamat"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        // Handle spinner item selection
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRole = roles[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedRole = null;
            }
        });

        // Set click listener for register button
        btnRegister.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || selectedRole == null || selectedRole.equals("Pilih Role")) {
                Toast.makeText(this, "Semua field harus diisi dengan benar!", Toast.LENGTH_SHORT).show();
            } else {
                // Logic to handle registration by sending data to API
                registerUser(username, password, selectedRole);
            }
        });
    }

    private void registerUser(String username, String password, String role) {
        // Membuat objek User untuk dikirimkan ke API
        User newUser = new User(username, password, role);

        // Mengakses ApiService untuk melakukan registrasi
        ApiService apiService = ApiClient.getApiService();
        Call<ResponseBody> call = apiService.register(newUser);

        // Menangani respons API
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Membaca respons teks dari API
                        String message = response.body().string();
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish(); // Tutup halaman registrasi setelah berhasil
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Kesalahan membaca respons.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Jika registrasi gagal
                    Toast.makeText(RegisterActivity.this, "Registrasi gagal. Coba lagi!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Jika terjadi kesalahan dalam menghubungi API
                Toast.makeText(RegisterActivity.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}