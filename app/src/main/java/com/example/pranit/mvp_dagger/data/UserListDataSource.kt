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
 * UserListDataSource.kt on mvp-dagger
 * Last modified 17/7/18 11:37 PM
 *
 * @auther pranit
 */

package com.example.pranit.mvp_dagger.data

import com.example.pranit.mvp_dagger.api.UserApi
import com.example.pranit.mvp_dagger.api.getAllUsers
import com.example.pranit.mvp_dagger.model.User

class UserListDataSource private constructor(private val service: UserApi){

    fun getAllUsersPerPage(page : Int, onSuccess :(users:List<User>)-> Unit, onError : (error : String)->  Unit) {
        getAllUsers(service, page, onSuccess, onError)
    }

    companion object {
        private var INSTANCE: UserListDataSource? = null

        @JvmStatic
        fun getInstance(service: UserApi): UserListDataSource{
            if (INSTANCE == null) {
                synchronized(UserListDataSource::class.java) {
                    INSTANCE = UserListDataSource(service)
                }
            }

            return INSTANCE!!
        }
    }
}