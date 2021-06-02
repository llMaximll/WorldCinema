package com.github.llmaximll.worldcinema.repositories

import android.content.Context
import com.github.llmaximll.worldcinema.common.CommonFunctions
import com.github.llmaximll.worldcinema.dataclasses.network.MovieInfo
import com.github.llmaximll.worldcinema.dataclasses.network.PosterInfo
import com.github.llmaximll.worldcinema.dataclasses.network.SignInDC
import com.github.llmaximll.worldcinema.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val TAG = "CinemaRepository"

class CinemaRepository private constructor(context: Context) {

    private val cf = CommonFunctions.get()

    suspend fun signUp(token: String?, email: String, password: String, firstName: String, lastName: String): Boolean {
        return suspendCoroutine { cont ->
            NetworkService
                .getInstance(token)
                ?.getJsonApi()
                ?.signUp(email, password, firstName, lastName)
                ?.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        if (response?.isSuccessful == true) {
                            cont.resume(true)
                        } else {
                            cont.resume(false)
                            cf.log(TAG, "response=${response?.message()}")
                        }
                    }

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        cont.resume(false)
                        cf.log(TAG, "result=$t")
                    }
                })
        }
    }

    suspend fun signIn(token: String?, email: String, password: String): SignInDC? {
        return suspendCoroutine { cont ->
            NetworkService
                .getInstance(token)
                ?.getJsonApi()
                ?.signIn(email, password)
                ?.enqueue(object : Callback<SignInDC> {
                    override fun onResponse(call: Call<SignInDC>?,
                                            response: Response<SignInDC>?
                    ) {
                        if (response?.isSuccessful == true) {
                            cont.resume(response.body())
                        } else {
                            cont.resume(null)
                            cf.log(TAG, "response=${response?.body()}")
                        }
                    }

                    override fun onFailure(call: Call<SignInDC>?, t: Throwable?) {
                        cont.resume(null)
                        cf.log(TAG, "result=$t")
                    }
                })
        }
    }

    suspend fun downloadInfoMovies(token: String?, filter: String): List<MovieInfo>? {
        return suspendCoroutine { cont ->
            NetworkService
                .getInstance(token)
                ?.getJsonApi()
                ?.downloadMovies(filter)
                ?.enqueue(object : Callback<List<MovieInfo>> {
                    override fun onResponse(
                        call: Call<List<MovieInfo>>?,
                        response: Response<List<MovieInfo>>?
                    ) {
                        if (response?.isSuccessful == true) {
                            cont.resume(response.body())
                        }
                        if (response?.isSuccessful != true) {
                            cont.resume(null)
                            cf.log(TAG, "response=${response?.body()}")
                        }
                    }

                    override fun onFailure(call: Call<List<MovieInfo>>?, t: Throwable?) {
                        cont.resume(null)
                        cf.log(TAG, "result=$t")
                    }

                })
        }
    }

    suspend fun downloadInfoPoster(token: String?): PosterInfo? {
        return suspendCoroutine { cont ->
            NetworkService
                .getInstance(token)
                ?.getJsonApi()
                ?.downloadInfoPoster()
                ?.enqueue(object : Callback<PosterInfo> {
                    override fun onResponse(
                        call: Call<PosterInfo>?,
                        response: Response<PosterInfo>?
                    ) {
                        if (response?.isSuccessful == true) {
                            cont.resume(response.body())
                        }
                        if (response?.isSuccessful != true) {
                            cont.resume(null)
                            cf.log(TAG, "response=${response?.body()}")
                        }
                    }

                    override fun onFailure(call: Call<PosterInfo>?, t: Throwable?) {
                        cont.resume(null)
                        cf.log(TAG, "result=$t")
                    }
                })
        }
    }

    suspend fun downloadInfoLastView(token: String, filter: String): MovieInfo? {
        cf.log(TAG, "repository downloadInfoLastView | token=$token")
        return suspendCoroutine { cont ->
            NetworkService
                .getInstance(token)
                ?.getJsonApi()
                ?.downloadInfoLastView(filter)
                ?.enqueue(object : Callback<List<MovieInfo>> {
                    override fun onResponse(call: Call<List<MovieInfo>>?,
                                            response: Response<List<MovieInfo>>?) {
                        if (response?.isSuccessful == true) {
                            cont.resume(response.body()[0])
                        }
                        if (response?.isSuccessful != true) {
                            cont.resume(null)
                            cf.log(TAG, "response=${response?.body()}")
                        }
                    }

                    override fun onFailure(call: Call<List<MovieInfo>>?, t: Throwable?) {
                        cont.resume(null)
                        cf.log(TAG, "result=$t")
                    }
                })
        }
    }

    suspend fun downloadInfoRequiredMovie(movieId: String): MovieInfo? {
        return suspendCoroutine { cont ->
            NetworkService
                .getInstance(null)
                ?.getJsonApi()
                ?.downloadInfoRequiredMovie(movieId)
                ?.enqueue(object : Callback<MovieInfo> {
                    override fun onResponse(
                        call: Call<MovieInfo>?,
                        response: Response<MovieInfo>?
                    ) {
                        if (response?.isSuccessful == true) {
                            cont.resume(response.body())
                        }
                        if (response?.isSuccessful != true) {
                            cont.resume(null)
                            cf.log(TAG, "response=${response?.body()}")
                        }
                    }
                    override fun onFailure(call: Call<MovieInfo>?, t: Throwable?) {
                        cont.resume(null)
                        cf.log(TAG, "result=$t")
                    }
                })
        }
    }

    companion object {
        private var INSTANCE: CinemaRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CinemaRepository(context)
            }
        }

        fun get(): CinemaRepository {
            return requireNotNull(INSTANCE) {
                "CinemaRepository must be initialized"
            }
        }
    }
}