package com.example.pranit.mvp_dagger.model

import com.google.gson.annotations.SerializedName

data class User (

    @field:SerializedName("id")
    var id: Int? ,
    @field:SerializedName("first_name")
    var firstName: String? ,
    @field:SerializedName("last_name")
    var lastName: String? ,
    @field:SerializedName("avatar")
    var avatar: String?

)