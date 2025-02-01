package com.blessy.laboratoriumkomputer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blessy.laboratoriumkomputer.R;
import com.blessy.laboratoriumkomputer.api.ApiClient;
import com.blessy.laboratoriumkomputer.api.ApiService;
import com.blessy.laboratoriumkomputer.model.User;
import com.blessy.laboratoriumkomputer.utils.SharedPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, registerButton;
    private SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Use correct R value

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        sharedPrefs = new SharedPrefs(this);

        loginButton.setOnClickListener(v -> loginUser());

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Tolong lengkapi username dan password", Toast.LENGTH_SHORT).show();
            return;
        }

        User loginUser = new User(username, password);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = apiService.login(loginUser);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);

                        String token = jsonObject.optString("token");
                        String role = jsonObject.optString("role");

                        if (token.isEmpty() || role.isEmpty()) { // Check for both token and role
                            Toast.makeText(MainActivity.this, "Token or Role not received", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        sharedPrefs.saveToken(token);
                        sharedPrefs.saveUserRole(role);
                        sharedPrefs.saveLoginStatus(true);

                        Intent intent;
                        if ("ADMIN".equalsIgnoreCase(role)) { // Case-insensitive comparison
                            intent = new Intent(MainActivity.this, DashboardAdminActivity.class);
                        } else {
                            intent = new Intent(MainActivity.this, MainActivity.class); // Or another appropriate activity
                        }
                        startActivity(intent);
                        finish();

                    } catch (IOException | JSONException e) { // Catch both exceptions
                        Toast.makeText(MainActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("MainActivity", "Parsing error: " + e.getMessage());
                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            JSONObject errorJson = new JSONObject(errorBody);
                            String errorMessage = errorJson.optString("message", "Invalid username or password"); // Extract error message
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException | JSONException e) {
                        Toast.makeText(MainActivity.this, "Error reading error body", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Login gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "API error: " + t.getMessage());
            }
        });
    }
}