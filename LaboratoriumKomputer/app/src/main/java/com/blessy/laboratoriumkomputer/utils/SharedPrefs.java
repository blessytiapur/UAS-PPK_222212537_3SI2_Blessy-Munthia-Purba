package com.blessy.laboratoriumkomputer.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private final SharedPreferences prefs;

    // Konstruktor yang menerima context untuk akses SharedPreferences
    public SharedPrefs(Context context) {
        // Menyimpan SharedPreferences dengan nama 'user_prefs' dan mode PRIVATE
        this.prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
    }

    // Menyimpan role pengguna (misalnya admin, user, dll)
    public void saveUserRole(String role) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("role", role);
        editor.apply();  // Gunakan apply() agar tidak terjadi blocking operasi
    }

    // Mendapatkan role pengguna, default null jika tidak ditemukan
    public String getUserRole() {
        return prefs.getString("role", null); // null sebagai default
    }

    // Menyimpan token autentikasi (misalnya token JWT)
    public void saveToken(String token) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("auth_token", token); // Menyimpan token dengan key 'auth_token'
        editor.apply();  // Gunakan apply() agar data tersimpan tanpa blocking UI thread
    }

    public void saveLoginStatus(boolean status) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_logged_in", status);
        editor.apply();
    }



    // Mendapatkan token autentikasi, jika tidak ada mengembalikan null
    public String getToken() {
        return prefs.getString("auth_token", null); // Mengambil token, null jika tidak ditemukan
    }

    // Menyediakan method untuk memeriksa apakah token ada atau tidak
    public boolean isTokenExists() {
        return getToken() != null;
    }
}