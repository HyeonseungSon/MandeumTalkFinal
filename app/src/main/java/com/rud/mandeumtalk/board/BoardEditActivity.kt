package com.rud.mandeumtalk.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rud.mandeumtalk.MainActivity
import com.rud.mandeumtalk.R
import java.text.SimpleDateFormat
import java.util.*

class BoardEditActivity : AppCompatActivity() {

    private val TAG = BoardEditActivity::class.java.simpleName

    private lateinit var writerUid : String
    private lateinit var writer : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_edit)

        val key = intent.getStringExtra("key").toString()

        getBoardData(key)
        getImageData(key)

        findViewById<TextView>(R.id.editUploadButton).setOnClickListener {
            editBoardData(key)
        }
    }

    private fun getBoardData (key : String) {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataModel = dataSnapshot.getValue(BoardModel::class.java)

//                Toast.makeText(this@BoardEditActivity, "${dataModel?.title}" +
//                        "\n${dataModel?.contents}" +
//                        "\n${dataModel?.writer}" +
//                        "\n${dataModel?.writerUid}" +
//                        "\n${dataModel?.dateTime}" +
//                        "\n${dataModel?.key}" +
//                        "\n$key", Toast.LENGTH_LONG).show()

                findViewById<EditText>(R.id.titleArea).setText(dataModel?.title)
                findViewById<EditText>(R.id.contentsArea).setText(dataModel?.contents)
                writer = dataModel!!.writer
                writerUid = dataModel!!.writerUid

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost::onCancelled", databaseError.toException())
            }
        }
        Firebase.database.getReference("Board").child(key).addValueEventListener(postListener)
    }

    private fun getImageData (key : String) {

        val storageReference = Firebase.storage.reference.child(key + ".png")

        val imageViewFromFB = findViewById<ImageView>(R.id.uploadImage)

        storageReference.downloadUrl.addOnCompleteListener({ task ->
            if (task.isSuccessful) {
                Glide.with(this).load(task.result).into(imageViewFromFB)
            } else {

            }
        })
    }

    private fun editBoardData (key: String) {

        val title : String = findViewById<EditText>(R.id.titleArea).text.toString()
        val contents : String = findViewById<EditText>(R.id.contentsArea).text.toString()

        val currentDateTime = Calendar.getInstance().time
        val dateTimeFormat : String = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREAN).format(currentDateTime)
        val editDateTime : String = "수정한 날짜 : $dateTimeFormat"

        Firebase.database.getReference("Board").child(key).setValue(BoardModel(title, contents, writer, editDateTime, writerUid, key))

        Toast.makeText(this, "게시물 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show()

        val intent = Intent (this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}