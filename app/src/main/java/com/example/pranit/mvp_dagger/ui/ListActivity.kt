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
 * ListActivity.kt on mvp-dagger
 * Last modified 18/7/18 1:23 AM
 *
 * @auther pranit
 */

package com.example.pranit.mvp_dagger.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.pranit.mvp_dagger.R
import com.example.pranit.mvp_dagger.api.UserApi
import com.example.pranit.mvp_dagger.data.UserListDataSource
import com.example.pranit.mvp_dagger.model.User
import com.example.pranit.mvp_dagger.model.UserResponse
import com.example.pranit.mvp_dagger.uiutil.EndlessRecyclerViewAdapter

class ListActivity : AppCompatActivity(), EndlessRecyclerViewAdapter.RequestToLoadMoreListener, UserListContract.View {
    private val TAG: String = "ListActivity"

    override lateinit var presenter: UserListPresenter
    private lateinit var recyUserList : RecyclerView
    private val userList: ArrayList<User> = ArrayList()
    private lateinit var endlessAdapter: EndlessRecyclerViewAdapter
    private lateinit var userAdapter: UserAdapter
    private var currentPage: Int = 0
    private var maxPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        recyUserList = findViewById(R.id.recy_users)
        recyUserList.setHasFixedSize(true)
        userAdapter = UserAdapter(userList)
        endlessAdapter = EndlessRecyclerViewAdapter(this,userAdapter,this)
        recyUserList.adapter = endlessAdapter
        presenter = UserListPresenter(UserListDataSource.getInstance(UserApi.create()), this)
        presenter.start()

        if(!userList.isEmpty()){
            endlessAdapter.onDataReady(true)
        }
    }

    override fun onLoadMoreRequested() {
        if(currentPage <= maxPage) {
            currentPage++
            presenter.getAllUsersPerPage(currentPage)
        }
    }

    override fun onSuccess(response: UserResponse?) {
        currentPage = response?.page ?: 0
        maxPage = response?.totalPages ?: 0
        userAdapter.appendItems(response?.data as ArrayList<User>)
        if (currentPage < maxPage) {
            endlessAdapter.onDataReady(true)
        }else {
            endlessAdapter.onDataReady(false)
        }
    }

    override fun onError(error: String) {
        Log.e(TAG, error)
        endlessAdapter.onDataReady(false)
    }
}
