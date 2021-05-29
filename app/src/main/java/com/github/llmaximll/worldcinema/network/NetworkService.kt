package com.github.llmaximll.worldcinema.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://cinema.areas.su/"

class NetworkService {
    private var mRetrofit: Retrofit? = null
    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getJsonApi(): ServerApi {
        return mRetrofit!!.create(ServerApi::class.java)
    }

    companion object {
        private var mInstance: NetworkService? = null
        val instance: NetworkService?
            get() {
                if (mInstance == null) {
                    mInstance = NetworkService()
                }
                return mInstance
            }
    }
}