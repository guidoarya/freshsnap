package com.daniel.android_freshsnap.api

import com.daniel.android_freshsnap.api.response.DetailResponse
import com.daniel.android_freshsnap.api.response.HomeResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @GET("home-page")
    fun getFruit(
    ): Call<HomeResponse>

    @GET("detail/{id}")
    fun getDetail(
        @Path("id") id: Int?
    ): Call<DetailResponse>
}