package com.blessy.laboratoriumkomputer.api;

import com.blessy.laboratoriumkomputer.model.Komputer;
import com.blessy.laboratoriumkomputer.model.User;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("auth/register")
    Call<ResponseBody> register(@Body User user);

    @POST("auth/login")
    Call<ResponseBody> login(@Body User user);

    @GET("komputer")
    Call<List<Komputer>> getKomputer(@Header("Authorization") String token);

    @POST("komputer")  // Sesuaikan dengan endpoint yang sesuai di server Anda
    Call<ResponseBody> tambahKomputer(@Header("Authorization") String token, @Body Komputer komputer);

    @GET("komputer/{id}")
    Call<Komputer> getKomputerById(
            @Header("Authorization") String token, // Token untuk otentikasi
            @Path("id") Long id // ID alumni yang akan diambil
    );

    @PUT("komputer/{id}")
    Call<String> editKomputer(
            @Header("Authorization") String token,
            @Path("id") Long id,
            @Body Komputer komputer
    );

    @GET("komputer/cari")
    Call<List<Komputer>> cariKomputer(@Header("Authorization") String authorization, @Query("keyword") String keyword);

    @GET("auth/profile")
    Call<User> getProfile(@Header("Authorization") String token); // Menambahkan header untuk token

    @PUT("auth/profile/edit")
    Call<User> editProfile(@Header("Authorization") String token, @Body User user);

    @DELETE("auth/profile/delete")
    Call<Void> deleteAccount(@Header("Authorization") String token, @Body User user);
}
