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