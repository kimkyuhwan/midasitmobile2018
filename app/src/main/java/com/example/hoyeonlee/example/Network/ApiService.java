package com.example.hoyeonlee.example.Network;

import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

/**
 * Created by hoyeonlee on 2018. 3. 17..
 */

public interface ApiService {
    public static final String URL = "http://210.89.178.127:8000/";
    public static final String subURL = "/api/v1/";

    @POST(subURL+"registration/")
    Call<ResponseBody> signUp(@Body RequestBody body);

    @POST(subURL+"login/")
    Call<ResponseBody> signIn(@Body RequestBody body);
//    Call<ResponseBody> signIn(@Field("username") String email, @Field("password") String password);

    @GET(subURL+"test/")
    Call<ResponseBody> test();
}
