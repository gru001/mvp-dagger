/*
 * MIT License
 *
 * Copyright (c) 2018 Pranit.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * UserApi.kt on mvp-dagger
 * Last modified 17/7/18 10:31 PM
 *
 * @auther pranit
 */

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