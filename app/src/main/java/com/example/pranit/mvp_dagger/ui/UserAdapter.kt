package com.example.pranit.mvp_dagger.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.pranit.mvp_dagger.R
import com.example.pranit.mvp_dagger.model.User
import com.example.pranit.mvp_dagger.ui.UserAdapter.ViewHolder

/**
 * Created by pranit on 19/1/18.
 */
class UserAdapter(val users: ArrayList<User>) :RecyclerView.Adapter<ViewHolder>(){

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent,false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtvUser.text = "${users[position].firstName} ${users[position].lastName}"
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtvUser: TextView = itemView.findViewById(R.id.txt_name)
    }
}