package com.github.llmaximll.worldcinema.repositories

import android.content.Context
import com.github.llmaximll.worldcinema.common.CommonFunctions
import com.github.llmaximll.worldcinema.dataclasses.network.SignInDC
import com.github.llmaximll.worldcinema.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val TAG = "CinemaRepository"

class CinemaRepository private constructor(context: Context) {

    private val commonFunctions = CommonFunctions.get()

    suspend fun signUp(email: String, password: String, firstName: String, lastName: String): Boolean {
        return suspendCoroutine { cont ->
            NetworkService
                .instance
                ?.getJsonApi()
                ?.signUp(email, password, firstName, lastName)
                ?.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        if (response?.isSuccessful == true) {
                            cont.resume(true)
                        } else {
                            cont.resume(false)
                            commonFunctions.log(TAG, "response=${response?.message()}")
                        }
                    }

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        cont.resume(false)
                        commonFunctions.log(TAG, "result=$t")
                    }
                })
        }
    }

    suspend fun signIn(email: String, password: String): SignInDC? {
        return suspendCoroutine { cont ->
            NetworkService
                .instance
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
                            commonFunctions.log(TAG, "response=${response?.body()}")
                        }
                    }

                    override fun onFailure(call: Call<SignInDC>?, t: Throwable?) {
                        cont.resume(null)
                        commonFunctions.log(TAG, "result=$t")
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