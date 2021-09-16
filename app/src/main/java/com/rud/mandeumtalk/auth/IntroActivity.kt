package com.rud.mandeumtalk.auth

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.fragments.IntroFragment
import com.rud.mandeumtalk.fragments.LoginFragment
import com.rud.mandeumtalk.viewPager.FragmentAdapter
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

	private var isDouble: Boolean = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_intro)

		val adapter = FragmentAdapter(supportFragmentManager)
		adapter.addFragment(LoginFragment(), "일반 로그인")
		adapter.addFragment(IntroFragment(), "간편 로그인")
		viewPager.adapter = adapter
		tab_menu.setupWithViewPager(viewPager)
	}

	override fun onBackPressed(){
		if(isDouble) {
			finish()
		}
		isDouble = true
		Toast.makeText(this, "종료하시려면 더블클릭", Toast.LENGTH_LONG).show()
		Handler().postDelayed(Runnable {isDouble = false}, 2000)
	}
}