package com.rud.mandeumtalk.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.rud.mandeumtalk.MainActivity
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.intro.auth.JoinActivity
import com.rud.mandeumtalk.databinding.FragmentLoginBinding
import com.rud.mandeumtalk.firebase.Auth


class LoginFragment : Fragment() {

//    private lateinit var auth: FirebaseAuth

    private lateinit var binding: FragmentLoginBinding

    // onBackPressed()
//    private var isDouble = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

//        auth = Firebase.auth

        // 로그인
        binding.loginBtn.setOnClickListener {

            val email = binding.emailArea.text.toString()
            val password = binding.passwordArea.text.toString()

            if (email == "" && password == "") {
                Toast.makeText(requireContext(), "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (email == "") {
                Toast.makeText(requireContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (password == "") {
                Toast.makeText(requireContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                Auth.getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
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
        }

        // 비회원 로그인 버튼
        binding.noAcouuntBtn.setOnClickListener {

//            val key : String = Database.memberReference.push().key.toString()
//
//            Database.memberReference.child(key).setValue(MemberModel("비회원", "", "비회원 로그인", key))
//
//            val intent = Intent(activity, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)

            Auth.getAuth().signInAnonymously().addOnCompleteListener { task ->
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

        // 회원가입 버튼
        binding.joinBtn.setOnClickListener {
            val intent = Intent(activity, JoinActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return binding.root
    }

}