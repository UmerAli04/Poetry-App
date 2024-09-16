package com.umda.poetryapp.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //Making Retrofit object and call different methods
    public static Retrofit RETROFIT = null;

    public static Retrofit getClient() {
        //Creating Retrofit Object with base url and client object
        if (RETROFIT == null) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            Gson gson = new GsonBuilder().create();
            RETROFIT = new Retrofit.Builder()
                    .baseUrl("http://192.168.100.12/poetryapi/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        //Returning Retrofit Object
        return RETROFIT;
    }
}
