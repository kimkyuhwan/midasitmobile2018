package com.example.hoyeonlee.example.Network;

import com.example.hoyeonlee.example.DataSchema.Menu;
import com.example.hoyeonlee.example.DataSchema.Reservation;

import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
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

    @GET(subURL+"test/")
    Call<ResponseBody> test();

    @GET(subURL+"menus/")
    Call<ArrayList<Menu>> getAllMenus();

    @POST(subURL+"menus/")
    Call<Menu> addMenu(@Body RequestBody body);

    @DELETE(subURL+"menus/{id}/")
    Call<Menu> deleteMenu(@Path("id") String id);

    @PUT(subURL+"menus/{id}/")
    Call<Menu> updateMenu(@Path("id") String id,@Body RequestBody body);

    @GET(subURL+"status/")
    Call<ArrayList<Reservation>> getAllOrders();

    @GET(subURL+"status/?complete=false")
    Call<ArrayList<Reservation>> getUnCompletedOrders(@Body RequestBody body);

    @GET(subURL+"status/?complete=true")
    Call<ArrayList<Reservation>> getCompletedOrders(@Body RequestBody body);

    @PUT(subURL+"status/{id}")
    Call<RequestBody> updateOrder(@Path("id") String id, @Body RequestBody body);

}
