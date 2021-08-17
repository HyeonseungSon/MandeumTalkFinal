package com.rud.mandeumtalk.board

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rud.mandeumtalk.MainActivity
import com.rud.mandeumtalk.R
import com.rud.mandeumtalk.fragments.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write)

        auth = Firebase.auth

        findViewById<Button>(R.id.writeUploadButton).setOnClickListener {

            val title = findViewById<EditText>(R.id.titleArea).text.toString()
            val contents = findViewById<EditText>(R.id.contentsArea).text.toString()
            var currentUserUid = auth.currentUser?.email.toString()
            if (currentUserUid == "null") {
                currentUserUid = "비회원 게시물"
            }
            val dateTime : String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString()

            val database = Firebase.database
            val myRef = database.getReference("Board")
            myRef.push().setValue(BoardModel(title, contents, currentUserUid, dateTime))

            Toast.makeText(this, "$title\n작성 완료되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}

/*
*             if (item.writer == "null") {
                Log.e("if!!!", "if!!!")
                itemView.findViewById<TextView>(R.id.input3).text = "비회원 게시물"
                Log.e("item.writer == \"null\"", "${item.writer == "null"}")
            } else {
                Log.e("else!!!", "else!!!")
                itemView.findViewById<TextView>(R.id.input3).text = item.writer
            }*/