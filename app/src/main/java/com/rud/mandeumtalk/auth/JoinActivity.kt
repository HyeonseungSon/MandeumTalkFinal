package com.rud.mandeumtalk.auth

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.rud.mandeumtalk.MainActivity
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.databinding.ActivityJoinBinding
import com.rud.mandeumtalk.firebase.Auth
import com.rud.mandeumtalk.firebase.Database

class JoinActivity : AppCompatActivity() {

//    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

//        auth = Firebase.auth


        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.joinBtn.setOnClickListener {

            var isGoToJoin = true

            val email = binding.emailArea.text.toString()
            val password1 = binding.passwordArea1.text.toString()
            val password2 = binding.passwordArea2.text.toString()

            if (email.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
                Toast.makeText(this, "이메일 또는 비밀번호를 입력해 주세요.", Toast.LENGTH_LONG).show()
            } else if (!email.contains("@") || !email.contains(".")) {
                Toast.makeText(this, "올바른 이메일 형식으로 입력해 주세요.", Toast.LENGTH_LONG).show()
            } else if (email.length < 8) {
                Toast.makeText(this, "이메일이 너무 짧습니다.", Toast.LENGTH_LONG).show()
            } else if (password1 != password2) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
            } else if (password1.length < 8) {
                Toast.makeText(this, "비밀번호는 여덟자리 이상 입력해 주세요.", Toast.LENGTH_LONG).show()
            } else {
                if (isGoToJoin) {
                    Auth.getAuth().createUserWithEmailAndPassword(email, password1).addOnCompleteListener(this) { task ->
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
//                val memberListener = object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//
//                        val keyArrayList = arrayListOf<String>()
//
//                        for (model in snapshot.children) {
//                            val key = model.key
//                            keyArrayList.add(key!!)
//                        }
//
//                        if (keyArrayList.contains(email)) {
//                            Toast.makeText(this@JoinActivity, "중복된 이메일 입니다.", Toast.LENGTH_SHORT).show()
//                        }
//                        if (!keyArrayList.contains(email)) {
//                            joinMandeum(email, password1)
//                        }
//                    }
//                    override fun onCancelled(error: DatabaseError) {
//                        Toast.makeText(this@JoinActivity, "기존회원정보 불러오기 실패\n관리자 문의", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                Database.memberReference.addValueEventListener(memberListener)
            }
        }

        findViewById<ImageView>(R.id.backIcon).setOnClickListener {
            val intent = Intent(this, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    fun joinMandeum (email : String, password : String) {
        Database.memberReference.child(email).setValue(MemberModel(email, password, "일반 회원가입", ""))

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}