package com.github.llmaximll.worldcinema.network

import com.github.llmaximll.worldcinema.dataclasses.network.EpisodeInfo
import com.github.llmaximll.worldcinema.dataclasses.network.MovieInfo
import com.github.llmaximll.worldcinema.dataclasses.network.PosterInfo
import com.github.llmaximll.worldcinema.dataclasses.network.SignInDC
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ServerApi {
    @POST("auth/register")
    fun signUp(@Query("email") email: String,
               @Query("password") password: String,
               @Query("firstName") firstName: String,
               @Query("lastName") lastName: String):
            Call<String>

    @POST("auth/login")
    fun signIn(@Query("email") email: String,
               @Query("password") password: String):
            Call<SignInDC>

    @GET("movies")
    fun downloadMovies(@Query("filter") filter: String):
            Call<List<MovieInfo>>

    @GET("movies/cover")
    fun downloadInfoPoster():
            Call<PosterInfo>

    @GET("usermovies")
    fun downloadInfoLastView(@Query("filter") filter: String):
            Call<List<MovieInfo>>

    @GET("movies/{movieId}")
    fun downloadInfoRequiredMovie(@Path("movieId") movieId: String):
            Call<MovieInfo>

    @GET("movies/{movieId}/episodes")
    fun downloadInfoEpisodesRequiredMovie(@Path("movieId") movieId: String):
            Call<List<EpisodeInfo>>
}