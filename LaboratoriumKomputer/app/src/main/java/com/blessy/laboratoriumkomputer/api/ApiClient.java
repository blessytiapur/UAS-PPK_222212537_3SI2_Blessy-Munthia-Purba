package com.blessy.laboratoriumkomputer.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.0.114:8088/";
    private static Retrofit retrofit;

    // Menyediakan instance Retrofit
    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient() // Mengizinkan parsing JSON lebih longgar
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    // Menyediakan ApiService untuk digunakan di bagian lain aplikasi
    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }

}

