package com.github.llmaximll.worldcinema.network

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ServerApi {
    @POST("auth/register")
    fun signUp(@Query("email") email: String,
               @Query("password") password: String,
               @Query("firstName") firstName: String,
               @Query("lastName") lastName: String):
            Call<String>
}