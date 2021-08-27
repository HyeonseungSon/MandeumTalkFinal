package com.rud.mandeumtalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


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





