package com.example.pranit.mvp_dagger.ui

import com.example.pranit.mvp_dagger.model.UserResponse
import com.example.pranit.mvpshop.BasePresenter
import com.example.pranit.mvpshop.BaseView

interface UserListContract {
    interface View : BaseView<UserListPresenter> {
        fun onSuccess(response: UserResponse?)

        fun onError(error: String)
    }

    interface UserActionListener :BasePresenter{
        fun getAllUsersPerPage(page: Int)
    }
}