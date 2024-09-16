package com.umda.poetryapp.Api;

import com.umda.poetryapp.Response.DeletePoetryResponse;
import com.umda.poetryapp.Response.GetPoetryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    //Define API endpoints here and their corresponding methods
    @GET("getpoetry.php")
    Call<GetPoetryResponse> getPoetry(); //Call defines what type of response is expected from the server

    //Add other API methods here
    @FormUrlEncoded
    @POST("deletepoetry.php")
    Call<DeletePoetryResponse> deletePoetry(@Field("poetry_id") String poetry_id);

    @FormUrlEncoded
    @POST("addpoetry.php")
    Call<DeletePoetryResponse> addPoetry(@Field("poetry") String poetry, @Field("poet_name") String poet_name);

    @FormUrlEncoded
    @POST("updatepoetry.php")
    Call<DeletePoetryResponse> updatePoetry(@Field("poetry_data") String poetryData, @Field("id") String id);
}