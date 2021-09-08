package com.rud.mandeumtalk.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Auth {

    companion object {

        private lateinit var auth : FirebaseAuth

        fun getAuth () : FirebaseAuth {
            auth = Firebase.auth

            return auth
        }
    }
}