package com.example.retrofit_get_example_nestedjson;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("5cc008de310000440a035fdf")

    Call<BooksPojo> getBooksfromURL();
}
