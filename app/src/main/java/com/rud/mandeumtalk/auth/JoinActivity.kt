package com.rud.mandeumtalk.auth

import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rud.mandeumtalk.MainActivity
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.databinding.ActivityJoinBinding


class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        auth = Firebase.auth


        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.joinBtn.setOnClickListener {

            var isGoToJoin = true

            val email = binding.emailArea.text.toString()
            val password1 = binding.passwordArea1.text.toString()
            val password2 = binding.passwordArea2.text.toString()
            val emailLink = intent.data.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            if (password1.isEmpty()) {
                Toast.makeText(this, "Password1을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            if (password2.isEmpty()) {
                Toast.makeText(this, "Password2을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            if (password1.length < 8) {
                Toast.makeText(this, "닉네임을 8자리 이상으로 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            // 비밀번호 2개가 같은지 확인
            if (!password1.equals(password2)) {
                Toast.makeText(this, "비밀번호를 똑같이 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            // 비밀번호가 6자 이상인지
            if (password1.length < 6) {
                Toast.makeText(this, "비밀번호를 6자리 이상으로 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if (email.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
                Toast.makeText(this, "입력 안한 칸이 있습니다.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }


            if (isGoToJoin) {
                auth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(this) { task ->
					if (task.isSuccessful) {

						Toast.makeText(this, "회원가입이 완료되었습니다.\n반갑습니다. 만듦톡 입니다.", Toast.LENGTH_LONG).show()

						val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
						startActivity(intent)

					} else {
						Toast.makeText(this, "회원가입에 실패하였습니다.\n이메일을 확인해주세요.", Toast.LENGTH_LONG).show()
					}
				}
            }
        }

        findViewById<ImageView>(R.id.backIcon).setOnClickListener {
            val intent = Intent(this, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}