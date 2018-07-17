package com.example.pranit.mvp_dagger.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.pranit.mvp_dagger.R
import com.example.pranit.mvp_dagger.api.UserApi
import com.example.pranit.mvp_dagger.data.UserListDataSource
import com.example.pranit.mvp_dagger.model.User
import com.example.pranit.mvp_dagger.uiutil.EndlessRecyclerViewAdapter

class ListActivity : AppCompatActivity(), EndlessRecyclerViewAdapter.RequestToLoadMoreListener, UserListContract.View {
    private val TAG: String = "ListActivity"

    override lateinit var presenter: UserListPresenter
    private lateinit var recyUserList : RecyclerView
    private val userList: ArrayList<User> = ArrayList()
    private lateinit var endlessAdapter: EndlessRecyclerViewAdapter
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        recyUserList = findViewById(R.id.recy_users)
        recyUserList.setHasFixedSize(true)
        userAdapter = UserAdapter(userList)
        endlessAdapter = EndlessRecyclerViewAdapter(this,userAdapter,this)
        recyUserList.adapter = endlessAdapter
        presenter = UserListPresenter(UserListDataSource.getInstance(UserApi.create()), this)

        if(!userList.isEmpty()){
            endlessAdapter.onDataReady(true)
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun onLoadMoreRequested() {

    }

    override fun onSuccess(response: List<User>) {
        userList.addAll(response)
        userAdapter.notifyDataSetChanged()
    }

    override fun onError(error: String) {
        Log.e(TAG, error)
    }
}
