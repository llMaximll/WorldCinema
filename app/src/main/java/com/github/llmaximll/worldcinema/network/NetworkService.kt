package com.github.llmaximll.worldcinema.network

import com.github.llmaximll.worldcinema.common.CommonFunctions
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://cinema.areas.su/"

class NetworkService(token: String?) {
    private val cf = CommonFunctions.get()
    private var mRetrofit: Retrofit? = null
    init {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request = chain?.request()?.newBuilder()?.addHeader(
                "Authorization",
                "Bearer ${token ?: ""}"
            )?.build()
            chain?.proceed(request)!!
        }
        cf.log("NetworkService", "NetworkService | token=$token")
        val gson = GsonBuilder()
            .setLenient()
            .create()
        mRetrofit = Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create(gson))
            client(httpClient.build())
        }.build()
    }

    fun getJsonApi(): ServerApi {
        return mRetrofit!!.create(ServerApi::class.java)
    }

    companion object {
        private var mInstance: NetworkService? = null
        fun getInstance(token: String?): NetworkService? {
            if (mInstance == null || token != null) {
                mInstance = NetworkService(token)
            }
            return mInstance
        }
    }
}