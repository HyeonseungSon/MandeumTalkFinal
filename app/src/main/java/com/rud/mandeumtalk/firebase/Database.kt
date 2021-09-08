package com.rud.mandeumtalk.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Database {

    companion object {

        val database : FirebaseDatabase = Firebase.database

        val memberReference = database.getReference("Member")

    }
}