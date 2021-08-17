package com.rud.mandeumtalk

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rud.mandeumtalk.auth.IntroActivity
import com.rud.mandeumtalk.board.BoardActivity
import com.rud.mandeumtalk.databinding.ActivityJoinBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

	private lateinit var auth: FirebaseAuth

	var backKeyPressedTime : Long = 0
	override fun onCreate(savedInstanceState: Bundle?) {

		auth = Firebase.auth

		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

	}

	override fun onBackPressed() {
			if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
				backKeyPressedTime = System.currentTimeMillis();
				Toast.makeText(this, "종료하실려면 더블클릭" ,Toast.LENGTH_LONG).show()
				return;
			}
			if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
				finishAffinity()
			}
		}
	}





