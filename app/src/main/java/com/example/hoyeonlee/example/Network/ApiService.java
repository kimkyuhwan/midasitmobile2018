package com.example.hoyeonlee.example.Network;

import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.DataSchema.Menus;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by hoyeonlee on 2018. 3. 17..
 */

public interface ApiService {
    public static final String URL = "http://192.168.0.60:8000/";
    public static final String subURL = "/api/v1/";

    @POST(subURL+"registration/")
    Call<ResponseBody> signUp(@Body RequestBody body);

    @POST(subURL+"login/")
    Call<ResponseBody> signIn(@Body RequestBody body);
//    Call<ResponseBody> signIn(@Field("username") String email, @Field("password") String password);

    @GET(subURL+"test/")
    Call<ResponseBody> test();

    @GET(subURL+"menus/")
    Call<ArrayList<Menu>> getMenu();

    @POST(subURL+"menus/")
    Call<Menu> addMenu(@Body RequestBody body);

    @DELETE(subURL+"menus/{id}/")
    Call<Menu> deleteMenu(@Path("id") String id);

    @Headers({"Content-Type:text/xml"})
    @PUT(subURL+"menus/{id}/")
    Call<Menu> updateMenu(@Path("id") String id,@Body RequestBody body);

}
