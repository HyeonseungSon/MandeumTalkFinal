package com.rud.mandeumtalk.fragments

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rud.mandeumtalk.MainActivity
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.auth.IntroActivity
import com.rud.mandeumtalk.auth.JoinActivity
import com.rud.mandeumtalk.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: FragmentLoginBinding

    // onBackPressed()
    private var isDouble = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        auth = Firebase.auth

        // 로그인
        binding.loginBtn.setOnClickListener {

            val email = binding.emailArea.text.toString()
            val password = binding.passwordArea.text.toString()

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val intent = Intent(activity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                    Toast.makeText(context, "로그인 성공", Toast.LENGTH_LONG).show()

                } else {

                    Toast.makeText(context, "로그인 실패", Toast.LENGTH_LONG).show()
                }
            }
        }

        // 비회원 가입
        binding.noAcouuntBtn.setOnClickListener {
            auth.signInAnonymously().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                    Toast.makeText(context, "로그인 성공", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(context, "실패", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Join Us
        binding.joinBtn.setOnClickListener {
            val intent = Intent(activity, JoinActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("Activity", "Login")
            startActivity(intent)
        }
        return binding.root
    }

}