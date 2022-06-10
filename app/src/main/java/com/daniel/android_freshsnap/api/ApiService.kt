package com.daniel.android_freshsnap.api

import com.daniel.android_freshsnap.api.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @FormUrlEncoded
    @POST("user")
    fun getRegist(
        @Field("name") name :String,
        @Field("email") email : String,
        @Field("password") password : String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun getLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("home-page")
    fun getFruit(
    ): Call<HomeResponse>

    @GET("detail/{id}")
    fun getDetail(
        @Path("id") id: Int?
    ): Call<DetailResponse>

    @Multipart
    @POST("history")
    fun upload(
        //@Header("Authorization") token: String,
        //@Field("email") email: String,
        //@Field("password") password: String,
        @Part file: MultipartBody.Part,
        @Part("location") location: RequestBody
    ): Call<ReviewResponse>
}