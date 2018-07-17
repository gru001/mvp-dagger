package com.example.pranit.mvp_dagger.api

import android.util.Log
import com.example.pranit.mvp_dagger.model.User
import com.example.pranit.mvp_dagger.model.UserResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val TAG = "UserApi"

fun getAllUsers(service : UserApi, page: Int, onSuccess :(users:List<User>)-> Unit, onError : (error : String)->  Unit) {

    service.getAllUsers(page).enqueue(object :Callback<UserResponse> {
        override fun onFailure(call: Call<UserResponse>?, t: Throwable?) {
            Log.d(TAG, "fail to get data")
            onError(t?.message ?: "unknown error")
        }

        override fun onResponse(call: Call<UserResponse>?, response: Response<UserResponse>?) {
            Log.d(TAG, "got a response $response")
            if (response!!.isSuccessful) {
                val repos = response?.body()?.data ?: emptyList()
                onSuccess(repos)
            } else {
                onError(response?.errorBody()?.string() ?: "Unknown error")
            }
        }

    })
}

interface UserApi {

    @GET("users")
    fun getAllUsers(@Query("page") page: Int) : Call<UserResponse>

    companion object Factory{
        private const val BASE_URL = "https://reqres.in/api/"

        fun create() : UserApi {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(UserApi::class.java)
        }
    }
}