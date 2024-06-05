package com.example.projectesmian

import com.google.firebase.database.Exclude

data class DataClub(

    val asal : String? =null,
    val deskripsi : String? = null,
    val gambarurl : String? = null,
    val nmklub : String? = null,


    @get:Exclude
    @set:Exclude
    var key:String? = null

)
