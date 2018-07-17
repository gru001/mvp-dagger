package com.example.pranit.mvp_dagger.ui

import com.example.pranit.mvp_dagger.data.UserListDataSource

class UserListPresenter(val repository: UserListDataSource, val view: UserListContract.View):UserListContract.UserActionListener {
    override fun getAllUsersPerPage(page: Int) {
        repository.getAllUsersPerPage(page, {
            response -> view.onSuccess(response)
        }, {error -> view.onError(error) })
    }

    override fun start() {
        getAllUsersPerPage(0)
    }

    init {
        view.presenter = this
    }
}