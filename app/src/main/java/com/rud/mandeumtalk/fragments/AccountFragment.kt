package com.rud.mandeumtalk.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.auth.IntroActivity
import com.rud.mandeumtalk.databinding.FragmentAccountBinding


class AccountFragment : Fragment() {

	private lateinit var auth : FirebaseAuth
	private lateinit var binding : FragmentAccountBinding
	lateinit var alertDialog : AlertDialog
	lateinit var builder : AlertDialog.Builder

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		auth = Firebase.auth

		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)

		binding.homeIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_homeFragment)
		}
		binding.homeText.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_homeFragment)
		}

		binding.portfolioIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_guideFragment)
		}
		binding.portfolioText.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_guideFragment)
		}

		binding.boardIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_guideFragment)
		}
		binding.boardText.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_guideFragment)
		}

		binding.contactUsIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_boardFragment)
		}
		binding.contactUsText.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_to_boardFragment)
		}

		binding.accountIcon.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_self)
		}
		binding.accountText.setOnClickListener {
			it.findNavController().navigate(R.id.action_accountFragment_self)
		}

		binding.accountBtnLogout.setOnClickListener {
			showdialog()
		}


		return binding.root
	}
 	private fun showdialog() {
		try{
			var str_tittle = "로그아웃"
			var str_message = " 로그아웃 하시겠습니까?"
			var str_buttonOK = "네"
			var str_buttonNO = "아니오"

			builder = AlertDialog.Builder(getContext())
			builder.setTitle(str_tittle) //팝업창 타이틀 지정
			builder.setIcon(R.drawable.accountmainpink) // 팝업창 이미지
			builder.setMessage(str_message) //팝업창 내용 지정
			// builder.setCancelable(false) //외부 레이아웃 클릭시도 팝업창이 사라지지않게 설정
			builder.setPositiveButton(str_buttonOK, DialogInterface.OnClickListener { dialog, which ->
				auth.signOut()
				Toast.makeText(context, "로그아웃", Toast.LENGTH_SHORT).show()
				val intent = Intent(activity, IntroActivity::class.java)
				intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
				startActivity(intent)
			})
			builder.setNegativeButton(str_buttonNO, DialogInterface.OnClickListener { dialog, which ->
				// TODO Auto-generated method stub
				Toast.makeText(context, "취소했습니다.", Toast.LENGTH_SHORT).show()
			})

			alertDialog = builder.create()
			try {
				alertDialog.show()
			}
			catch (e : Exception){
				e.printStackTrace()
			}
		}
		catch(e : Exception){
			e.printStackTrace()
		}
	}

}